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
 * A TourExtra.
 */
@Entity
@Table(name = "tour_extra")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TourExtra implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @Column(name = "icon")
    private String icon;

    @Column(name = "price")
    private Double price;

    @Column(name = "offer")
    private Double offer;

    @Column(name = "shop_product_id")
    private String shopProductId;

    @Column(name = "shop_url")
    private String shopUrl;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "default_image")
    private String defaultImage;

    @Lob
    @Column(name = "default_image_data")
    private byte[] defaultImageData;

    @Column(name = "default_image_data_content_type")
    private String defaultImageDataContentType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tourExtra")
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
            "tourExtraCategory",
            "tourExtra",
            "vehicle",
            "driver",
        },
        allowSetters = true
    )
    private Set<ImageFile> images = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tourExtra")
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
    private Set<Content> contents = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_tour_extra__tags",
        joinColumns = @JoinColumn(name = "tour_extra_id"),
        inverseJoinColumns = @JoinColumn(name = "tags_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "names", "places", "tours", "tourExtras", "webPages" }, allowSetters = true)
    private Set<Tag> tags = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_tour_extra__category",
        joinColumns = @JoinColumn(name = "tour_extra_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "images", "contents", "createdBy", "extras" }, allowSetters = true)
    private Set<TourExtraCategory> categories = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tourExtras")
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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TourExtra id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public TourExtra code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public TourExtra enabled(Boolean enabled) {
        this.setEnabled(enabled);
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getIcon() {
        return this.icon;
    }

    public TourExtra icon(String icon) {
        this.setIcon(icon);
        return this;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Double getPrice() {
        return this.price;
    }

    public TourExtra price(Double price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getOffer() {
        return this.offer;
    }

    public TourExtra offer(Double offer) {
        this.setOffer(offer);
        return this;
    }

    public void setOffer(Double offer) {
        this.offer = offer;
    }

    public String getShopProductId() {
        return this.shopProductId;
    }

    public TourExtra shopProductId(String shopProductId) {
        this.setShopProductId(shopProductId);
        return this;
    }

    public void setShopProductId(String shopProductId) {
        this.shopProductId = shopProductId;
    }

    public String getShopUrl() {
        return this.shopUrl;
    }

    public TourExtra shopUrl(String shopUrl) {
        this.setShopUrl(shopUrl);
        return this;
    }

    public void setShopUrl(String shopUrl) {
        this.shopUrl = shopUrl;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public TourExtra createdDate(LocalDate createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getDefaultImage() {
        return this.defaultImage;
    }

    public TourExtra defaultImage(String defaultImage) {
        this.setDefaultImage(defaultImage);
        return this;
    }

    public void setDefaultImage(String defaultImage) {
        this.defaultImage = defaultImage;
    }

    public byte[] getDefaultImageData() {
        return this.defaultImageData;
    }

    public TourExtra defaultImageData(byte[] defaultImageData) {
        this.setDefaultImageData(defaultImageData);
        return this;
    }

    public void setDefaultImageData(byte[] defaultImageData) {
        this.defaultImageData = defaultImageData;
    }

    public String getDefaultImageDataContentType() {
        return this.defaultImageDataContentType;
    }

    public TourExtra defaultImageDataContentType(String defaultImageDataContentType) {
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
            this.images.forEach(i -> i.setTourExtra(null));
        }
        if (imageFiles != null) {
            imageFiles.forEach(i -> i.setTourExtra(this));
        }
        this.images = imageFiles;
    }

    public TourExtra images(Set<ImageFile> imageFiles) {
        this.setImages(imageFiles);
        return this;
    }

    public TourExtra addImages(ImageFile imageFile) {
        this.images.add(imageFile);
        imageFile.setTourExtra(this);
        return this;
    }

    public TourExtra removeImages(ImageFile imageFile) {
        this.images.remove(imageFile);
        imageFile.setTourExtra(null);
        return this;
    }

    public Set<Content> getContents() {
        return this.contents;
    }

    public void setContents(Set<Content> contents) {
        if (this.contents != null) {
            this.contents.forEach(i -> i.setTourExtra(null));
        }
        if (contents != null) {
            contents.forEach(i -> i.setTourExtra(this));
        }
        this.contents = contents;
    }

    public TourExtra contents(Set<Content> contents) {
        this.setContents(contents);
        return this;
    }

    public TourExtra addContents(Content content) {
        this.contents.add(content);
        content.setTourExtra(this);
        return this;
    }

    public TourExtra removeContents(Content content) {
        this.contents.remove(content);
        content.setTourExtra(null);
        return this;
    }

    public User getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(User user) {
        this.createdBy = user;
    }

    public TourExtra createdBy(User user) {
        this.setCreatedBy(user);
        return this;
    }

    public Set<Tag> getTags() {
        return this.tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public TourExtra tags(Set<Tag> tags) {
        this.setTags(tags);
        return this;
    }

    public TourExtra addTags(Tag tag) {
        this.tags.add(tag);
        return this;
    }

    public TourExtra removeTags(Tag tag) {
        this.tags.remove(tag);
        return this;
    }

    public Set<TourExtraCategory> getCategories() {
        return this.categories;
    }

    public void setCategories(Set<TourExtraCategory> tourExtraCategories) {
        this.categories = tourExtraCategories;
    }

    public TourExtra categories(Set<TourExtraCategory> tourExtraCategories) {
        this.setCategories(tourExtraCategories);
        return this;
    }

    public TourExtra addCategory(TourExtraCategory tourExtraCategory) {
        this.categories.add(tourExtraCategory);
        return this;
    }

    public TourExtra removeCategory(TourExtraCategory tourExtraCategory) {
        this.categories.remove(tourExtraCategory);
        return this;
    }

    public Set<Tour> getTours() {
        return this.tours;
    }

    public void setTours(Set<Tour> tours) {
        if (this.tours != null) {
            this.tours.forEach(i -> i.removeTourExtra(this));
        }
        if (tours != null) {
            tours.forEach(i -> i.addTourExtra(this));
        }
        this.tours = tours;
    }

    public TourExtra tours(Set<Tour> tours) {
        this.setTours(tours);
        return this;
    }

    public TourExtra addTours(Tour tour) {
        this.tours.add(tour);
        tour.getTourExtras().add(this);
        return this;
    }

    public TourExtra removeTours(Tour tour) {
        this.tours.remove(tour);
        tour.getTourExtras().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TourExtra)) {
            return false;
        }
        return getId() != null && getId().equals(((TourExtra) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TourExtra{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", enabled='" + getEnabled() + "'" +
            ", icon='" + getIcon() + "'" +
            ", price=" + getPrice() +
            ", offer=" + getOffer() +
            ", shopProductId='" + getShopProductId() + "'" +
            ", shopUrl='" + getShopUrl() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", defaultImage='" + getDefaultImage() + "'" +
            ", defaultImageData='" + getDefaultImageData() + "'" +
            ", defaultImageDataContentType='" + getDefaultImageDataContentType() + "'" +
            "}";
    }
}
