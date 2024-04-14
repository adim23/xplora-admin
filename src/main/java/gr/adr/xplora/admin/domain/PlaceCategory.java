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
 * A PlaceCategory.
 */
@Entity
@Table(name = "place_category")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PlaceCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "icon")
    private String icon;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "default_image")
    private String defaultImage;

    @Lob
    @Column(name = "default_image_data")
    private byte[] defaultImageData;

    @Column(name = "default_image_data_content_type")
    private String defaultImageDataContentType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "placeCategory")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "captions", "createdBy", "destination", "tour", "tourCategory", "place", "placeCategory", "vehicle", "driver" },
        allowSetters = true
    )
    private Set<ImageFile> images = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "placeCategory")
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

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "categories")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "tourSteps", "images", "contents", "createdBy", "tags", "categories", "destination" },
        allowSetters = true
    )
    private Set<Place> places = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PlaceCategory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public PlaceCategory code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIcon() {
        return this.icon;
    }

    public PlaceCategory icon(String icon) {
        this.setIcon(icon);
        return this;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public PlaceCategory enabled(Boolean enabled) {
        this.setEnabled(enabled);
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public PlaceCategory createdDate(LocalDate createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getDefaultImage() {
        return this.defaultImage;
    }

    public PlaceCategory defaultImage(String defaultImage) {
        this.setDefaultImage(defaultImage);
        return this;
    }

    public void setDefaultImage(String defaultImage) {
        this.defaultImage = defaultImage;
    }

    public byte[] getDefaultImageData() {
        return this.defaultImageData;
    }

    public PlaceCategory defaultImageData(byte[] defaultImageData) {
        this.setDefaultImageData(defaultImageData);
        return this;
    }

    public void setDefaultImageData(byte[] defaultImageData) {
        this.defaultImageData = defaultImageData;
    }

    public String getDefaultImageDataContentType() {
        return this.defaultImageDataContentType;
    }

    public PlaceCategory defaultImageDataContentType(String defaultImageDataContentType) {
        this.defaultImageDataContentType = defaultImageDataContentType;
        return this;
    }

    public void setDefaultImageDataContentType(String defaultImageDataContentType) {
        this.defaultImageDataContentType = defaultImageDataContentType;
    }

    public Set<ImageFile> getImages() {
        return this.images;
    }

    public void setImages(Set<ImageFile> imageFiles) {
        if (this.images != null) {
            this.images.forEach(i -> i.setPlaceCategory(null));
        }
        if (imageFiles != null) {
            imageFiles.forEach(i -> i.setPlaceCategory(this));
        }
        this.images = imageFiles;
    }

    public PlaceCategory images(Set<ImageFile> imageFiles) {
        this.setImages(imageFiles);
        return this;
    }

    public PlaceCategory addImages(ImageFile imageFile) {
        this.images.add(imageFile);
        imageFile.setPlaceCategory(this);
        return this;
    }

    public PlaceCategory removeImages(ImageFile imageFile) {
        this.images.remove(imageFile);
        imageFile.setPlaceCategory(null);
        return this;
    }

    public Set<Content> getContents() {
        return this.contents;
    }

    public void setContents(Set<Content> contents) {
        if (this.contents != null) {
            this.contents.forEach(i -> i.setPlaceCategory(null));
        }
        if (contents != null) {
            contents.forEach(i -> i.setPlaceCategory(this));
        }
        this.contents = contents;
    }

    public PlaceCategory contents(Set<Content> contents) {
        this.setContents(contents);
        return this;
    }

    public PlaceCategory addContents(Content content) {
        this.contents.add(content);
        content.setPlaceCategory(this);
        return this;
    }

    public PlaceCategory removeContents(Content content) {
        this.contents.remove(content);
        content.setPlaceCategory(null);
        return this;
    }

    public User getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(User user) {
        this.createdBy = user;
    }

    public PlaceCategory createdBy(User user) {
        this.setCreatedBy(user);
        return this;
    }

    public Set<Place> getPlaces() {
        return this.places;
    }

    public void setPlaces(Set<Place> places) {
        if (this.places != null) {
            this.places.forEach(i -> i.removeCategory(this));
        }
        if (places != null) {
            places.forEach(i -> i.addCategory(this));
        }
        this.places = places;
    }

    public PlaceCategory places(Set<Place> places) {
        this.setPlaces(places);
        return this;
    }

    public PlaceCategory addPlace(Place place) {
        this.places.add(place);
        place.getCategories().add(this);
        return this;
    }

    public PlaceCategory removePlace(Place place) {
        this.places.remove(place);
        place.getCategories().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlaceCategory)) {
            return false;
        }
        return getId() != null && getId().equals(((PlaceCategory) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlaceCategory{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", icon='" + getIcon() + "'" +
            ", enabled='" + getEnabled() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", defaultImage='" + getDefaultImage() + "'" +
            ", defaultImageData='" + getDefaultImageData() + "'" +
            ", defaultImageDataContentType='" + getDefaultImageDataContentType() + "'" +
            "}";
    }
}
