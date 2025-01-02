
Objetivo: documentar las configuraciones de #Keycloak, incluídos: la base de datos, el compose.yaml y las configuraciones de Reinos, Clientes, Roles, Grupos, etc. Todo lo que se utilizado. Si sigue el orden de cada uno de los apartados, debería poder replicar el desarrollo.

---

**CREAR BASE DE DATOS Y ESQUEMA**
Lo primero es lo primero: crear la base de datos y el esquema. En psql:
```sql
create database keycloak_25; -- Creamos la DB (KC_DB_URL en compose.yaml)
\c keycloak_25; -- Nos conectamos a la DB
create schema keycloak; -- Creamos el esquema. (KC_DB_SCHEMA en compose.yaml)

```

---

**CREAR COMPOSE**
**compose.yaml** 
``` yaml
version: '3.1'

services:

  keycloak25:
    image: quay.io/keycloak/keycloak:25.0
    container_name: keycloak25
    deploy:
      resources:
        limits:
          memory: 500M
    restart: always
    ports:
      - 3703:8080 # Puerto externo: Puerto Intertno
    environment:
      - TZ=America/Argentina/Ushuaia # Para la JVM
      - KEYCLOAK_ADMIN=admin
      - KEYCLOAK_ADMIN_PASSWORD=chipaParaguaya # esta clave solo se setea si no existe ninguna, debemos cambiar esta clave
      # DEPRECATED - KC_HOSTNAME_URL=http://localhost:3704 # Para poder acceder desde afuera de la red del contenedor
      # DEPRECATED - KC_HOSTNAME_ADMIN_URL=http://localhost:3704 # Esta url debe poder ser accesible por fuera de la red del contenedor (si la mapeamos internamente, desde fuera no saben el nombre del servicio.)
      - KC_HOSTNAME_STRICT=false
      # DEPRECATED - KC_HOSTNAME_STRICT_BACKCHANNEL=true
      - KC_HTTP_ENABLED=true # Migrado desde KC23 --> KC_PROXY=edge # permite HTTP (https://www.keycloak.org/docs/25.0.2/upgrading/#deprecated-proxy-option)
      - KC_DB=postgres # Motor base de datos
      - KC_DB_URL=jdbc:postgresql://postgres:5432/keycloak_25 # /Nombre DB 
      - KC_DB_SCHEMA=keycloak # Nombre esquema
      - KC_DB_USERNAME=saimon
      - KC_DB_PASSWORD=chipaParaguaya
      - KC_FEATURES=docker,admin-fine-grained-authz,organization
      - KC_HEALTH_ENABLED=true
      - KC_METRICS_ENABLED=true
    command: start --log="console,file" 
    volumes:
       - ./logs:/opt/keycloak/data/log/ # mapeamos carpeta de logs para que se guarden fuera del contenedor
    networks:
      - citizen
    hostname:
      "keycloak25"

networks:
  citizen:
    external: true

```

Una vez creado el archivo, nos posicionamos en el mismo directorio y levantamos el contenedor con el comando
```bash
docker compose keycloak25 up -d
```

---


