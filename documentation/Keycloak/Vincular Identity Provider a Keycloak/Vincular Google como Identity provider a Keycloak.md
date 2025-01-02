#### Fuentes
Genérica: https://www.keycloak.org/docs/26.0.7/server_admin/index.html#_identity_broker
Específica: https://www.keycloak.org/docs/26.0.7/server_admin/#_google
![[VincularIdentityProviderGoogle-Paso0.png]]

#### Objetivo
El objetivo de este documento es demostrar como se puede vincular Google como Identity Provider a #Keycloak.
Un Identity Provider es aquel servicio que nos provee credenciales de identidad válidas para un usuario logueado. 

#### Procedimiento 
Para agregar un IdentityProvider a nuestro reino es el siguiente:
1. Loguearnos como administradores en la consola de Keycloak
2. Dirigirnos a la pestaña de IdentityProviders dentro de nuestro reino y selecccionar uno de los proveedores. De este paso vamos a sacar una Redirect URI que vamos a necesitar en el siguiente paso.
3. Crear o registrar nuestra App en el IdentityProvider (en este caso: https://console.cloud.google.com/). En este paso vamos a obtener un Client ID y Client Secret que necesitamos para el paso siguiente. 
4. Terminar de registrar el Client ID y Client Secret del IdentityProvider en Keycloak. Podemos tildar la casilla de verificación de email así nos queda verificado email si lo ha hecho a través del IdentityProvider (esto nos permite por ahora asegurarnos de que el correo exista sin todavía tener un servidor SMTP propio).