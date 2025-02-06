package ciudadano.consciente.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(schema = "app", name = "notification_templates")
public class NotificationTemplate {

    @Min(1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_templates_id")
    @Id
    private Integer notificationTemplateId;

    @NotNull(message = "Title cannot be null.")
    @Size(min = 1, max = 50, message = "Title must be between 1 and 100 characters.")
    private String title;

    @NotNull(message = "Template cannot be null.")
    @Column(columnDefinition = "TEXT")
    private String template;

    public NotificationTemplate() {
    }

    public NotificationTemplate(@NotNull(message = "Title cannot be null.") @Size(min = 1, max = 50) String title, @NotNull(message = "Title cannot be null.") @Size(min = 1) String template) {

        this.title = title;
        this.template = template;

    }

    public @Min(1) Integer getNotificationTemplateId() {
        return notificationTemplateId;
    }

    public void setNotificationTemplateId(@Min(1) Integer notificationTemplateId) {
        this.notificationTemplateId = notificationTemplateId;
    }

    public @NotNull(message = "Title cannot be null.") @Size(min = 1, max = 50, message = "Title must be between 1 and 100 characters.") String getTitle() {
        return title;
    }

    public void setTitle(@NotNull(message = "Title cannot be null.") @Size(min = 1, max = 50, message = "Title must be between 1 and 100 characters.") String title) {
        this.title = title;
    }

    public @NotNull(message = "Template cannot be null.") String getTemplate() {
        return template;
    }

    public void setTemplate(@NotNull(message = "Template cannot be null.") String template) {
        this.template = template;
    }
}
