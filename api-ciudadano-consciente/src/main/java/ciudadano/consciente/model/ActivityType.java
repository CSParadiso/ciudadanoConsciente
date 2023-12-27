package ciudadano.consciente.model;

import jakarta.persistence.*;

@Entity
@Table(schema = "app", name = "activity_types")
public class ActivityType {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_type_id")
    @Id
    private Integer activityTypeId;

    private String name;

    private String description;

    @Column(name = "functional_template_url")
    private String functionalTemplateUrl;

    public Integer getActivityTypeId() {
        return activityTypeId;
    }

    public void setActivityTypeId(Integer activityTypeId) {
        this.activityTypeId = activityTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFunctionalTemplateUrl() {
        return functionalTemplateUrl;
    }

    public void setFunctionalTemplateUrl(String functionalTemplateUrl) {
        this.functionalTemplateUrl = functionalTemplateUrl;
    }

}
