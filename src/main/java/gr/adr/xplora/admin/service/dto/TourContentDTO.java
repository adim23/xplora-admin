package gr.adr.xplora.admin.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link gr.adr.xplora.admin.domain.TourContent} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TourContentDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;

    private String title;

    @Lob
    private String shortDescription;

    @Lob
    private String data;

    @Lob
    private String meta;

    private String cancellation;

    @Lob
    private String activityPath;

    @Lob
    private String atAGlance;

    @Lob
    private String inDetail;

    @Lob
    private String whatIsIncluded;

    @Lob
    private String youCanAdd;

    @Lob
    private String importantInformation;

    @Lob
    private String extraInfo;

    private LocalDate createdDate;

    @NotNull
    private LanguageDTO language;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getCancellation() {
        return cancellation;
    }

    public void setCancellation(String cancellation) {
        this.cancellation = cancellation;
    }

    public String getActivityPath() {
        return activityPath;
    }

    public void setActivityPath(String activityPath) {
        this.activityPath = activityPath;
    }

    public String getAtAGlance() {
        return atAGlance;
    }

    public void setAtAGlance(String atAGlance) {
        this.atAGlance = atAGlance;
    }

    public String getInDetail() {
        return inDetail;
    }

    public void setInDetail(String inDetail) {
        this.inDetail = inDetail;
    }

    public String getWhatIsIncluded() {
        return whatIsIncluded;
    }

    public void setWhatIsIncluded(String whatIsIncluded) {
        this.whatIsIncluded = whatIsIncluded;
    }

    public String getYouCanAdd() {
        return youCanAdd;
    }

    public void setYouCanAdd(String youCanAdd) {
        this.youCanAdd = youCanAdd;
    }

    public String getImportantInformation() {
        return importantInformation;
    }

    public void setImportantInformation(String importantInformation) {
        this.importantInformation = importantInformation;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LanguageDTO getLanguage() {
        return language;
    }

    public void setLanguage(LanguageDTO language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TourContentDTO)) {
            return false;
        }

        TourContentDTO tourContentDTO = (TourContentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tourContentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TourContentDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", title='" + getTitle() + "'" +
            ", shortDescription='" + getShortDescription() + "'" +
            ", data='" + getData() + "'" +
            ", meta='" + getMeta() + "'" +
            ", cancellation='" + getCancellation() + "'" +
            ", activityPath='" + getActivityPath() + "'" +
            ", atAGlance='" + getAtAGlance() + "'" +
            ", inDetail='" + getInDetail() + "'" +
            ", whatIsIncluded='" + getWhatIsIncluded() + "'" +
            ", youCanAdd='" + getYouCanAdd() + "'" +
            ", importantInformation='" + getImportantInformation() + "'" +
            ", extraInfo='" + getExtraInfo() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", language=" + getLanguage() +
            "}";
    }
}
