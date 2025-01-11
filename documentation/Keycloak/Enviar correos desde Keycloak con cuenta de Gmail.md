#### Objetivo
La idea es poder enviar correos con #Keycloak a través de Google a los usuarios para validar sus correos y para reseteaer sus credenciales.

1. Establecer correo de Google en el administrador del reino.
   ![[HabilitarEmailEnReinoKeycloak-Paso0.png]]
   ![[HabilitarEmailEnReinoKeycloak-Paso1.png]]
3. En las configuraciones de la cuenta de Gmail, activar la verificación en dos pasos (es necesario para el siguiente paso)
   ![[ActivarYConfigurarVerificacionEnDosPasosEnGoogle.png]]
4. Habilitar contraseña de aplicación para que pueda usar las funciones de la cuenta de Gmail. (https://myaccount.google.com/apppasswords)
   ![[HabilitarPasswordDeApplicacionEnReinoKeycloak.png]]
5. Vincular contraseña proveída por Google en Keycloak
   ```bash
   Host: smtp.gmail.com
   Port: 587
   Encryption: Enable StartTLS
   Authentication: Enable
   Username: correoElectronicoAdmin
   Password: passwordApplicacionProvedidaPorGoogle
   ```
   ![[HabilitarEmailEnReinoKeycloak.png]]
