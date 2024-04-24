package gr.adr.xplora.admin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TourContent.
 */
@Entity
@Table(name = "tour_content")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TourContent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "short_description")
    private String shortDescription;

    @Lob
    @Column(name = "data")
    private String data;

    @Lob
    @Column(name = "meta")
    private String meta;

    @Column(name = "cancellation")
    private String cancellation;

    @Lob
    @Column(name = "activity_path")
    private String activityPath;

    @Lob
    @Column(name = "at_a_glance")
    private String atAGlance;

    @Lob
    @Column(name = "in_detail")
    private String inDetail;

    @Lob
    @Column(name = "what_is_included")
    private String whatIsIncluded;

    @Lob
    @Column(name = "you_can_add")
    private String youCanAdd;

    @Lob
    @Column(name = "important_information")
    private String importantInformation;

    @Lob
    @Column(name = "extra_info")
    private String extraInfo;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @JsonIgnoreProperties(value = { "createdBy", "content", "tourContent", "prompt" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Language language;

    @JsonIgnoreProperties(
        value = {
            "content",
            "steps",
            "images",
            "createdBy",
            "meetingPoint",
            "finishPoint",
            "tourExtras",
            "tags",
            "promotions",
            "categories",
            "destination",
            "defaultCategory",
        },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "content")
    private Tour tour;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TourContent id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public TourContent code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public TourContent title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return this.shortDescription;
    }

    public TourContent shortDescription(String shortDescription) {
        this.setShortDescription(shortDescription);
        return this;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getData() {
        return this.data;
    }

    public TourContent data(String data) {
        this.setData(data);
        return this;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMeta() {
        return this.meta;
    }

    public TourContent meta(String meta) {
        this.setMeta(meta);
        return this;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getCancellation() {
        return this.cancellation;
    }

    public TourContent cancellation(String cancellation) {
        this.setCancellation(cancellation);
        return this;
    }

    public void setCancellation(String cancellation) {
        this.cancellation = cancellation;
    }

    public String getActivityPath() {
        return this.activityPath;
    }

    public TourContent activityPath(String activityPath) {
        this.setActivityPath(activityPath);
        return this;
    }

    public void setActivityPath(String activityPath) {
        this.activityPath = activityPath;
    }

    public String getAtAGlance() {
        return this.atAGlance;
    }

    public TourContent atAGlance(String atAGlance) {
        this.setAtAGlance(atAGlance);
        return this;
    }

    public void setAtAGlance(String atAGlance) {
        this.atAGlance = atAGlance;
    }

    public String getInDetail() {
        return this.inDetail;
    }

    public TourContent inDetail(String inDetail) {
        this.setInDetail(inDetail);
        return this;
    }

    public void setInDetail(String inDetail) {
        this.inDetail = inDetail;
    }

    public String getWhatIsIncluded() {
        return this.whatIsIncluded;
    }

    public TourContent whatIsIncluded(String whatIsIncluded) {
        this.setWhatIsIncluded(whatIsIncluded);
        return this;
    }

    public void setWhatIsIncluded(String whatIsIncluded) {
        this.whatIsIncluded = whatIsIncluded;
    }

    public String getYouCanAdd() {
        return this.youCanAdd;
    }

    public TourContent youCanAdd(String youCanAdd) {
        this.setYouCanAdd(youCanAdd);
        return this;
    }

    public void setYouCanAdd(String youCanAdd) {
        this.youCanAdd = youCanAdd;
    }

    public String getImportantInformation() {
        return this.importantInformation;
    }

    public TourContent importantInformation(String importantInformation) {
        this.setImportantInformation(importantInformation);
        return this;
    }

    public void setImportantInformation(String importantInformation) {
        this.importantInformation = importantInformation;
    }

    public String getExtraInfo() {
        return this.extraInfo;
    }

    public TourContent extraInfo(String extraInfo) {
        this.setExtraInfo(extraInfo);
        return this;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public TourContent createdDate(LocalDate createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Language getLanguage() {
        return this.language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public TourContent language(Language language) {
        this.setLanguage(language);
        return this;
    }

    public Tour getTour() {
        return this.tour;
    }

    public void setTour(Tour tour) {
        if (this.tour != null) {
            this.tour.setContent(null);
        }
        if (tour != null) {
            tour.setContent(this);
        }
        this.tour = tour;
    }

    public TourContent tour(Tour tour) {
        this.setTour(tour);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TourContent)) {
            return false;
        }
        return getId() != null && getId().equals(((TourContent) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TourContent{" +
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
            "}";
    }
}
