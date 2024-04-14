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
 * A Place.
 */
@Entity
@Table(name = "place")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Place implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "destination_sight", nullable = false)
    private Boolean destinationSight;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "default_image")
    private String defaultImage;

    @Lob
    @Column(name = "default_image_data")
    private byte[] defaultImageData;

    @Column(name = "default_image_data_content_type")
    private String defaultImageDataContentType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "place")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "infos", "createdBy", "tour", "place" }, allowSetters = true)
    private Set<TourStep> tourSteps = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "place")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "captions",
            "createdBy",
            "destination",
            "tour",
            "tourCategory",
            "place",
            "placeCategory",
            "vehicle",
            "driver",
            "tourExtra",
            "tourExtraCategory",
        },
        allowSetters = true
    )
    private Set<ImageFile> images = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "place")
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
    private Set<Content> contents = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "rel_place__tags", joinColumns = @JoinColumn(name = "place_id"), inverseJoinColumns = @JoinColumn(name = "tags_id"))
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "names", "places", "tours", "tourExtras", "webPages" }, allowSetters = true)
    private Set<Tag> tags = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_place__category",
        joinColumns = @JoinColumn(name = "place_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "images", "contents", "createdBy", "places" }, allowSetters = true)
    private Set<PlaceCategory> categories = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "tours", "places", "images", "contents", "createdBy" }, allowSetters = true)
    private Destination destination;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Place id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Place code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getDestinationSight() {
        return this.destinationSight;
    }

    public Place destinationSight(Boolean destinationSight) {
        this.setDestinationSight(destinationSight);
        return this;
    }

    public void setDestinationSight(Boolean destinationSight) {
        this.destinationSight = destinationSight;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public Place longitude(String longitude) {
        this.setLongitude(longitude);
        return this;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public Place latitude(String latitude) {
        this.setLatitude(latitude);
        return this;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public Place createdDate(LocalDate createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getDefaultImage() {
        return this.defaultImage;
    }

    public Place defaultImage(String defaultImage) {
        this.setDefaultImage(defaultImage);
        return this;
    }

    public void setDefaultImage(String defaultImage) {
        this.defaultImage = defaultImage;
    }

    public byte[] getDefaultImageData() {
        return this.defaultImageData;
    }

    public Place defaultImageData(byte[] defaultImageData) {
        this.setDefaultImageData(defaultImageData);
        return this;
    }

    public void setDefaultImageData(byte[] defaultImageData) {
        this.defaultImageData = defaultImageData;
    }

    public String getDefaultImageDataContentType() {
        return this.defaultImageDataContentType;
    }

    public Place defaultImageDataContentType(String defaultImageDataContentType) {
        this.defaultImageDataContentType = defaultImageDataContentType;
        return this;
    }

    public void setDefaultImageDataContentType(String defaultImageDataContentType) {
        this.defaultImageDataContentType = defaultImageDataContentType;
    }

    public Set<TourStep> getTourSteps() {
        return this.tourSteps;
    }

    public void setTourSteps(Set<TourStep> tourSteps) {
        if (this.tourSteps != null) {
            this.tourSteps.forEach(i -> i.setPlace(null));
        }
        if (tourSteps != null) {
            tourSteps.forEach(i -> i.setPlace(this));
        }
        this.tourSteps = tourSteps;
    }

    public Place tourSteps(Set<TourStep> tourSteps) {
        this.setTourSteps(tourSteps);
        return this;
    }

    public Place addTourStep(TourStep tourStep) {
        this.tourSteps.add(tourStep);
        tourStep.setPlace(this);
        return this;
    }

    public Place removeTourStep(TourStep tourStep) {
        this.tourSteps.remove(tourStep);
        tourStep.setPlace(null);
        return this;
    }

    public Set<ImageFile> getImages() {
        return this.images;
    }

    public void setImages(Set<ImageFile> imageFiles) {
        if (this.images != null) {
            this.images.forEach(i -> i.setPlace(null));
        }
        if (imageFiles != null) {
            imageFiles.forEach(i -> i.setPlace(this));
        }
        this.images = imageFiles;
    }

    public Place images(Set<ImageFile> imageFiles) {
        this.setImages(imageFiles);
        return this;
    }

    public Place addImages(ImageFile imageFile) {
        this.images.add(imageFile);
        imageFile.setPlace(this);
        return this;
    }

    public Place removeImages(ImageFile imageFile) {
        this.images.remove(imageFile);
        imageFile.setPlace(null);
        return this;
    }

    public Set<Content> getContents() {
        return this.contents;
    }

    public void setContents(Set<Content> contents) {
        if (this.contents != null) {
            this.contents.forEach(i -> i.setPlace(null));
        }
        if (contents != null) {
            contents.forEach(i -> i.setPlace(this));
        }
        this.contents = contents;
    }

    public Place contents(Set<Content> contents) {
        this.setContents(contents);
        return this;
    }

    public Place addContents(Content content) {
        this.contents.add(content);
        content.setPlace(this);
        return this;
    }

    public Place removeContents(Content content) {
        this.contents.remove(content);
        content.setPlace(null);
        return this;
    }

    public User getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(User user) {
        this.createdBy = user;
    }

    public Place createdBy(User user) {
        this.setCreatedBy(user);
        return this;
    }

    public Set<Tag> getTags() {
        return this.tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Place tags(Set<Tag> tags) {
        this.setTags(tags);
        return this;
    }

    public Place addTags(Tag tag) {
        this.tags.add(tag);
        return this;
    }

    public Place removeTags(Tag tag) {
        this.tags.remove(tag);
        return this;
    }

    public Set<PlaceCategory> getCategories() {
        return this.categories;
    }

    public void setCategories(Set<PlaceCategory> placeCategories) {
        this.categories = placeCategories;
    }

    public Place categories(Set<PlaceCategory> placeCategories) {
        this.setCategories(placeCategories);
        return this;
    }

    public Place addCategory(PlaceCategory placeCategory) {
        this.categories.add(placeCategory);
        return this;
    }

    public Place removeCategory(PlaceCategory placeCategory) {
        this.categories.remove(placeCategory);
        return this;
    }

    public Destination getDestination() {
        return this.destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public Place destination(Destination destination) {
        this.setDestination(destination);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Place)) {
            return false;
        }
        return getId() != null && getId().equals(((Place) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Place{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", destinationSight='" + getDestinationSight() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", defaultImage='" + getDefaultImage() + "'" +
            ", defaultImageData='" + getDefaultImageData() + "'" +
            ", defaultImageDataContentType='" + getDefaultImageDataContentType() + "'" +
            "}";
    }
}
