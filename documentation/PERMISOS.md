ACLARACIÓN: por ahora, los roles no son compuestos, para ahorrarnos la complejidad inicial. Es necesario, por lo tanto que explicitemos con @RolesAllowed("rolName", "anotherRolName") si más de un rol tiene acceso al recurso. Si queremos un rol específico, podemos usar el método de SecurityIdentity hasRole("nombreRol").

ROLES:
- Ciuco-Admin (puede acceder a todo)
- O-Divulgator
- O-Moderator
- L-Divulgator
- L-Moderator
- Button
- Tester 

- ORGANIZATIONS: UserRoleOrganization
	- O-Moderator: puede asignar, editar y quitar roles en la Organización y sus niveles.
	- O-Divulgator: Puede agregar Levels a la Organización.

- LEVELS: UserRoleLevel
	- L-Moderator: puede asignar, editar y quitar roles en el nivel (que heredan los hijos del level?).
	- L-Divulgator: ¿puede agregar Activities a los Levels y Content a los Activities?
	
* POOL: 
	* Button: puede eliminar Concerns del Pool.


***ENDPOINTS DONE***

/users:
* POST: ==Exige petición Rest a la API del IdentityProvider== previa, esto nos permite obtener el usuario de KC (revisar que se hace antes o después). Luego de registrarse en KC, cualquier usuario logueado. @Authenticated. Se loguea a sí mismo con su AccessToken.
* PATCH: Ciuco-Admin. ==Exige petición Rest a la API del IdentityProvider==--> Sólo se podría editar el atributo "authServerId" si eventualmente se migra desde Keycloak a otro identityProvider.
* DELETE: Ciuco-Admin y cualquier usuario logueado como si mismo. @Authenticated. Exige verificación lógica. ==Exige petición Rest a la API del IdentityProvider== si lo manejamos nosotros.
* GET-ALL: Ciuco-Admin.
* GET \[id, username, email ] : Ciuco-Admin. (Quizás esto haya que modificarlo cuando se desee asignar roles a usuarios y también deba poder usar este recurso el otro Role que puede asignar roles: O-Divulgator. (Se puede verificar el path desde donde viene en el MainFilter). 

/roles:
* POST: Ciuco-Admin. ==Exige petición Rest a la API del IdentityProvider== si lo manejamos nosotros. En este caso usamos Kleycloak para mapear los roles (podríamos agregar a la DB de los roles el id del rol de keycloak).
* PATCH: Ciuco-Admin. ==Exige petición Rest a la API del IdentityProvider==. Solo se puede actualizar la descripción.
* DELETE: Ciuco-Admin. ==Exige petición Rest a la API del IdentityProvider== si lo manejamos nosotros. En este caso usamos Kleycloak para mapear los roles (podríamos agregar a la DB de los roles el id del rol de keycloak).
* GET-ALL: Ciuco-Admin y O-Divulgator.  
* GET: Ciuco-Admin y O-Divulgator.  

/votes
* GET-votesByUser: Ciuco-Admin y cualquier usuario logueado como si mismo. @Authenticated. Exige verificación lógica.
* GET-ALL: Ciuco-Admin
* GET: Ciuco-Admin
* POST: @Authenticated. Vota con su AccessToken.
* PATCH: @Authenticated. Exige verificación lógica con su AccessToken.

***NOT DONE***

/organizations:
* POST:
* GET-ALL: Ciuco-Admin
* GET: Ciuco-Admin
/org/roles/asign ==Exige petición Rest a la API del IdentityProvider== ONLY Ciuco-Admin could assign Role Ciuco-Admin, Tester and Button

/levels:
* POST: 
/level/roles/asign ==Exige petición Rest a la API del IdentityProvider== ONLY Ciuco-Admin could assign Role Ciuco-Admin, Tester and Button