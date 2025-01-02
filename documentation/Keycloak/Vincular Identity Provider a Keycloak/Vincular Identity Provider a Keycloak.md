Fuente: https://www.keycloak.org/docs/latest/server_admin/index.html#_identity_broker

Objetivo: el objetivo de este documento es demostrar como se puede vincular un IdentityProvide a #Keycloak.
Un Identity Provider es aquel servicio que nos provee credenciales de identidad válidas para un usuario logueado. Algunos de los proveedores que Keycloak permite que identifiquen usuarios en nuestro reino son:
* BitBucket
* Facebook
* GitHub (ya vinculado)
* GitLab
* Google --> [[Vincular Google como Identity provider a Keycloak]]
* Instagram
* LinkedIn --> [[Vincular LinkedIn como Identity provider a Keycloak]]
* Microsoft
* PayPal 
* StackOverflow
* X (Twiter)

El procedimiento para agregar un IdentityProvider a nuestro reino es el siguiente:
1. Loguearnos como administradores en la consola de Keycloak
2. Dirigirnos a la pestaña de IdentityProviders dentro de nuestro reino y selecccionar uno de los proveedores. De este paso vamos a sacar una Redirect URI que vamos a necesitar en el siguiente paso. Ejemplo: http://localhost:3704/admin/master/console/#/Ciudadano/identity-providers. 
3. Crear o registrar nuestra App en el IdentityProvider. En este paso vamos a obtener un Client ID y Client Secret que necesitamos para el paso siguiente. Ejemplo: https://docs.github.com/en/apps/oauth-apps/building-oauth-apps/creating-an-oauth-app
4. Terminar de registrar el Client ID y Client Secret del IdentityProvider en Keycloak. Podemos tildar la casilla de verificación de email así nos queda verificado email si lo ha hecho a través del IdentityProvider (esto nos permite por ahora asegurarnos de que el correo exista sin todavía tener un servidor SMTP propio).