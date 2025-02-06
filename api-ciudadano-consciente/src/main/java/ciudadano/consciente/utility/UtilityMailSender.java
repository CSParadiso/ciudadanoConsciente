package ciudadano.consciente.utility;


import ciudadano.consciente.access.AccessNotificationTemplate;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.model.NotificationTemplate;
import ciudadano.consciente.model.Organization;
import ciudadano.consciente.model.User;
import ciudadano.consciente.model.VerifyToken;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import io.quarkus.qute.Qute;
import io.quarkus.qute.Template;
import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import java.util.Map;

@Blocking
@RequestScoped
public class UtilityMailSender {

    @Inject
    Mailer mailer;

    @ConfigProperty(name = "quarkus.mailer.from")
    String emailSender;

    @Inject
    Template userSignUp;

    @Inject
    Template userGoodbye;

    @Inject
    Template organizationGoodbye;

    @Inject
    Template verifyOrganizationEmail;

    @Inject
    Template confirmOrganizationVerification;

    @Inject
    Template deleteOrganizationToken;

    @Inject
    AccessNotificationTemplate accessNotificationTemplate;

    @Inject
    Logger audit;

    public void sendVerifyTokenToOrganization(VerifyToken verifyToken, Organization organization) {

        // Set default from build time
        String defaultTemplate = verifyOrganizationEmail.data("organizationName", organization.getName())
                .data("token", verifyToken.getToken()).render();
        NotificationTemplate verifyOrganizationEmailNotificationTemplate = null;
        // Verify if DB has some template for it
        try {
            verifyOrganizationEmailNotificationTemplate = accessNotificationTemplate.getByName("verifyOrganizationEmail")
                    .orElseThrow(() -> new HttpNoContentException("NotificationTemplate not found."));
            defaultTemplate = Qute.fmt(verifyOrganizationEmailNotificationTemplate.getTemplate(), Map.of(
                    "organizationName", organization.getName(), "token", verifyToken.getToken()));
        } catch (Exception e) {
            audit.debugv("NotificationTemplate not found in DB.");
        }

        try {
            mailer.send(
                    Mail.withHtml(organization.getEmail(),
                    //Mail.withHtml("cayetanosimonparadiso@protonmail.com",
                                    "Verificar Organización " + organization.getName() + " en Ciudadano Consciente",
                                    defaultTemplate)
                            .setFrom("Ciudadano Consciente" + "<" + emailSender + ">"));
        } catch (Exception e) {
            throw new HttpInternalServerException("Failed to send token to organization email: " + e.getMessage());
        }

    }

    public void sendConfirmationToNewOrganization(Organization organization, User user) {

        // Set default from build time
        String defaultTemplate = confirmOrganizationVerification.data("organizationName", organization.getName())
                .data("oModeratorEmail", user.getEmail()).render();
        NotificationTemplate confirmOrganizationVerificationNotificationTemplate = null;
        // Verify if DB has some template for it
        try {
            confirmOrganizationVerificationNotificationTemplate = accessNotificationTemplate.getByName(
                    "confirmOrganizationVerification")
                    .orElseThrow(() -> new HttpNoContentException("NotificationTemplate not found."));
            defaultTemplate = Qute.fmt(confirmOrganizationVerificationNotificationTemplate.getTemplate(), Map.of(
                    "organizationName", organization.getName(),"oModeratorEmail", user.getEmail()));
        } catch (Exception e) {
            audit.debugv("NotificationTemplate not found in DB.");
        }

        try {
            mailer.send(
                    Mail.withHtml(organization.getEmail(),
                    //        Mail.withHtml("cayetanosimonparadiso@protonmail.com",
                                    "Organización verificada en Ciudadano Consciente",
                                    defaultTemplate)
                            .setFrom("Ciudadano Consciente" + "<" + emailSender + ">"));
        } catch (Exception e) {
            throw new HttpInternalServerException("Failed to send confirmation to organization email: " + e.getMessage());
        }

    }

    public void sendDeleteTokenToOrganization(VerifyToken verifyToken, Organization organization) {

        // Set default from build time
        String defaultTemplate = deleteOrganizationToken.data("organizationName", organization.getName())
                .data("token", verifyToken.getToken()).render();
        NotificationTemplate deleteOrganizationTokenNotificationTemplate = null;
        // Verify if DB has some template for it
        try {
            deleteOrganizationTokenNotificationTemplate = accessNotificationTemplate.getByName(
                            "deleteOrganizationToken")
                    .orElseThrow(() -> new HttpNoContentException("NotificationTemplate not found."));
            defaultTemplate = Qute.fmt(deleteOrganizationTokenNotificationTemplate.getTemplate(), Map.of(
                    "organizationName", organization.getName()), "token", verifyToken.getToken());
        } catch (Exception e) {
            audit.debugv("NotificationTemplate not found in DB.");
        }

        try {
            mailer.send(
                    Mail.withHtml(organization.getEmail(),
                    //Mail.withHtml("cayetanosimonparadiso@protonmail.com",
                                    "Eliminar Organización " + organization.getName() + " en Ciudadano Consciente",
                                    defaultTemplate)
                            .setFrom("Ciudadano Consciente" + "<" + emailSender + ">"));
        } catch (Exception e) {
            throw new HttpInternalServerException("Failed to send delete token to organization email: " + e.getMessage());
        }

    }

