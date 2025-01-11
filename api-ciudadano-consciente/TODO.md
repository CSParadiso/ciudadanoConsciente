# DONE
- Commit fe565c2 
  - 1 Cambiar los 200 por 201 cuando se crea con éxito el POST y anexar el header location con la url del recurso creado
  - 2 Eliminar las verificaciones de null en los DELETE (el único campo viene por PATH
  - 3 Corroborar que las references sean consistentes (Nivel no sea padre de sí mismo en NIVEL)
  - 4 Corroborar que las references sean consistentes (Unicidad de title en el mismo level en REFERENCIA)
  - 5 Asignar Rol a Usuario en Nivel(TABLA users_roles_levels) en NIVEL

- Commit 639bb55 
  - 6 Generar Tipos de Actividades (POST y GETall)
  - 7 Unir URL del Tipo de Actividad (template y parameterModel viven en la misma carpeta remota- Hablado con Fede.)

- Commit fa800e3
  - 8 Añadida Redirección a FrontEnd (localhost:8080/app lleva a localhost:5173)

- Commit 7ed776e 
  - 9 Generar Tipos de Actividades (GET, DELETE y PATCH)

- Commit 0d96db6 
  - 10 Refactor: cambiar completamente a idioma inglés (Activity Type entero, UtilityVerifyRequestField y App). 
  - 11 Refactor: cambiar PATCH (las verificaciones deben ser exhaustivas en el servicio) de todas las entidades (Activity Type).

- Commit ed05cbf 
  - 12 Refactor: PATCH (las verificaciones deben ser exhaustivas en el servicio) de todas las entidades (Level entero)
  - 13 Refactor: cambiar completamente a idioma inglés (Level entero)

- Commit 3df07c9 
  - 12 Refactor: PATCH (las verificaciones deben ser exhaustivas en el servicio) de todas las entidades (Organization entero)
  - 13 Refactor: migrado completamente a idioma inglés Organization 
  - 14 Feat: Generada funcionalidad UserRoleOrganization desde el lado de la Organization (faltan las capas desde su punto de vista)

- Commit d5e00e3 
  - 15 Feat: Implementado el GETALL de UserRoleLevel en Level 
  - 16 Feat: Implementado el GETALL de UserRoleOrganization en Organization

- Commit 8d1d926 
  - 12 Refactor: PATCH (las verificaciones deben ser exhaustivas en el servicio) (Reference entero)
  - 13 Refactor: migrado completamente al idioma inglés Reference

- Commit 5a50f91 
  - 12 Refactor: PATCH (las verificaciones deben ser exhaustivas en el servicio) (Role entero)
  - 13 Refactor: migrado completamente al idioma inglés Role

- Commit 411462f 
  - 12 Refactor: PATCH (las verificaciones deben ser exhaustivas en el servicio) (User)
  - 13 Refactor: migrado completamente al idioma inglés User

- Commit db9b6a8 
  - 14 feat: Generar funcionalidad Actividades (CRUD completo)

- Commit d9e38c0 
  - 15 feat: Implementar en Level (para manipular UserRoleLevel):
    - POST /levels/{id}/users/{user}/roles/{role},   --> asigna rol a usuario en nivel
    - GET /levels/{id}/users,                        --> obtiene los usuarios y sus roles en el nivel 
    - GET /levels/{id}/users/{user}/roles/{role},    --> obtiene (si existe) el rol del usuario en el nivel 
    - DELETE /levels/{id}/users/{user} y             --> elimina todos los roles de usuario en nivel 
    - DELETE /levels/{id}/users/{user}/roles/{role}  --> elimina un de usuario en nivel

- Commit 0a6e51a 
    - 16 feat: 
  - GET level/{id}/user{user} --> obtener roles de un usuario en el nivel ,
  - GET level/{id}/roles{role} --> obtener usuarios de un nivel con cierto rol y
  - PATCH level/{id}/users/{user}/roles/{role} --> actualización de rol de usuario en nivel

- Commit a85abf0 
  - 17 refactor: DELETE de LEVEL, ACTIVITY, ACTIVITY-TYPE, ORGANIZATION, REFERENCE, ROLE, USER para que retorne la entidad eliminada

- Commit b38286b 
  - 18 refactor: verificaciones de campos (migradas al recurso) de todas las entidades

- Commit 8fa4344 
  - 19 refactor:
    - GET /levels/{id}/roles/{role} --> /levels/{id}/users/roles con {role} y {user} como QueryParams (porque modifica el resultado. Un rol específico.)
    - DEPRECATED (merged en otros):
      - GET /levels/{id}/users
      - GET /levels/{id}/users/{user}
      - GET /levels/{id}/users/{user}/roles/{role}
      - POST /levels/{id}/users/{user}/roles/{role} --> /levels/{id}/user/roles con {user} y {role} como BodyParam
      - PATCH /levels/{id}/users/{user}/roles/{role} --> /levels/{id}/user/roles con {user} y {role} como BodyParam

- Commit b897396 
  - 20 feat: GET, POST, PATCH, DELETE de UserRoleOrganization en Organization

- Commit 931eea3 
  - 21 feat: Generar Status de Respuestas (constantes estandarizadas del estado de la respuesta. Categorización nominal.)

- Commit 19a7261
  - 22 feat: Generar Respuestas

- Commit 5d90406
  - 23 feat: Generar Concerns (antes denominadas Questions)

- Commit b43d052
  - 24 feat: añadida a Concern una url asociada para explayar o ejemplificar (solo haría falta un campo en la entidad)

- Commit 51d2a4e
  - 25 feat: añadido creator(user) a ActivityType.

- Commit 67e7498
  - docs: actualizar documentación DER(cuarta iteración)

- Commit 89cdefb
  - 26 feat: Generar Entidades

- Commite 9ac671
  - Migrar de Entity a EntityType

- Commit d24f60d
  - 27 feat: Generada entidad en Votos (GETall, GET, PATCH en Vote)
  - 28 feat: Generada POST de votos en entidades con User como Body Param (Organization)

- Commit fbf2af2
  - 29 feat: Generar GET votos en Entidades (Organization)
  - 30 feat: Generar POST de votos en entidades con User como Body Param (Level, ActivityType, Concern, Reference)

- Commit d5871c0
  - 31 refactor: Corroborar si las verificaciones numéricas es mejor hacerlas con el compareTo que con el != 
  - 32 feat: Generar GET votos en User

- Commit 2bd66d5
  - 33 refactor de las excepciones creando el transferible de acuerdo a 'problem details' RFC7807 (https://www.rfc-editor.org/rfc/rfc7807.html)

- Commit 9ab5e48,fdb96a9
  - Leer sobre autentication y authorization.
  - Levantar servidor Keyckloac

- Commit 5152710
  - feat: Verificar que existan los archivos que requerimos (README.md, template.js, model.json y thumbnail.png) en el repo fetcheado.

- Commit 42d302b
  - feat-refactor: Agregar githubUser, githubRepo y githubPath, githubShaModel, githubShaTemplate, githubShaReadme y githubShaThumbnail en  reemplazo de functionalTemplateUrl.

- Commit 3825680
  - refactor: Modificar AnswerStatus a ActivityTypeVersionStatus en DB
  - 'staged': el programador suscribe o actualiza la versión
  - 'approved': aprobada por CIUCO, programador debe aceptar publicarla
  - 'rejected': no aprobada por CIUCO.
  - 'published': el programador expone al mundo su template 
  - refactor: Modificar columna status en tabla Answer para que sea solo booleano. 
  - refactor: Modificar atributo status en entidad Answer en API 
  - refactor: Cambiar entidad AnswerStatus a ActivityTypeVersionStatus en API

- Commit 6923337 
  - feat: Añadir activity_type_version a la BD (con trigger a función de autoincremento para versiones del mismo nivel). 
  - feat: Añadir version a lógica de entidades Votables (DB y API)
  - refactor: Migrar a VERSION como entidad todos los campos de Github de ActivityType. Además, un Date de creado y un Date de última modificación de estado.

- Commit a035353
  - feat: Añadida restricción de unique para el conjunto de los campos de github de una versión (en la DB y en la API). 
  - refactor: Modificar endpoints de ActivityType y ActivityTypeVersion para corroborar coherencia con DB y modelo.

- Commit a204b05
  - feat: Agregado un estado NOT_PUBLISHED que se dá cuando la versión ha sido aprobada pero por alguna razón no ha sido publicada. 
  - feat: Agregado fetch a metadata de VersionServer desde la API (backend). Ahora las verificaciones de los archivos necesarios para crear una nueva versión de ActivityType suceden en el back. 
  - feat: Agregadas entidades VersionServer y FileNameRequired (tanto en la DB como en la API). Esto permite abstraer aún más las peticiones del front y permitir nuevos servidores de versiones. 
  - feat: Verificada la creación de versiones únicas desde Github a partir de la metadata. 
  - feat: Agregado recurso para recuperar todas las versiones con status como opcional. 
  - feat: Desacoplado a su propio directorio el uso de apis externas para mantener un orden más coherente en los directorios y poder modularizar con mayor granularidad.

- Commit 792dba5
  - feat: Recuperar archivos puntuales de una versión (ESTO SE REALIZA DESDE EL FRONT IGUAL) a partir del commit de la version y otros datos.

- Commit 51a0e56
  - refactor: Verificación de archivos necesarios y recupero de los mismos en un solo endpoint (por lo menos para Github). Eliminados los campos metadata_url y download_url de VersionServer.
  - refactor: eliminada la columna branch de Activity Type Version.

- Commit 1c71bb6
  - feat: Creada en DB las tablas:
    - Content(model jsonb, activity_type_version ActivityType)
    - Images(imageName String, imageBlob bytea y content Content)
  - feat: Creada en db la fila ActivityTypeVersion con id 1 para que sirva de fallback cuando se hace el default si se borra en Content.

- Commit eebb9df
  - feat: Creada en DB las tablas:
    - Image(name varchar, content Content) FALTA AGREGARLO A LA API 
  - feat: Configuration property. Imágenes persistidas en FileSystem (en directorio configurable) - UtilityFileSystem 
  - feat: Configuration property. Seteo de tamaño máximo de archivos (en MBytes)
  - feat: Agregado de campos (extension, mimeType y alias) a la tabla file_name_required y su correspondiente modificación en la API. Esto responde a un poco más genéricos métodos, sin ataduras a los nombres hardcodeados de los archivos necesitados. 
  - refactor: retornar el contenido (excepto la imagen) cuando se crea la version del activityType. 
  - docs: Documentar diagrama Drawio 
  - docs: Documentar DBCreation script 
  - feat: creada utilidad UtilityFileSignature

- Commit 43ae315 
  - Diseñando Subscripción de ActivityType por parte de LA ORGANIZACIÓN. 
  - feat: Creada entidad Content, DTO Content, DTOCreate Content, DTOUpdateContent.}
  - feat: Método POST, GET, GETALL, DELETE, PATCH de Content.
  - feat: Creada entidad Image, DTO Image, DTOCreate Image, DTOUpdateContentImage.
  - feat: Método POST, GET(image File), GETALL(imageDTO) de Image (en recurso de content), PATCH
  - feat: Método GET(model.json FILE) en recurso Content
  - feat: Salvado al filesystem los archivos de imágenes. Salvados en la DB la metadata de la imágen.
  - bug: Ahora al eliminar el ActivityTypeVersion se elimina también el thumbnail asociado.
  - bug: Añadida fila fallback en ActivityTypeVersion para foreign keys que on delete set default (de esta manera la fila queda y referencia a una versio eliminada)
  - feat: Generar Votes de Content

- Commit 4717384
  - refactor: ActivityTypeVersion no se eliminan físicamente sinó que cambian su estado a DELETED (y no se pueden crear nuevos contenidos pero sobreviven los que ya están).
  - refactor del recurso y servicio del ActivityTypeVersion
  - bug: Resuelto el mapeo nulo del campo activityTypeVersion en el Content
  - feat: Crear distintos directorios para las imágenes de cada contenidos
  - refactor: Agregar Constante en las Entidades votables con el nombre de la entidad en la DB
  - feat: agregar Content a Activity
  - feat: recuperar template del content asociado a la Activity

- Commit 14b4bce
  - build: Construir imagen de Postgres poblada con datos

- Commit 2c5020a
  - refactor: modificado el recurso de Concern
  - build: creado el Dockerfile, compose y bakups que permiten levantar el proyecto entero con una imagen
  - build: generada y pusheada la primera imagen de la api a dockerHub (csparadiso/ciudadano-consciente:API1.0)

- Commit da3a297
  - refactor: modificado el compose.yaml para poder seleccionar el directorio de imágenes del filesystem donde queremos que se almacenen los archivos de imágenes.

- Commit a62e881
  - feat: añadido recurso que recupera todos los childrens de un level.
  - feat: añadido recurso que recupera todos los paths y sus votos
  - feat: añadido recurso que recupera la actividad de un level

- Commit 60d3a99
  - feat: añadido recurso que recupera los paths votados por un usuario. (Basado en votos)
  - feat: añadido recurso que recupera los paths usados recientemente por un usuario. (Basado en respuestas)
  - refactor: modificados varios transferibles.

- Commit dc17039
  - feat: añadido recurso que recupera todas las respuestas de un nivel y de sus hijos

- Commit 3f7f2af
  - feat: añadido archivo de loggeo
  - feat: personalizado un poco el index.html de Quarkus
  - feat: añadido recurso de POSTeo de respuestas en BATCH mode (pero se DEPRECA por no corresponder a una buena práctica)
  - feat: añadida interfaz Taggable
  - feat: generar Tags (etiquetas)

- Commit 9a1c4e1
  - feat: añadida interfaz Votable
  - feat: creado el recurso de obtener levels por usuario y rol
  - refactor: unificados los votos en el recurso '/votes/{userId}/{entityTypeId}/{entityId}'.
  - refactor: deprecados todos los recursos y servicios de votos en cada entidad.
  - refactor: modificadas las HttpNotFoundException por HttpNoContentExpception
  - refactor: modificados los ApiResponse (eliminados los 404 y los duplicados de 204)
  - refactor: modificados los Rest por RestResponse<DTOActivity> en el recurso Activity
  - feat: añadidas las anotaciones de content en ApiResponse del recurso Activity

- Commit 390ceff
  - feat: añadir recurso para obtener organizations de un user a través del id del user
  - feat: modificada entidad (en modelo y en DB). El content ahora tiene un creator (User not null),
  un determinante de si es publico o no (booleano not null) y una organización vinculada (opcional).
  (resta la lógica de protección del borrado y la modificación)

- Commit cad5cc7
  - refactor: desde Response a RestResponse<T> en ActivityType, ActivityTypeVersion, ActivityTypeVersionStatus,
  Answer, Concern y Content.

- Commit e8a8d87
  - refactor: desde Response a RestResponse<T> en EntityType, Level, Organizations, Reference, Role. Tag, Tagged, User, UserRoleLevel, Vote.
  - feat: añadir a todas las APIResponse anotations el content schema de la response

- Commit 158f3ce
  - feat: añadir vistas de entidades votables en DB (voted_[organizations | levels | activity_types | concerns | references | activity_type_versions | contents])
  - feat: añadir vistas de entidades taggeables en DB (tagged-[organizations | levels | activity_types | concerns | references | activity_type_versions | contents])
  - feat: agregar modelo, accesor, transferible, transformador y recurso de vistas de las voted entities
    - añadir transferible y transformador genérico de vistas de entidades votables en API (DTOVotedEntity, MapperVotedEntity)
    - añadir modelo, accesor y recursos GET_ALL, GET de vistas de entidades votables en API (Organization, Level, ActivityType, Concern, Reference, ActivityTypeVersion, Content)
  - feat: añadir tabla de rachas (random_streak) en DB para el MODO RANDOM. Las rachas son  de answers a contents random.
  ###### CHANGELOG 158f3ce:
  - Ahora existen endpoints para consultar los votos de entidades:
    - tipoEntidad/votes --> GET ALL VOTES
    - tipoEntidad/{id}/votes --> GET ALL VOTES FOR SPECIFIC ENTITY

- Commit 9c4f6f1
  - feat: agregar modelo, accesor, transferible, transformador y recurso de vistas de las tagged entities
    - añadir transferible y transformador genérico de vistas de entidades tageables en API (DTOTaggedEntity, MapperTaggedEntity)
    - añadir modelo, accesor y recursos GET_ALL, GET de vistas de entidades taggeables en API (Organization, Level, ActivityType, Concern, Reference, ActivityTypeVersion, Content)
  ###### CHANGELOG 9c4f6f1:
  - Ahora existen endpoints para consultar los tags de entidades:
    - tipoEntidad/tags --> GET ALL TAGS
    - tipoEntidad/{id}/tags --> GET ALL TAGS FOR SPECIFI ENTITY

- Commit 80872f7
  - feat: añadir modelo, accesor, transferible, transformador y recurso de RandomStreak
  ###### CHANGELOG 80872f7:
  - Ahora se puede registrar la racha de un usuario en MODO RANDOM:
    - streak/random/users --> GET ALL RANDOM STREAKS
    - streak/random/users/{userId} --> POST, GET y PATCH RANDOM STREAK FOR SPECIFIC USER
    - streak/random/{id} --> GET SPECIFIC RANDOM STREAK

- Commit 30eac98
  - refactor: eliminar el campo "last_modified" de las answers de la DB y de la API (del modelo, de los transferibles y servicios asociados).
  - refactor: deprecar el PATCH de Answers y sus transferibles y métodos asociados
  ###### CHANGELOG 30eac98:
  - Ahora las respuestas no se pueden actualizar, sólo se generan.
  - Los transferibles de Answer ya no tienen el campo "lastModified", tampoco el endpoint GET /levels/paths/recently/users/{userId}

- Commit 3ed15a9
  - feat: añadir endpoint para recuperar respuestas de un usuario en un nivel y sus hijos
  ###### CHANGELOG 3ed15a9:
  - Ahora se puede recuperar las repuestas de un usuario en un PATH ('/answers/levels/{levelId}/users/{userId}/childrens')

- Commit a18c1d9
  - feat: Recuperar user a partir de email o username (endpoints 'users/[username | email]/{username | email}')
  ###### CHANGELOG a18c1d9:
  - Ahora se puede recuperar usuarios por username o email:
    - users/username/{username} --> USER
    - users/email/{email} --> USER

- Commit 4053926
  - refactor: para tener fecha y hora, migrar todas las fechas a timestamp with time zone en la DB
  - refactor: para tener fecha y hora, migrar todas las fechas a OffsetDateTime en la API
    - stagedDate de DTOActivityTypeVersion
    - lastModifiedStatusDate de DTOActivityTypeVersion
    - created de DTOAnswer
    - created de DTOAnswerOfChildrens
    - date de DTOConcern
    - created de DTOLevelPathUsedRecentlyByUser
    - date de DTOVote
    - stagedDate de ActivityTypeVersion
    - lastModifiedStatusDate de ActivityTypeVersion
    - created de Answer
    - date de Concern
    - date de Vote
    - DTO Voted Entity
    - Voted{Entity}
    ###### CHANGELOG 4053926:
    - Ahora todas las fechas de la API tienen además hora y timezone (GMT -3)
    - Ahora la DB tienes timezone

- Commit b95d6cb
  - refactor: un User solo puede tener un Role en una Organization en DB(unique constraint) y API(unique constraint del modelo, recursos: post, patch y delete, servicios y accesor)
  - refactor: un User solo puede tener un Role en un Level en DB(unique constraint) y API(unique constraint del modelo, recursos: post, patch y delete, servicios y accesor)
  - docs: añadido directorio 'configurationChanges' para scripts con eventuales cambios de configuraciones. Se añade un archivo inicial.
  ###### CHANGELOG b95d6cb:
  - A partir de ahora los usuarios solo pueden tener un solo Rol en una Organización
  - A partir de ahora los usuarios solo pueden tener un solo Rol en un Nivel
  - Consideración: Verificar combinaciones de Roles en Organización y Levels

- Commit f54ae1e
  - refactor: casteado correctamente la fecha cuando se la recupera desde la DB a través de una nativeQuery (es necesario que de Instant pase a OffsetDateTime)
  - refactor: modficados los nombres de las vistas en db y en los correspondientes lugares en la api. Ahora comienzan con "v_nombre_vista"
  - feat: creada función en la DB get_ancestor(int) -> recupera el ancestro (path) de un nivel especifico. Retorna nivel ancestro
  - feat: creada función en la DB get_genealogy(int) -> recupera el árbol genealógico de un nivel especifico. En tuplas(padre, hijo)
  - feat-core: ahora todos los recursos están autenticados.
  - feat-core: creados dos clientes para keycloak (api-ciudadano-consciente y desa-api-ciudadano-consciente)
  - feat: añadido Filtro Web que loguea cliente de Keycloak que hace la request HTTP, el username y el id del usuario de Keycloak
  ###### CHANGELOG f54ae1e:
  - Ahora todos los recursos requieren autenticación (por ahora dos clientes: api-ciudadano-consciente y desa-api-ciudadano-consciente)

- Commit: 40c6c51
  - feat: añadido cliente Rest del servidor de Keycloak usando las credenciales del servicio.
  - refactor: modificado el nombre del campo 'pass_word' de User a 'auth_server_id' en la DB.
  - refactor: modificado el nombre del campo 'password' de User a 'authServerId' en la API.
  - refactor: modificado en la DB la cantidad de caracteres de las columnas 'email' y 'user_name'. De 100 a 255.
  - refactor: modificado el recurso POST User. Suprimido el bodyParam DTOCreateUser. Los datos vienen en el Access Token
  - refactor: modificado ExceptionMappers. DTOException muestra en el campo 'detail' el mensaje de error de la Excepción.
  - refactor: modificado el recurso PATCH User. Modificado el bodyParam, desde DTOUpdateUser a DTOUpdateUserAuthServerProvider
  - refactor: modificado el recurso DELETE User. El usuario solo puede ser eliminado por un Administrador Authenticado o por sí mismo.
  - refactor: modificado el recurso GET-VOTES User. Solo el propio usuario y un Administrador pueden los votos del usuario.
  - feat: añadido el campo "description" a la entidad "Role", tanto en la DB como en la API.
  - refactor: modificado los recursos GET-GET ALL, PATCH y POST de Role. Los roles solo pueden ser creados, modificados y eliminados por el admin de Ciuco. 
  ###### CHANGELOG:
  - Ahora el POST del Usuario se hace solamente con el AccessToken proveído por Keycloak.
  - Ahora en el campo 'detail' del DTO de las Excepciones vá el mensaje de ERROR de la Excepción.
  - Ahora el PATCH del Usuario solo se usaría si eventualmente se cambia de IdentityProvider, solo se cambia el valor del atributo 'authServerId'.
  - Ahora el DELETE del Usuario solo lo puede realizar un admin o el propio usuario a través de sus AccessToken.
  - KEYCLOAK: se crea un cliente llamado 'desa-api-ciudadano-consciente' en el cual se crea un rol de cliente llamado 'desa-admin'. A su vez se crea un grupo llamado 'CLIENT-Administrator' el cual tiene mapeado el rol 'Ciuco-Admin'. El grupo tiene un subgrupo llamado 'desa' que hereda los permisos del padre. Esto permite agregar usuarios al grupo y que estos usuarios tengan los roles del grupo. Con estos roles podemos permitirles acceder o no a determinados recursos.
  - Ahora los enpoints de los Roles están protegidos para con el rol Ciuco Admin.

- Commit 8dbce68:
  - refactor: GET y GET ALL ahora está protegido (solo Ciuco-Admin)
  - refactor: GET VotesByUser ahora está protegido (solo puede recuperar sus votos el usuario y Ciuco-Admin). Este
  - recurso se ha movido desde el recurso User (porque lo que se recupera son los votos).
  - refactor: PATCH Vote ahora está protegido (solo el usuario puede cambiar su voto)
  - refactor: POST Vote ahora está protegido y se depreca el endpoint que lleva el userId en el path
  ###### CHANGELOG:
  - Ahora POST y PATH se realizan con el Access Token 

- Commit 3400cfb:
  - refactor: ahora los endpoints del recurso ANSWERS están protegidos. El POST se hace con el AccessToken y los GET requieren autorización de rol Ciuco-Admin o de Moderador o Divulgador. Esto exiige que se verifique lógicamente que el Moderador o Divulgador tenga ese rol en ese nivel o en el padre (get_genealogy).

- Commit 6430a6b:
  - feat: endpoint de asignar y remover role a usuario en ORGANIZACIÓN en KEYCLOAK API REST
  - refactor: puentear servicio de Ciuco para que pase por KEYCLOAK para asignar y remover roles en ORGANIZACIÓN
  - feat: endpoint de asignar y remover role a usuario en LEVEL en KEYCLOAK API REST
  - refactor: puentear servicio de Ciuco para que pase por KEYCLOAK para asignar y remover roles en LEVEL

- Commit 4a4cc14:
  - refactor: utilización de atributos del access token para asignar y remover roles de Moderador y Divulgador en Organizaciones y Niveles. Las verificaciones en los recursos utilizan esta característica.

- Commit 89088eb:
  - fix: anotaciones removidas y verificaciones instaladas por FEDE.

- Commit c78ec72:
  - refactor: Añadidas anotaciones de codigo HTTP API RESPONSE a algunos endpoints
  - docs: Añadidos javadocs y documentación sobre protección de endpoints, vinculaciones de Identity Providers de Keycloak y algo de la estructura del modelo de negocio concerniente a las actividades, versiones, contenidos, etc.
  - IMAGE BUILD: api-rest-admin-keycloak (1.0.1) y api-ciudadano-consciente (1.2.1)

- Commit :
  - refactor: modificado el DTOUserRoleOrganization (ahora el campo user retorna el DTO del usuario)
  - refactor: modificado el DTOUserRoleLevel (ahora el campo user retorna el DTO del usuario)
  - refactor: modificado el endpoint GET organizations/{id}/users/roles (ahora solo Ciuco y moderador de la ORG puede recuperar los datos)
  - refactor: eliminada restricción unique en user.username en la DB y en la API
  - feat: añadido endpoint de modificación de username de usuario (PATCH /users/{id})
  - refactor: modificado el endpoint GET /organizations/users (ahora solo el propio usuario puede recuperar sus ORG)
  - refactor: modificado el endpoint PATCH /organizations/update (ahora solo CIUCO y el moderador de la ORG pueden actualizar email y descripcion)
  - refactor: modificado el endpoint GET /organizations/{id}/votes (ahora solo CIUCO y el moderador de la ORG pueden recuperar los datos)
--- 

---
### COMMIT MESSAGES RESUME
    - feat – a new feature is introduced with the changes
    - fix – a bug fix has occurred
    - chore – changes that do not relate to a fix or feature and don't modify src or test files (for example updating dependencies)
    - refactor – refactored code that neither fixes a bug nor adds a feature
    - docs – updates to documentation such as a the README or other markdown files
    - style – changes that do not affect the meaning of the code, likely related to code formatting such as white-space, missing semi-colons, and so on.
    - test – including new or correcting previous tests
    - perf – performance improvements
    - ci – continuous integration related
    - build – changes that affect the build system or external dependencies
    - revert – reverts a previous commit
---

---
### CUESTIONES DE DISEÑO
##### API de ADMIN separada
- Quizás sea necesario una nueva API de ADMINISTRACIÓN que nuclee los endpoints relacionados a gestion
    - ENDPOINTS DE ENTIDADES NOMINALES: STATUS de ActivityTypeVersion, EntityTypes (Deberían crearse automáticamente)
##### ASINCRONÍA
- El siguiente punto debe revisarse, al igual que la persistencia de las imágenes en el filesystem
- Podríamos implementar asincronía en la compresión de los archivos de imágenes. Que el usuario siga manipulando su imagen mientras por debajo comprimimos al persistir.

---
# TODO 

MANEJO DE USUARIOS Y ROLES
¿Qué sucede si un usuario tiene rol Moderador en Org pero Divulgador en Level?

---
# DOING 

CORE
TODO: feat: proteger Resources a través de Anotaciones @RolesAllowed y @Authenticated (User, Role, working en asignRoleOrganization y asignRoleLevel)

// TODO: feat ¿cómo comprender los roles inferiores dentro de lo superiores?
//

// TODO: feat: las modificaciones del content las hacen los creators (si son public) y los moderadores (si tiene vinculada una org)
// TODO: El content ahora tiene un creator (User not null),
// un determinante de si es publico o no (booleano not null) y
// una organización vinculada (opcional).
// (resta la lógica de protección del borrado y la modificación)

TODO refactor: si se elimina un activityTypeVersion su voto aún sigue existiendo (el id 22 no existe pero tiene votos)


// TODO Verificar referencias eliminadas en cascade para ver si se pueden usar los estados en lugar de borrar
// TODO DETERMINAR ALGORITMO DE COMPRESION EN FILESYSTEM


Diseñando Subscripción de ActivityType por parte del Dev.
// TODO refactor: si el DEV hace submit de su activityType, entonces podemos exigirle los campos fijos del model que exijamos y hacer nosotros el model.json con el resto de los datos libres que necesite
// TODO feat-refactor: PERSISTIR LO DE GITHUB. persistir version (esto implica modificar la entidad y desacoplarla de Git). Simplemente queda la opción de subirla desde public repo en Github.
// TODO feat: Anunciar los archivos que la versión del activity type no encuentra.
// TODO El archivo .js debería ser capaz de retornar ok o ko para que sepamos si la respuesta fué correcta o no. Quizás un porcentaje de completado de la actividad.
// AQUÍ ENTRA EL JUEGO EL CONTEXTO DE EJECUCIÓN DE ESE ARCHIVO 


---
# FIXME?

---
# OPTIMIZACIONES

// FIXME refactor: verificar nivel de logueo apropiado. Ahora todos los logs son DEBUG
// FIXME refactor: modificar los DTO de Levels para recuperar solo los datos que se necesitan
// FIXME refactor: match up DB restrictions with api restriction anotations
// FIXME refactor: hacer más genérico el voto de las entidades
// FIXME refactor: hacer clase por cada file type para hacer genérico el recurso get y el servicio que invoca
// FIXME Aplicar algoritmo para reducir al mínimo los estados de las versiones. (Algoritmos de las máquinas de estados finitos de Fundamentos de la Informática).

// FIXME Refactor de los DTO considerando si devolver las entidades hijas o sus identificadores
// FIXME Refactor Quizás sea necesario agregar la anotación @MappingTarget en argumento de la clase que es actualizada https://mapstruct.org/documentation/stable/reference/html/#updating-bean-instances
// FIXME refactor: los URI creados por los post de Level y Organizacion deberían retornar /levels/{id}/users/{user}/roles/{role} y no /levels/{id}/?users={user}&roles={role}
// FIXME refactor: de la mano del anterior. El recurso /levels/{id}/user/roles quizás no debería aceptar los query como identificadores, sinó como seleccionadores.
// FIXME refactor: los PATCH deberían quizás consumir el tipo de MEDIATYPE merge-patch-json y no json. Habría que modificar la implementación.
// FIXME refactor: quizás no es necesario el user en el PATCH de Concern o quizás lo sea necesario en el DELETE también (unificar criterio)
// FIXME refactor: quizás se pueda usar las restricciones UNIQUE de la DB para atrapar las excepciones cuando se quiere crear o actualizar una entidad y no andar comprobando antes si existe ese campo único. (Esto incrementa en uno cada el id de la entidad, incluso los fallidos)

// FIXME Verificar Recursos que aceptan DTOs que no haya campos vacios de ids u otros campos.

// TODO Verificar en la BD que hacer con el caso de ActivtyType null en Activity (quizás es obligatorio adaptar el dataset al activity type). En este sentido podría abrirse el espacio para que las organizaciones sugieran ActivityTypes que les gustaría usar.
// TODO feat: Crear registro id 0,1 hardcodeado para todas las entidades que en cascade se setean a su default 0,1

// TODO feat: determinar entidades votable s programticamente

---

# EXTRA FEATURES
// TODO Repositorios privados (simplemente deberían crear un fine graned token que permite acceso al contenido del repo.
// No se puede hacer por carpeta creo, que sería lo ideal). Esto conlleva que debamos almacenar el token
// (que tiene fecha de expiración). https://github.com/settings/personal-access-tokens/new

// TODO Servidor de versiones local

---

# ENTREGA FINAL
// TODO Ir haciendo la presentación con cosas que hicimos y no están en la especificación de requerimientos y cosas a futuro que se podrían implementar.
// TODO Corregir documentación en google docs

--- 
# INICIO SECCIÓN PERMISOS

|METODO|Endpoint|Roles Permitidos|Notas|Operation|
|---|---|---|---|---|
|GET|activities|@Authenticated|x|Retrieve all Activities.|
|GET|activities/{id}|@Authenticated|x|Retrieve a specific Activity by its ID.|
|GET|activities/{id}/template|@Authenticated|x|Retrieve the template of a specific Activity.|
|GET|activities/level/{levelId}|@Authenticated|x|Retrieve the Activity of a Level.|
|POST|activities|@Authenticated|x|Create an Activity.|
|PATCH|activities/{id}|@Authenticated|x|Update an Activity.|
|DELETE|activities/{id}|@Authenticated|x|Delete a specific Activity by its ID.|
|---|---|---|---|---|
|GET|activity-type|@Authenticated|x|Retrieve all Activity Types.|
|GET|activity-type/{id}|@Authenticated|x|Retrieve a specific Activity Type by its ID.|
|POST|activity-type|@Authenticated|x|Create an Activity Type.|
|PATCH|activity-type/{id}|@Authenticated|x|Update an Activity Type.|
|DELETE|activity-type/{id}|@Authenticated|x|Delete a specific Activity Type by its ID.|
|POST|activity-type/{id}/votes|@Authenticated|@Deprecated|Vote Activity Type.|
|GET|activity-type/votes|@Authenticated|x|Retrieve votes of Activity Types.|
|GET|activity-type/{id}/votes|@Authenticated|x|Retrieve votes of a Activity Type.|
|GET|activity-type/tags|@Authenticated|x|Retrieve tags of all Activity Types.|
|GET|activity-type/{id}/tags|@Authenticated|x|Retrieve tags of a ActivityType.|
|---|---|---|---|---|
|GET|activity-type-version/activity-type/{activity-type}|Authenticated|QueryParam("status")|Retrieve all Versions of a Activity Type.|
|GET|activity-type-version/{id}|Authenticated|QueryParam("status")|Retrieve all Versions (optional: status)|
|GET|activity-type-version/{id}|Authenticated|x|Retrieve a specific Version of an Activity Type by its ID.|
|GET|activity-type-version/{id}/{filename}|Authenticated|x|Retrieve a specific Version of an Activity Type by its ID.|
|POST|activity-type-version|Authenticated|x|Retrieve the content of a specific Activity Type Version. THIS WILL BE MADE IN THE FRONT END, IN THE APP.|
|POST|activity-type-version/{server}|Authenticated|Deprecated|Create Activity Type Version. Require a version server provider (only github support initially).|
|POST|activity-type/{id}/votes|Authenticated|x|Create Activity Type Version. Upload local files.|
|PATCH|activity-type-version/{id}|Authenticated|x|Update the Status of an Activity Type Version.|
|DELETE|activity-type-version/{id}|Authenticated|x|Delete a specific Activity Type Version by its ID.|
|POST|activity-type-version/{id}/votes|Authenticated|Deprecated|Vote Activity Type Version.|
|GET|activity-type-version/votes|Authenticated|x|Retrieve votes of Activity Type Versions.|
|GET|activity-type-version/{id}/votes|Authenticated|x|Retrieve votes of a ActivityTypeVersion.|
|GET|activity-type-version/tags|Authenticated|x|Retrieve tags of Activity Type Versions.|
|GET|activity-type-version/{id}/tags|Authenticated|x|Retrieve tags of a ActivityTypeVersion.|
|---|---|---|---|---|
|GET|activity-type-version-status|Authenticated|NOMINAL (podría formar parte de una api de CIUCO ADMIN)|Retrieve all categories of Activity Type Version Status|
|GET|activity-type-version-status/{id}|Authenticated|NOMINAL (podría formar parte de una api de CIUCO ADMIN|Retrieve a category of Activity Type Version Status.|
|POST|activity-type-version-status|Authenticated|NOMINAL (podría formar parte de una api de CIUCO ADMIN|Create a category of Activity Type Version Status.|
|PATCH|activity-type-version-status/{id}|Authenticated|NOMINAL (podría formar parte de una api de CIUCO ADMIN|Update a category of Activity Type Version Status.|
|DELETE|activity-type-version-status/{id}|Authenticated|NOMINAL (podría formar parte de una api de CIUCO ADMIN|Delete a category of Activity Type Version Status.|
|NOTA|Podría|ser|endpoint|de otra api CIUCO-ADMIN|
|---|---|---|---|---|
|GET|answers|Ciuco-Admin|x|Retrieve all Answers.|
|GET|answers/levels/{levelId}/childrens|"Ciuco-Admin", "O-Moderator, O-Divulgator", "L-Moderator", "L-Divulgator"|x|Retrieve all Answers from a Level and his childrens.|
|GET|answers/levels/{levelId}/users/{userId}/childrens|x|Deprecated(since = "UserId should not be in path.")|Retrieve all Answers of a User from a Level and his childrens.|
|GET|answers/levels/{levelId}/childrens/user|Authenticated|x|Retrieve all Answers of a User from a Level and his childrens.|
|GET|answers/{id}|Authenticated|x|Retrieve a  Answer by its ID.|
|POST|answers|Authenticated|x|Create a Answer.|
|POST|answers/batch|x|Deprecated(since = "The request should be atomic, not for a Collection.")|Create Answers in Batch Mode.|
|PATCH|answers/{id}/status|x|Deprecated(since = "1.0.3. The answers should not be modified.")|Update Status of Answer.|
|NOTA|Las|respuestas|no se|borran|
|---|---|---|---|---|
|GET|concerns|Authenticated|x|Retrieve all Concerns|
|GET|concerns/{id}|Authenticated|x|Retrieve a  Concern.|
|POST|concerns|Authenticated|x|Create a Concern.|
|PATCH|concerns/{id}|Authenticated|x|Update a Concern.|
|DELETE|concerns/{id}|Authenticated|x|Delete a  Concern by its ID.|
|GET|concerns/{id}/[tags, votes]|Authenticated|x|Retrieve {tags || votes} of a Concern.|
|GET|concerns/[tags, votes]|Authenticated|x|Retrieve {tags || votes} of Concerns.|
|---|---|---|---|---|
|GET|contents|Authenticated|x|Retrieve all Contents.|
|GET|contents/{id}|Authenticated|x|Retrieve a specific Content.|
|POST|contents|Authenticated|x|Create a new Content for a Activity Type Version.|
|POST|contents/images|Authenticated|x|Add Images to Content.|
|DELETE|contents|Authenticated|x|Delete a specific Content by its ID.|
|GET|contents/{content}/images|Authenticated|x|Retrieve all Images from Content.|
|GET|contents/{content}/images/{id}|Authenticated|x|Retrieve a Image File from Content.|
|GET|contents/{content}/model|Authenticated|x|Retrieve a Model file from Content.|
|PATCH|contents/{id}|Authenticated|x|Update a Content.|
|PATCH|contents/images/{id}|Authenticated|x|Update a Image file from Content.|
|GET|contents/{id}/[tags, votes]|Authenticated|x|Retrieve {tags || votes} of a Content.|
|GET|contents/[tags, votes]|Authenticated|x|Retrieve {tags || votes} of Contents.|
|---|---|---|---|---|
|GET|entity-types|Authenticated|x|Retrieve all categories of Entities.|
|GET|entity-types/{id}|Authenticated|x|Retrieve a category of EntityType.|
|POST|entity-types|Authenticated|x|Create a category of EntityType.|
|PATCH|entity-types/{id}|Authenticated|x|Update a category of EntityType.|
|DELETE|entity-types/{id}|Authenticated|x|Delete a category of EntityType.|
|NOTA|Podría|ser|endpoint|de otra api CIUCO-ADMIN|
|---|---|---|---|---|
|GET|levels|Ciuco-Admin|x|Retrieve all Levels.|
|GET|levels/paths|Ciuco-Admin|x|Retrieve all Levels without parent.|
|GET|levels/{id}|Ciuco-Admin|x|Retrieve a  Level by its ID.|
|GET|levels/{id}/childrens|Ciuco-Admin|x|Retrieve all childrens of a Level by its ID.|
|GET|levels/organizations/{organizationId}/paths|Ciuco-Admin|x|Retrieve all Levels (without a parent) of an Organization by the Organization ID.|
|GET|levels/organizations/{organizationId}/users/{userId}/roles/{roleId}|Ciuco-Admin|x|Retrieve all Levels of an Organization where the user has a specific role.|
|GET|levels/paths/favorites/users/{userId}|Ciuco-Admin|x|Retrieve all Paths voted by a specific User.|
|GET|levels/paths/recently/users/{userId}|Ciuco-Admin|x|Retrieve latest Paths used by a specific User.|
|POST|levels|{"Ciuco-Admin", "L-Moderator", "L-Divulgator"}|x|Create a Level.|
|PATCH|levels/{id}|{"Ciuco-Admin", "L-Moderator", "L-Divulgator"}|x|Update a Level.|
|DELETE|levels/{id}|{"Ciuco-Admin", "L-Moderator"}|x|Delete a  Level by its ID.|
|GET|levels/{id}/users/roles|Ciuco-Admin|x|Retrieve Users with Role in Level.|
|POST|levels/{id}/users/roles|{"Ciuco-Admin", "L-Moderator"}|x|Assign Role to User in Level.|
|DELETE|levels/{id}/users/{user}/roles/{role}|{"Ciuco-Admin", "L-Moderator"}|x|Delete a Role of a User in a Level.|
|GET|levels/{id}/[tags, votes]|Authenticated|Ciuco-Admin|Retrieve {tags || votes} of a Level.|
|GET|levels/[tags, votes]|Authenticated|Ciuco-Admin|Retrieve {tags || votes} of Levels.|
|---|---|---|---|---|
|GET|organizations|Ciuco-Admin|---|Retrieve all Organizations.|
|GET|organizations/{id}|Ciuco-Admin|---|Retrieve an specific Organization by its ID.|
|GET|organizations/users/{userId}|Ciuco-Admin|---|Retrieve all Organization in which a User participate.|
|POST|organizations|Ciuco-Admin|---|Create a new Organization.|
|PATCH|organizations/{id}|Ciuco-Admin|---|Update Organization.|
|DELETE|organizations/{id}|Ciuco-Admin|---|Delete Organization.|
|GET|organizations/{id}/users/roles|Ciuco-Admin|---|Retrieve Users with Role in Organization.|
|POST|organizations/{id}/users/roles|{"Ciuco-Admin", "O-Moderator"}|---|Assign Role to User in Organization.|
|DELETE|organizations/{id}/users/{user}/roles/{role}|{"Ciuco-Admin", "O-Moderator"}|---|Delete a Role of a User in a Organization.|
|GET|organizations/{id}/[tags, votes]|Authenticated|Ciuco-Admin|Retrieve {tags || votes} of a Organization.|
|GET|organizations/[tags, votes]|Authenticated|Ciuco-Admin|Retrieve {tags || votes} of Organizations.|
|---|---|---|---|---|
|GET|streak/random|Authenticated|---|Retrieve all Random Streaks.|
|GET|streak/random/{id}|Authenticated|---|Retrieve a Random Streaks.|
|GET|streak/random/users/{userId}|Authenticated|---|Retrieve Random Streak of User.|
|POST|streak/random/users/{userId}|Authenticated|---|Create a Random Streak for a User.|
|PATCH|streak/random/users/{userId}|Authenticated|---|Update a Random Streak of User.|
|---|---|---|---|---|
|GET|references|Authenticated|---|Retrieve all References.|
|GET|references/{id}|Authenticated|---|Retrieve a specific Reference by its ID.|
|POST|references|Authenticated|---|Create a new Reference.|
|PATCH|references/{id}|Authenticated|---|Update a Reference.|
|DELETE|references/{id}|Authenticated|---|Delete a Reference.|
|GET|references/{id}/[tags, votes]|Authenticated|x|Retrieve {tags || votes} of a References.|
|GET|references/[tags, votes]|Authenticated|x|Retrieve {tags || votes} of Reference.|
|---|---|---|---|---|
|GET|roles|{"Ciuco-Admin", "O-Divulgator" }|x|Retrieve all Roles.|
|GET|roles/{id}|{"Ciuco-Admin", "O-Divulgator" }|x|Retrieve a specific Role by its ID.|
|POST|roles|Ciuco-Admin|x|Create a new Role.|
|PATCH|roles/{id}|Ciuco-Admin|x|Update a Role.|
|DELETE|roles/{id}|Ciuco-Admin|x|Delete a Role.|
|---|---|---|---|---|
|GET|tags|Authenticated|x|Retrieve all Tags.|
|GET|tags/{id}|Authenticated|x|Retrieve a specific Tag by its ID.|
|POST|tags|Authenticated|x|Create a new Tag.|
|PATCH|tags/{id}|Authenticated|x|Update a Tag.|
|DELETE|tags/{id}|Authenticated|x|Delete a Tag.|
|---|---|---|---|---|
|GET|tagged|Authenticated|x|Retrieve all Taggged.|
|GET|tagged/{id}|Authenticated|x|Retrieve a Tagged.|
|POST|tagged/{tagId}/{entityTypeId}/{entityId}|Authenticated|x|Tag an Entity.|
|DELETE|tagged/{id}|Authenticated|x|Delete a  Tagged by its ID.|
|---|---|---|---|---|
|GET|users|Ciuco-Admin|x|Retrieve all Users.|
|GET|users/{id}|Ciuco-Admin|x|Retrieve a specific User by its ID.|
|GET|users/username/{username}|Ciuco-Admin|x|Retrieve a specific User by its username.|
|GET|users/email/{email}|Ciuco-Admin|x|Retrieve a specific User by its email address.|
|POST|users|Authenticated|x|Create a new User.|
|PATCH|users/{id}|Ciuco-Admin|x|Update a User.|
|DELETE|users/{id}|Authenticated|x|Delete a User.|
|---|---|---|---|---|
|GET|votes|Ciuco-Admin|x|Retrieve all Votes.|
|GET|votes/{id}|Ciuco-Admin|x|Retrieve a  Vote by its ID.|
|POST|votes/{entityTypeId}/{entityId}|Authenticated|x|Vote an Entity.|
|PATCH|votes/{id}/status|Authenticated|x|Update Status of Vote.|
|GET|votes/{user}|Authenticated|x|Retrieve votes of a User.|

