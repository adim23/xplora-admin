package gr.adr.xplora.admin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TourStep.
 */
@Entity
@Table(name = "tour_step")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TourStep implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "step_order", nullable = false)
    private Integer stepOrder;

    @NotNull
    @Column(name = "wait_time", nullable = false)
    private Integer waitTime;

    @NotNull
    @Column(name = "drive_time", nullable = false)
    private Integer driveTime;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tourStep")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "language",
            "createdBy",
            "destination",
            "tourExtraInfo",
            "tour",
            "tourCategory",
            "place",
            "placeCategory",
            "tourExtraCategory",
            "tourExtra",
            "menu",
            "webPage",
            "tag",
            "tourStep",
            "promotion",
            "imageFile",
        },
        allowSetters = true
    )
    private Set<Content> infos = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "steps",
            "images",
            "extraInfos",
            "contents",
            "createdBy",
            "meetingPoint",
            "finishPoint",
            "tourExtras",
            "tags",
            "promotions",
            "categories",
            "destination",
        },
        allowSetters = true
    )
    private Tour tour;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "tourSteps", "images", "contents", "createdBy", "tags", "categories", "destination" },
        allowSetters = true
    )
    private Place place;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TourStep id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public TourStep code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getStepOrder() {
        return this.stepOrder;
    }

    public TourStep stepOrder(Integer stepOrder) {
        this.setStepOrder(stepOrder);
        return this;
    }

    public void setStepOrder(Integer stepOrder) {
        this.stepOrder = stepOrder;
    }

    public Integer getWaitTime() {
        return this.waitTime;
    }

    public TourStep waitTime(Integer waitTime) {
        this.setWaitTime(waitTime);
        return this;
    }

    public void setWaitTime(Integer waitTime) {
        this.waitTime = waitTime;
    }

    public Integer getDriveTime() {
        return this.driveTime;
    }

    public TourStep driveTime(Integer driveTime) {
        this.setDriveTime(driveTime);
        return this;
    }

    public void setDriveTime(Integer driveTime) {
        this.driveTime = driveTime;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public TourStep createdDate(LocalDate createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Set<Content> getInfos() {
        return this.infos;
    }

    public void setInfos(Set<Content> contents) {
        if (this.infos != null) {
            this.infos.forEach(i -> i.setTourStep(null));
        }
        if (contents != null) {
            contents.forEach(i -> i.setTourStep(this));
        }
        this.infos = contents;
    }

    public TourStep infos(Set<Content> contents) {
        this.setInfos(contents);
        return this;
    }

    public TourStep addInfo(Content content) {
        this.infos.add(content);
        content.setTourStep(this);
        return this;
    }

    public TourStep removeInfo(Content content) {
        this.infos.remove(content);
        content.setTourStep(null);
        return this;
    }

    public User getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(User user) {
        this.createdBy = user;
    }

    public TourStep createdBy(User user) {
        this.setCreatedBy(user);
        return this;
    }

    public Tour getTour() {
        return this.tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public TourStep tour(Tour tour) {
        this.setTour(tour);
        return this;
    }

    public Place getPlace() {
        return this.place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public TourStep place(Place place) {
        this.setPlace(place);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TourStep)) {
            return false;
        }
        return getId() != null && getId().equals(((TourStep) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TourStep{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", stepOrder=" + getStepOrder() +
            ", waitTime=" + getWaitTime() +
            ", driveTime=" + getDriveTime() +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