    public void sendConfirmationToDeletedOrganization(Organization organization, User user) {

        // Set default from build time
        String defaultTemplate = organizationGoodbye.data("organizationName", organization.getName())
                .data("email", organization.getEmail())
                .data("oModeratorEmail", user.getEmail()).render();
        NotificationTemplate organizationGoodbyeNotificationTemplate = null;
        // Verify if DB has some template for it
        try {
            organizationGoodbyeNotificationTemplate = accessNotificationTemplate.getByName(
                            "organizationGoodbye")
                    .orElseThrow(() -> new HttpNoContentException("NotificationTemplate not found."));
            defaultTemplate = Qute.fmt(organizationGoodbyeNotificationTemplate.getTemplate(), Map.of(
                    "organizationName", organization.getName(), "email", organization.getEmail(), "oModeratorEmail", user.getEmail()));
        } catch (Exception e) {
            audit.debugv("NotificationTemplate not found in DB.");
        }

        try {
            mailer.send(
                    Mail.withHtml(organization.getEmail(),
                    //Mail.withHtml("cayetanosimonparadiso@protonmail.com",
                                    "Organización " + organization.getName() + " eliminada en Ciudadano Consciente",
                                    defaultTemplate)
                            .setFrom("Ciudadano Consciente" + "<" + emailSender + ">"));
        } catch (Exception e) {
            throw new HttpInternalServerException("Failed to send delete confirmation to organization email: " + e.getMessage());
        }

    }

//    public void sendWelcomeToNewUser(User user) {
//
//        try {
//            mailer.send(
//                    Mail.withText(user.getEmail(),
//                                    "Bienvenido a la cultura de ser un Ciudadano Consciente " + user.getUsername(),
//                                    "Desde Ciudadano Consciente le damos la bienvenida a esta gran experiencia. Por favor, si es votante de Milei, no continúe.")
//                            .setFrom("Ciudadano Consciente" + "<" + emailSender + ">"));
//        } catch (Exception e) {
//            throw new HttpInternalServerException("Failed to send welcome email to user: " + e.getMessage());
//        }
//
//    }

    public void sendWelcomeToNewUser(User user) {

        // Set default from build time
        String defaultTemplate = userSignUp.data("username", user.getUsername())
                .data("email", user.getEmail()).render();
        NotificationTemplate userSignUpNotificationTemplate = null;
        // Verify if DB has some template for it
        try {
            userSignUpNotificationTemplate = accessNotificationTemplate.getByName("userSignUp")
                    .orElseThrow(() -> new HttpNoContentException("NotificationTemplate not found."));
            defaultTemplate = Qute.fmt(userSignUpNotificationTemplate.getTemplate(), Map.of("username",
                    user.getUsername(), "email", user.getEmail()));
        } catch (Exception e) {
            audit.debugv("NotificationTemplate not found in DB.");
        }

        try {
            mailer.send(
                    Mail.withHtml(user.getEmail(),
                    //        Mail.withHtml("cayetanosimonparadiso@protonmail.com",
                                    "Bienvenido a Ciudadano Consciente, " + user.getUsername(),
                            defaultTemplate)
                            .setFrom("Ciudadano Consciente" + "<" + emailSender + ">"));
        } catch (Exception e) {
            throw new HttpInternalServerException("Failed to send welcome email to user: " + e.getMessage());
        }

    }

//    public void sendGoodbyeToUser(User user) {
//
//        try {
//            mailer.send(
//                    Mail.withText(user.getEmail(),
//                                    "Te vamos a extrañar siendo un Ciudadano Consciente",
//                                    "Aunque te vamos a extrañar no vamos a guardar rencores ni tus datos. " +
//                                            "Hasta siempre y aguante Led Zeppelin.")
//                            .setFrom("Ciudadano Consciente" + "<" + emailSender + ">"));
//        } catch (Exception e) {
//            throw new HttpInternalServerException("Failed to send welcome email to user: " + e.getMessage());
//        }
//
//    }

    public void sendGoodbyeToUser(User user) {

        // Set default from build time
        String defaultTemplate = userGoodbye.data("username", user.getUsername())
                .data("email", user.getEmail()).render();
        NotificationTemplate userGoodbyeNotificationTemplate = null;
        // Verify if DB has some template for it
        try {
            userGoodbyeNotificationTemplate = accessNotificationTemplate.getByName("userGoodbye")
                    .orElseThrow(() -> new HttpNoContentException("NotificationTemplate not found."));
            defaultTemplate = Qute.fmt(userGoodbyeNotificationTemplate.getTemplate(), Map.of("username",
                    user.getUsername(), "email", user.getEmail()));
        } catch (Exception e) {
            audit.debugv("NotificationTemplate not found in DB.");
        }

        try {
            mailer.send(
                    Mail.withHtml(user.getEmail(),
                    //        Mail.withHtml("cayetanosimonparadiso@protonmail.com",
                                    "Adiós desde Ciudadano Consciente",
                            defaultTemplate)
                            .setFrom("Ciudadano Consciente" + "<" + emailSender + ">"));
        } catch (Exception e) {
            throw new HttpInternalServerException("Failed to send goodbye email to user: " + e.getMessage());
        }

    }

}
