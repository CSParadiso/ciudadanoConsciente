#### Objetivo
La idea es generar un cliente para un Reino de #Keycloak 

---

En la pestaña de la izquierda seleccionamos la opción "Clientes" y añadimos uno nuevo con el botón "Create client". ![[CrearClienteEnKeycloak.png]]

En "Client type" seteamos "OpenID Connect".
"Client ID" es el nombre con el que Keycloak identificará al cliente que le hace las peticiones de autorización. (este nombre debe luego setearse en el compose.yaml o en el application.properties).
"Name" es un nombre de display simplemente. La descripción es opcional y a discreción, igual que el display en la UI.
Para continuar, seleccionamos "Next". ![[CrearNombreClienteEnKeycloak.png]]

Si toogleamos a ON la opción "Client authentication", seremos capaces de poder tildar también el checkbox "Service accounts roles": esto nos permite que el cliente (la aplicación que le solicita permisos a Keycloak) sea capaz de realizar solicitudes identificandose como sí misma, una cuenta de servicio que nos permite realizar solicitudes programáticamente. El resto queda como por defecto y seleccionamos "Next". ![[CrearConfClienteEnKeycloak.png]]

En este paso, lo casi exclusivamente importante es "Valid redirect URIs" que especifica adonde redirige Keycloak luego de un login exitoso. Como estamos creando un cliente de desarrollo y la solicitud se hace y recibe en la misma URI, en ese campo la URL desde donde se hace la petición (que vaya y vuelva al mismo lugar, básicamente). ![[Pasted image 20240806232002.png]] ![[Pasted image 20240806232111.png]]O podemos seleccionar todos con `*` . Seleccionamos "Save".
![[CrearLoginClienteEnKeycloak.png]]

---
**VINCULAR APP DE CLIENTE CON KEYCLOAK**
En el application.properties o en el compose.yaml debemos vincular las credenciales del cliente y el secreto proveído por Keycloak. El nombre del cliente en keycloak y el secreto.

Una vez seleccionado un Cliente, desde la pestaña llamada "Credentials" podemos copiar el valor de "Client Secret": 
![[SeleccionarClienteEnKeycloak.png]]
![[CopiarSecretClienteEnKeycloak.png]]
```conf
# keycloak OIDC en application.properties
realm=${REALM}  
quarkus.oidc.auth-server-url=${OIDC_AUTH_SERVER_URL}  
quarkus.oidc.client-id=${OIDC_CLIENT_ID}  
quarkus.oidc.credentials.secret=${OIDC_SECRET}
```
```.env
# keycloak OIDC
REALM=NOMBRE_REINO
#OIDC_AUTH_SERVER_URL=URL/DEL/SERVIDOR/DE/AUTH/realms/NOMBRE_REINO
OIDC_CLIENT_ID=NOMBRE_CLIENTE
OIDC_SECRET=SECRETO_CLIENTE
```

