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
 * A Promotion.
 */
@Entity
@Table(name = "promotion")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Promotion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;

    @Column(name = "enabled")
    private Boolean enabled;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "promotion")
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
    private Set<Content> titles = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "promotions")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<Tour> tours = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Promotion id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Promotion code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getDiscount() {
        return this.discount;
    }

    public Promotion discount(Double discount) {
        this.setDiscount(discount);
        return this;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public LocalDate getFromDate() {
        return this.fromDate;
    }

    public Promotion fromDate(LocalDate fromDate) {
        this.setFromDate(fromDate);
        return this;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return this.toDate;
    }

    public Promotion toDate(LocalDate toDate) {
        this.setToDate(toDate);
        return this;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public Promotion enabled(Boolean enabled) {
        this.setEnabled(enabled);
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Content> getTitles() {
        return this.titles;
    }

    public void setTitles(Set<Content> contents) {
        if (this.titles != null) {
            this.titles.forEach(i -> i.setPromotion(null));
        }
        if (contents != null) {
            contents.forEach(i -> i.setPromotion(this));
        }
        this.titles = contents;
    }

    public Promotion titles(Set<Content> contents) {
        this.setTitles(contents);
        return this;
    }

    public Promotion addTitle(Content content) {
        this.titles.add(content);
        content.setPromotion(this);
        return this;
    }

    public Promotion removeTitle(Content content) {
        this.titles.remove(content);
        content.setPromotion(null);
        return this;
    }

    public Set<Tour> getTours() {
        return this.tours;
    }

    public void setTours(Set<Tour> tours) {
        if (this.tours != null) {
            this.tours.forEach(i -> i.removePromotions(this));
        }
        if (tours != null) {
            tours.forEach(i -> i.addPromotions(this));
        }
        this.tours = tours;
    }

    public Promotion tours(Set<Tour> tours) {
        this.setTours(tours);
        return this;
    }

    public Promotion addTour(Tour tour) {
        this.tours.add(tour);
        tour.getPromotions().add(this);
        return this;
    }

    public Promotion removeTour(Tour tour) {
        this.tours.remove(tour);
        tour.getPromotions().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Promotion)) {
            return false;
        }
        return getId() != null && getId().equals(((Promotion) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Promotion{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", discount=" + getDiscount() +
            ", fromDate='" + getFromDate() + "'" +
            ", toDate='" + getToDate() + "'" +
            ", enabled='" + getEnabled() + "'" +
            "}";
    }
}
