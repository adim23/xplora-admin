package gr.adr.xplora.admin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Tag.
 */
@Entity
@Table(name = "tag")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tag")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "language",
            "createdBy",
            "destination",
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
    private Set<Content> names = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tags")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "tourSteps", "images", "contents", "createdBy", "tags", "categories", "destination" },
        allowSetters = true
    )
    private Set<Place> places = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tags")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<Tour> tours = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tags")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "images", "contents", "createdBy", "tags", "categories", "tours" }, allowSetters = true)
    private Set<TourExtra> tourExtras = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tags")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "menus", "contents", "createdBy", "tags" }, allowSetters = true)
    private Set<WebPage> webPages = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Tag id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Tag code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<Content> getNames() {
        return this.names;
    }

    public void setNames(Set<Content> contents) {
        if (this.names != null) {
            this.names.forEach(i -> i.setTag(null));
        }
        if (contents != null) {
            contents.forEach(i -> i.setTag(this));
        }
        this.names = contents;
    }

    public Tag names(Set<Content> contents) {
        this.setNames(contents);
        return this;
    }

    public Tag addNames(Content content) {
        this.names.add(content);
        content.setTag(this);
        return this;
    }

    public Tag removeNames(Content content) {
        this.names.remove(content);
        content.setTag(null);
        return this;
    }

    public Set<Place> getPlaces() {
        return this.places;
    }

    public void setPlaces(Set<Place> places) {
        if (this.places != null) {
            this.places.forEach(i -> i.removeTags(this));
        }
        if (places != null) {
            places.forEach(i -> i.addTags(this));
        }
        this.places = places;
    }

    public Tag places(Set<Place> places) {
        this.setPlaces(places);
        return this;
    }

    public Tag addPlace(Place place) {
        this.places.add(place);
        place.getTags().add(this);
        return this;
    }

    public Tag removePlace(Place place) {
        this.places.remove(place);
        place.getTags().remove(this);
        return this;
    }

    public Set<Tour> getTours() {
        return this.tours;
    }

    public void setTours(Set<Tour> tours) {
        if (this.tours != null) {
            this.tours.forEach(i -> i.removeTags(this));
        }
        if (tours != null) {
            tours.forEach(i -> i.addTags(this));
        }
        this.tours = tours;
    }

    public Tag tours(Set<Tour> tours) {
        this.setTours(tours);
        return this;
    }

    public Tag addTour(Tour tour) {
        this.tours.add(tour);
        tour.getTags().add(this);
        return this;
    }

    public Tag removeTour(Tour tour) {
        this.tours.remove(tour);
        tour.getTags().remove(this);
        return this;
    }

    public Set<TourExtra> getTourExtras() {
        return this.tourExtras;
    }

    public void setTourExtras(Set<TourExtra> tourExtras) {
        if (this.tourExtras != null) {
            this.tourExtras.forEach(i -> i.removeTags(this));
        }
        if (tourExtras != null) {
            tourExtras.forEach(i -> i.addTags(this));
        }
        this.tourExtras = tourExtras;
    }

    public Tag tourExtras(Set<TourExtra> tourExtras) {
        this.setTourExtras(tourExtras);
        return this;
    }

    public Tag addTourExtra(TourExtra tourExtra) {
        this.tourExtras.add(tourExtra);
        tourExtra.getTags().add(this);
        return this;
    }

    public Tag removeTourExtra(TourExtra tourExtra) {
        this.tourExtras.remove(tourExtra);
        tourExtra.getTags().remove(this);
        return this;
    }

    public Set<WebPage> getWebPages() {
        return this.webPages;
    }

    public void setWebPages(Set<WebPage> webPages) {
        if (this.webPages != null) {
            this.webPages.forEach(i -> i.removeTags(this));
        }
        if (webPages != null) {
            webPages.forEach(i -> i.addTags(this));
        }
        this.webPages = webPages;
    }

    public Tag webPages(Set<WebPage> webPages) {
        this.setWebPages(webPages);
        return this;
    }

    public Tag addWebPage(WebPage webPage) {
        this.webPages.add(webPage);
        webPage.getTags().add(this);
        return this;
    }

    public Tag removeWebPage(WebPage webPage) {
        this.webPages.remove(webPage);
        webPage.getTags().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tag)) {
            return false;
        }
        return getId() != null && getId().equals(((Tag) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tag{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            "}";
    }
}
