package ciudadano.consciente.model;

import ciudadano.consciente.utility.UtilityFileSystem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;

@Entity
@Table(schema = "app", name = "activity_type_version",
        uniqueConstraints = {
                @UniqueConstraint( // We cant have a version with the same identifiers of GithubMetadata
                        name = "activity_type_version_model_template_readme_activ_key",
                        columnNames = {"model", "template", "readme", "activity_type_id"}
                )
        })
public class ActivityTypeVersion {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "activity_type_version_id")
    private Integer activityTypeVersionId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "activity_type_id", referencedColumnName = "activity_type_id")
    private ActivityType activityTypeId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "activity_type_version_status_id", referencedColumnName = "activity_type_version_status_id")
    private ActivityTypeVersionStatus activityTypeVersionStatusId;

    @Column(name = "version_number")
    private Integer versionNumber;

    @Column(name = "staged_date")
    private LocalDate stagedDate;

    @Column(name = "last_modified_status_date")
    private LocalDate lastModifiedStatusDate;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JdbcTypeCode(SqlTypes.JSON) // To automatically use the table as jsonb
    @Column(name = "model", columnDefinition = "jsonb")
    private String model;

    @Column(name = "template")
    private String template;

    @Column(name = "readme")
    private String readme;

    @Transient // not persisted in the db
    private byte[] thumbnail;

    public ActivityTypeVersion() {
        this.stagedDate = LocalDate.now();
        this.lastModifiedStatusDate = LocalDate.now();
    }

    public ActivityTypeVersion(String model, String template, String readme) {

        this.model = model;
        this.template = template;
        this.readme = readme;
        this.stagedDate = LocalDate.now();
        this.lastModifiedStatusDate = LocalDate.now();


    }

    public Integer getActivityTypeVersionId() {
        return activityTypeVersionId;
    }

    public void setActivityTypeVersionId(Integer activityTypeVersionId) {
        this.activityTypeVersionId = activityTypeVersionId;
    }

    public ActivityType getActivityTypeId() {
        return activityTypeId;
    }

    public void setActivityTypeId(ActivityType activityTypeId) {
        this.activityTypeId = activityTypeId;
    }

    public ActivityTypeVersionStatus getActivityTypeVersionStatusId() {
        return activityTypeVersionStatusId;
    }

    public void setActivityTypeVersionStatusId(ActivityTypeVersionStatus activityTypeVersionStatusId) {
        this.activityTypeVersionStatusId = activityTypeVersionStatusId;
    }

    public Integer getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(Integer versionNumber) {
        this.versionNumber = versionNumber;
    }

    public LocalDate getStagedDate() {
        return stagedDate;
    }

    public void setStagedDate(LocalDate stagedDate) {
        this.stagedDate = stagedDate;
    }

    public LocalDate getLastModifiedStatusDate() {
        return lastModifiedStatusDate;
    }

    public void setLastModifiedStatusDate(LocalDate lastModifiedStatusDate) {
        this.lastModifiedStatusDate = lastModifiedStatusDate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getReadme() {
        return readme;
    }

    public void setReadme(String readme) {
        this.readme = readme;
    }

    public byte[] getThumbnail() {
        return new UtilityFileSystem().getByteArrayFromFileSystem(this.getActivityTypeVersionId().toString());
    }

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }
}