**CONSOLA DE ADMINISTRACIÓN DE KEYCLOAK**
1. Ingresamos en el navegador la url de la consola (en este caso localhost) y el puerto externo (definido en el compose, 3703): http://localhost:3703.
2. Es necesario cambiar las contraseñas de la consola de administración. Arriba a la derecha, desplegamos la lista y seleccionamos "Manage Account".
	![[Pasted image 20240806221612.png]]
	
	 Luego, en las pestañas de la izquierda seleccionamos "Account security>Signing In". ![[Pasted image 20240806221912.png]]
	Actualizamos la contraseña seleccionando "Update".
	![[Pasted image 20240806222129.png]]
	Debemos ingresar dos veces la nueva contraseña y seleccionamos "Submit". (Se recomienda utilizar un gestor de contraseñas como [Proton Pass](https://proton.me/pass)).
	![[Pasted image 20240806222326.png]]
	

--- 


**CREAR REINO**
Para crear un Reino debemos seleccionar en la pestaña de la izquierda arriba y seleccionar luego "Create Realm". ![[Pasted image 20240806223950.png]]
Luego importamos un Reino ya creado o creamos uno nuevo indicando el nombre, activamos el botón "Enable" (que significa "habilitar") y seleccionamos "Create". ![[Pasted image 20240806224227.png]]
Ahora ya somos capaces de seleccionar el Reino desde la misma pestaña pestaña de arriba a la izquierda. ![[Pasted image 20240806224430.png]]

---


**CONFIGURAR REINO**
En la pestaña de la izquierda abajo, seleccionamos "Realm settings".![[Pasted image 20240806232510.png]]
En la pestaña "General" podríamos tooglear Organizations a ON para habilitar la funcionalidad Organizations (aún en modo PREVIEW) y seleccionamos "Save". ![[Pasted image 20240806232817.png]]
En la pestaña "Login" es importante que toogleemos a ON "User Registration" para permitirle a Keycloak registrar clientes y servirnos así a unos de sus propósitos principales que es la gestión, autenticación y autorización de usuarios. El resto de los campos y pestañas queda a consideración del administrador: roles y grupos por defecto, campos que se solicitan en el registro, sesiones, tokens, localizaciones, etc.![[Pasted image 20240806233240.png]]


---


**CREAR CLIENTE**
En la pestaña de la izquierda seleccionamos la opción "Clientes" y añadimos uno nuevo con el botón "Create client". ![[Pasted image 20240806224943.png]]
En "Client type" seteamos "OpenID Connect".
"Client ID" es el nombre con el que Keycloak identificará al cliente que le hace las peticiones de autorización. (este nombre debe luego setearse en el compose.yaml o en el application.properties).
"Name" es un nombre de display simplemente. La descripción es opcional y a discreción, igual que el display en la UI.
Para continuar, seleccionamos "Next". ![[Pasted image 20240806225545.png]]

Si toogleamos a ON la opción "Client authentication", seremos capaces de poder tildar también el checkbox "Service accounts roles": esto nos permite que el cliente (la aplicación que le solicita permisos a Keycloak) sea capaz de realizar solicitudes identificandose como sí misma, una cuenta de servicio que nos permite realizar solicitudes programáticamente. El resto queda como por defecto y seleccionamos "Next". ![[Pasted image 20240806230558.png]]

En este paso, lo casi exclusivamente importante es "Valid redirect URIs" que especifica adonde redirige Keycloak luego de un login exitoso. Como estamos creando un cliente de desarrollo y la solicitud se hace y recibe en la misma URI, en ese campo la URL desde donde se hace la petición (que vaya y vuelva al mismo lugar, básicamente). ![[Pasted image 20240806232002.png]] ![[Pasted image 20240806232111.png]] Seleccionamos "Save".

---


**VINCULAR APP DE CLIENTE CON KEYCLOAK**
En el application.properties o en el compose.yaml debemos vincular las credenciales del cliente y el secreto proveído por Keycloak. El nombre del cliente en keycloak y el secreto.

Una vez seleccionado un Cliente, desde la pestaña llamada "Credentials" podemos copiar el valor de "Client Secret": ![[Pasted image 20240806235830.png]] para pegarlo en el archivo de configuración del cliente, que puede ser el application.properties: ![[Pasted image 20240806235937.png]] o puede ser el compose.yaml: ![[Pasted image 20240807000327.png]]

---


**CREAR ROLES**
Ahora que ya tenemos una DB, un Reino y un Cliente (y luego de crear uno o más usuarios) vamos a crear roles para poder asirgnarlos luego.
Seleccionando la pestaña "Realm roles", veremos un botón llamado "Create role" que debemos clikear. ![[Pasted image 20240807143129.png]]Luego elegimos un nombre (vamos a tratar de mantener las iniciales con mayúsculas) y una descripción y seleccionamos "Save".

****


**CREAR GRUPOS**
Los roles que hayamos creado, se los podemos mapear a Grupos a los que luego añadiremos usuarios que heredarán esos Roles.
En la pestaña de la izquierda seleccionamos "Groups" y cuando nos aparezca el contenido del menú seleccionamos "Create group". ![[Pasted image 20240807143352.png]]
Elegimos un nombre para el grupo (vamos a tratar de mantener los grupos en minúsculas) y seleccionamos "Create".
![[Pasted image 20240807143646.png]]

A los grupos se les puede asignar subgrupos para mayor granularidad. Vamos a usar esta característica de Keycloak para mapear nuestros roles. Una vez seleccionado un grupo, en la pestaña "Child groups" seleccionamos "Create group" y repetimos el proceso anterior.

Tanto a los grupos como a los subgrupos podemos mapearles roles. Una ves seleccionado el grupo o subgrupo deseado, buscamos la pestaña "Role mapping" y le seleccionamos"Assign role". 
![[Pasted image 20240807144259.png]]

 Luego, tildamos el checkbox del rol desde la lista de roles y seleccionamos "Assign".  
 ![[Pasted image 20240807144508.png]] 
---


**AGREGAR USUARIOS A GRUPOS (y herederar los roles)**
Ahora que tenemos grupos que tiene mapeados roles, podemos unir usuarios a grupos y que estos hereden esos roles. Esto nos permite en nuestro cliente determinar el rol de un usuario a través del AccessToken y de esta manera permitirle o no el acceso a un recurso.

En la pestaña "Users", seleccionamos un usuario y luego dentro del perfil del usuario seleccionamos la pestaña "Groups" y el botón "Join Group".
![[Pasted image 20240807145432.png]]
Seleccionamos algún grupo o subgrupo al cual querramos agregar al usuario y seleccionamos "Join".
![[Pasted image 20240807145524.png]]



