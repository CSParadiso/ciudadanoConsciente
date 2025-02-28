# SANDBOX ACCOUNT (7 dias de antes de pasar a prod)
#### Fuentes
Genérica: https://www.keycloak.org/docs/26.0.7/server_admin/index.html#_identity_broker
Específica: https://www.keycloak.org/docs/26.0.7/server_admin/#paypal
![[VincularIdentityProviderPayPal-Paso0.png]]

#### Objetivo
El objetivo de este documento es demostrar como se puede vincular Paypal como Identity Provider a #Keycloak.
Un Identity Provider es aquel servicio que nos provee credenciales de identidad válidas para un usuario logueado. 

#### Procedimiento 
Para agregar un IdentityProvider a nuestro reino es el siguiente:
1. Loguearnos como administradores en la consola de Keycloak
2. Dirigirnos a la pestaña de IdentityProviders dentro de nuestro reino y selecccionar uno de los proveedores. 
   ![[VincularIdentityProviderPayPal-Paso1.png]]
3. De este paso vamos a sacar una Redirect URI que vamos a necesitar en el siguiente paso.
    ![[VincularIdentityProviderPayPal-Paso2.png]]
3. Crear o registrar nuestra App en el IdentityProvider (en este caso: https://developer.paypal.com/developer/applications). En este paso vamos a obtener un Client ID y Client Secret que necesitamos para el paso siguiente. 
4. 
   ![[VincularIdentityProviderPayPal-Paso3.1.png]]
   ![[VincularIdentityProviderPayPal-Paso3.2.png]]
4. Registrar el Client ID y Client Secret del IdentityProvider en Keycloak. Podemos tildar la casilla de verificación de email así nos queda verificado email si lo ha hecho a través del IdentityProvider (esto nos permite por ahora asegurarnos de que el correo exista sin todavía tener un servidor SMTP propio).
   ![[VincularIdentityProviderLinkedin-Paso4.png]]
   6. Crear cuentas de prueba del sandbox
      ![[VincularIdentityProviderPayPal-Paso5.png]]
   7. Verificar 
       ![[VincularIdentityProviderPayPal-Paso6.png]]