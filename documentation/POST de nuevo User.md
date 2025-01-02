PERMISOS NECESARIOS: debe estar autenticado (logueado a través de #Keycloak). 

Ahora que ya contamos con las bondades de Keycloak como servidor de autorizaciones podemos pensar la forma en que un nuevo usuario se crea.

Como la identidad la provee el servidor (ya sea creando un nuevo usuario por sus medios o a través de otros IdentityProviders) podemos confiar en que a través de la Anotación de Java "@Authenticated" nos aseguramos de que los usuarios que soliciten un POST al endpoint /users estarán autenticados y tendrán en su Access Token los Claims que nos permiten persistirlo y mapearlo a nuestra entidad User que será luego persistida en la DB que utiliza la API Ciudadano Consciente. Este mapeo es el siguiente:
1. user.authServerId = userInfo.getSubject();  
2. user.username = userInfo.getName(); 
3. user.email = userInfo.getEmail();

Entonces, ya no es necesario tener un DTO de creación de Usuario (DTOCreateUser) que sea recibido como bodyParam en el recurso. Ahora simplemente:
1. Verificamos que estos campos existan en el AccessToken.
2. Verificamos que el usuario no exista ya. Aquí hay que tener cuidado y tratar de tener lo más sincronizado posible Keycloak con la API para evitar que se genere en KC y lo rebotemos en la API. Es importante verificar los escenarios en que se hay conflictos con los emails y los usuarios e identificar cual usamos como identificadores.
3. Pasamos los datos al ServiceUser para que persista al nuevo usuario.

```java

// ******************** RESOURCE 
@Authenticated  
@POST  
@Operation(summary = "Create a new User.")  
@APIResponse(responseCode = "201", description = "User successfully created.", content = @Content(schema = @Schema(implementation = DTOUser.class)))  
@APIResponse(responseCode = "400", description = "Failed to create User. Verify 'Warning' Header.")  
@APIResponse(responseCode = "500", description = "Failed to create User. Verify 'Warning' Header.")  
public RestResponse<DTOUser> create() {  
  
  UserInfo userInfo = securityIdentity.getAttribute("userinfo");  
  
  String authServerId = userInfo.getSubject();  
  String username = userInfo.getPreferredUserName();  
  String email = userInfo.getEmail();  
  if (!utilityVerifyRequestField.isValidField(authServerId) ||  
          !utilityVerifyRequestField.isValidField(username) ||  
          !utilityVerifyRequestField.isValidField(email)) {  
    throw new HttpBadRequestException("Missing mandatory Claims (sub, preferred_username, email) from Access Token.");  
  }  
  
  audit.debug("KC-ID: " + authServerId); // keycloak.user_entity.id  
  audit.debug("KC-PUN: " + username); // keycloak.user_entity.username  
  audit.debug("KC-EMAIL: " + email); // keycloak.user_entity.email_constraint  
  
  audit.debug("Creating User...");  
  DTOUser user = serviceUser.createUser(authServerId, username, email);  
  
  audit.debug("Creating URI...");  
  URI uri = URI.create(BASE_PATH_RESOURCE + user.getUserId());  
  
  return RestResponse.ResponseBuilder  
          .create(RestResponse.Status.CREATED, user)  
          .location(uri)  
          .build();  
  
}

// ******************** SERVICE
@Transactional(Transactional.TxType.REQUIRED)  
public DTOUser createUser(String authServerId, String username, String email) {  
  
    audit.debug("Creating new User.");  
    User user = new User(authServerId, username, email);  
  
    audit.debug("Saving User with authServerId " + authServerId + ".");  
    try {  
        accessUser.save(user)  
                .orElseThrow( ()-> new HttpInternalServerException("Failed to persist new User."));  
    } catch (ConstraintViolationException e) {  
        audit.debug("User already exists: " + e.getErrorMessage());  
        throw new HttpBadRequestException("User already exists: " + e.getErrorMessage() );  
    }  
  
    audit.debug("Mapping EntityType into DTO.");  
    return mapperUser.entityToDto(user);  
  
}


```