package ciudadano.consciente.utility;

import ciudadano.consciente.exception.AuthDenialSecurityException;
import io.quarkus.oidc.UserInfo;
import io.quarkus.security.identity.SecurityIdentity;
import io.vertx.ext.web.RoutingContext;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.jboss.logging.Logger;

import java.util.Arrays;
import java.util.Set;

@RequestScoped
public class UtilityAuthVerifier {

    //public static UtilityAuthVerifier.UserAuthData UserAuthData;

    @Inject
    Logger audit;

    public UserAuthData getPermissions(SecurityIdentity securityIdentity, Object object) {

        UtilityAuthVerifier.UserAuthData user = new UserAuthData(securityIdentity.getAttribute("userinfo"), securityIdentity.getRoles());

        //[USER_AUTH_ID][OPERATION.summary][CALLING_METHOD]
        audit.infov("[{0}][{1}][{2}]", user.getUserInfo().getSubject(),
                object.getClass().getEnclosingMethod().getAnnotation(Operation.class).summary(),
                object.getClass().getEnclosingMethod().getName());

        return user;

    }

    public class UserAuthData {

        private UserInfo userInfo;
        private Set<String> roles;

        private JsonArray mao;
        private JsonArray dao;
        private JsonArray mal;
        private JsonArray dal;

        private UserAuthData (UserInfo userInfo, Set<String> roles) {
            this.userInfo = userInfo;
            this.roles = roles;
            this.mao = (JsonArray) userInfo.get("mao");
            this.dao= (JsonArray) userInfo.get("dao");
            this.mal= (JsonArray) userInfo.get("mal");
            this.dal= (JsonArray) userInfo.get("dal");
        }

        public UserInfo getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfo userInfo) {
            this.userInfo = userInfo;
        }

        public Set<String> getRoles() {
            return roles;
        }

        public void setRoles(Set<String> roles) {
            this.roles = roles;
        }

        public JsonArray getMao() {
            return mao;
        }

        public void setMao(JsonArray mao) {
            this.mao = mao;
        }

        public JsonArray getDao() {
            return dao;
        }

        public void setDao(JsonArray dao) {
            this.dao = dao;
        }

        public JsonArray getMal() {
            return mal;
        }

        public void setMal(JsonArray mal) {
            this.mal = mal;
        }

        public JsonArray getDal() {
            return dal;
        }

        public void setDal(JsonArray dal) {
            this.dal = dal;
        }

        public boolean isCiucoAdmin() {
            return roles.contains("Ciuco-Admin");
        }

        public boolean hasOrgRoles(Integer organizationId) {

            boolean isAuthorized;
            boolean authorizedAsModerator = false, authorizedAsDivulgator = false;

            try {
                authorizedAsModerator =
                        this.getMao().contains(Json.createValue(organizationId));
            } catch (NullPointerException ignored) {}

            try {
                authorizedAsDivulgator = this.getDao().contains(Json.createValue(organizationId));
            } catch (NullPointerException ignored) {}

            isAuthorized = authorizedAsModerator || authorizedAsDivulgator;
            if (!isAuthorized) audit.warnv("[USER AUTH MISMATCH: User {0} is not allowed to " +
                            "retrieve data of Organization {1} ('mao': {2} and 'dao':{3}).]",
                    userInfo.getEmail(), organizationId, authorizedAsModerator, authorizedAsDivulgator);
            return isAuthorized;
        }

    }

}
