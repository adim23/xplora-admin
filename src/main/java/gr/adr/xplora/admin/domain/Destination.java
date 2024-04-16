package gr.adr.xplora.admin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Destination.
 */
@Entity
@Table(name = "destination")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Destination implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "default_image")
    private String defaultImage;

    @Lob
    @Column(name = "default_image_data")
    private byte[] defaultImageData;

    @Column(name = "default_image_data_content_type")
    private String defaultImageDataContentType;

    @Lob
    @Column(name = "css_style")
    private String cssStyle;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "destination")
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "destination")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "tourSteps", "images", "contents", "createdBy", "tags", "categories", "destination" },
        allowSetters = true
    )
    private Set<Place> places = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "destination")
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "destination")
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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Destination id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Destination code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public Destination createdDate(LocalDate createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getDefaultImage() {
        return this.defaultImage;
    }

    public Destination defaultImage(String defaultImage) {
        this.setDefaultImage(defaultImage);
        return this;
    }

    public void setDefaultImage(String defaultImage) {
        this.defaultImage = defaultImage;
    }

    public byte[] getDefaultImageData() {
        return this.defaultImageData;
    }

    public Destination defaultImageData(byte[] defaultImageData) {
        this.setDefaultImageData(defaultImageData);
        return this;
    }

    public void setDefaultImageData(byte[] defaultImageData) {
        this.defaultImageData = defaultImageData;
    }

    public String getDefaultImageDataContentType() {
        return this.defaultImageDataContentType;
    }

    public Destination defaultImageDataContentType(String defaultImageDataContentType) {
        this.defaultImageDataContentType = defaultImageDataContentType;
        return this;
    }

    public void setDefaultImageDataContentType(String defaultImageDataContentType) {
        this.defaultImageDataContentType = defaultImageDataContentType;
    }

    public Set<Tour> getTours() {
        return this.tours;
    }

    public void setTours(Set<Tour> tours) {
        if (this.tours != null) {
            this.tours.forEach(i -> i.setDestination(null));
        }
        if (tours != null) {
            tours.forEach(i -> i.setDestination(this));
        }
        this.tours = tours;
    }

    public Destination tours(Set<Tour> tours) {
        this.setTours(tours);
        return this;
    }

    public Destination addTours(Tour tour) {
        this.tours.add(tour);
        tour.setDestination(this);
        return this;
    }

    public Destination removeTours(Tour tour) {
        this.tours.remove(tour);
        tour.setDestination(null);
        return this;
    }

    public Set<Place> getPlaces() {
        return this.places;
    }

    public void setPlaces(Set<Place> places) {
        if (this.places != null) {
            this.places.forEach(i -> i.setDestination(null));
        }
        if (places != null) {
            places.forEach(i -> i.setDestination(this));
        }
        this.places = places;
    }

    public Destination places(Set<Place> places) {
        this.setPlaces(places);
        return this;
    }

    public Destination addPlaces(Place place) {
        this.places.add(place);
        place.setDestination(this);
        return this;
    }

    public Destination removePlaces(Place place) {
        this.places.remove(place);
        place.setDestination(null);
        return this;
    }

    public Set<ImageFile> getImages() {
        return this.images;
    }

    public void setImages(Set<ImageFile> imageFiles) {
        if (this.images != null) {
            this.images.forEach(i -> i.setDestination(null));
        }
        if (imageFiles != null) {
            imageFiles.forEach(i -> i.setDestination(this));
        }
        this.images = imageFiles;
    }

    public Destination images(Set<ImageFile> imageFiles) {
        this.setImages(imageFiles);
        return this;
    }

    public Destination addImages(ImageFile imageFile) {
        this.images.add(imageFile);
        imageFile.setDestination(this);
        return this;
    }

    public Destination removeImages(ImageFile imageFile) {
        this.images.remove(imageFile);
        imageFile.setDestination(null);
        return this;
    }

    public Set<Content> getContents() {
        return this.contents;
    }

    public void setContents(Set<Content> contents) {
        if (this.contents != null) {
            this.contents.forEach(i -> i.setDestination(null));
        }
        if (contents != null) {
            contents.forEach(i -> i.setDestination(this));
        }
        this.contents = contents;
    }

    public Destination contents(Set<Content> contents) {
        this.setContents(contents);
        return this;
    }

    public Destination addContents(Content content) {
        this.contents.add(content);
        content.setDestination(this);
        return this;
    }

    public Destination removeContents(Content content) {
        this.contents.remove(content);
        content.setDestination(null);
        return this;
    }

    public User getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(User user) {
        this.createdBy = user;
    }

    public Destination createdBy(User user) {
        this.setCreatedBy(user);
        return this;
    }

    public String getCssStyle() {
        return cssStyle;
    }

    public void setCssStyle(String cssStyle) {
        this.cssStyle = cssStyle;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Destination)) {
            return false;
        }
        return getId() != null && getId().equals(((Destination) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Destination{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", defaultImage='" + getDefaultImage() + "'" +
            ", defaultImageData='" + Arrays.toString(getDefaultImageData()) + "'" +
            ", defaultImageDataContentType='" + getDefaultImageDataContentType() + "'" +
            "}";
    }
}
