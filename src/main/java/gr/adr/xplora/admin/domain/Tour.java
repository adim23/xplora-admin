package gr.adr.xplora.admin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import gr.adr.xplora.admin.domain.enumeration.TourMode;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Tour.
 */
@Entity
@Table(name = "tour")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Tour implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "mode", nullable = false)
    private TourMode mode;

    @NotNull
    @Column(name = "duration", nullable = false)
    private Integer duration;

    @NotNull
    @Column(name = "pet_friendly", nullable = false)
    private Boolean petFriendly;

    @NotNull
    @Column(name = "kids_allowed", nullable = false)
    private Boolean kidsAllowed;

    @Column(name = "available_from_date")
    private LocalDate availableFromDate;

    @Column(name = "available_to_date")
    private LocalDate availableToDate;

    @NotNull
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @Column(name = "initial_price")
    private Double initialPrice;

    @Column(name = "price")
    private Double price;

    @Column(name = "badge")
    private String badge;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "widget_id")
    private String widgetId;

    @Column(name = "external_id")
    private String externalId;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "default_image")
    private String defaultImage;

    @Lob
    @Column(name = "default_image_data")
    private byte[] defaultImageData;

    @Lob
    @Column(name = "css_style")
    private String cssStyle;

    @Column(name = "default_image_data_content_type")
    private String defaultImageDataContentType;

    @NotNull
    @Column(name = "audio_guide", nullable = false)
    private Boolean audioGuide;

    @NotNull
    @Column(name = "tour_guide", nullable = false)
    private Boolean tourGuide;

    @NotNull
    @Column(name = "accessibility", nullable = false)
    private Boolean accessibility;

    @Column(name = "icon")
    private String icon;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tour")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "infos", "createdBy", "tour", "place" }, allowSetters = true)
    private Set<TourStep> steps = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tour")
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tourExtraInfo")
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
    private Set<Content> extraInfos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tour")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "tourSteps", "images", "contents", "createdBy", "tags", "categories", "destination" },
        allowSetters = true
    )
    private Place meetingPoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "tourSteps", "images", "contents", "createdBy", "tags", "categories", "destination" },
        allowSetters = true
    )
    private Place finishPoint;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_tour__tour_extra",
        joinColumns = @JoinColumn(name = "tour_id"),
        inverseJoinColumns = @JoinColumn(name = "tour_extra_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "contents", "createdBy", "tags", "categories", "tours" }, allowSetters = true)
    private Set<TourExtra> tourExtras = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "rel_tour__tags", joinColumns = @JoinColumn(name = "tour_id"), inverseJoinColumns = @JoinColumn(name = "tags_id"))
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "names", "places", "tours", "tourExtras", "webPages" }, allowSetters = true)
    private Set<Tag> tags = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_tour__promotions",
        joinColumns = @JoinColumn(name = "tour_id"),
        inverseJoinColumns = @JoinColumn(name = "promotions_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "titles", "tours" }, allowSetters = true)
    private Set<Promotion> promotions = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_tour__category",
        joinColumns = @JoinColumn(name = "tour_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "children", "images", "menus", "contents", "createdBy", "parent", "tours" }, allowSetters = true)
    private Set<TourCategory> categories = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "tours", "places", "images", "contents", "createdBy" }, allowSetters = true)
    private Destination destination;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return this.id;
    }

    public Tour id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Tour code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public TourMode getMode() {
        return this.mode;
    }

    public Tour mode(TourMode mode) {
        this.setMode(mode);
        return this;
    }

    public void setMode(TourMode mode) {
        this.mode = mode;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public Tour duration(Integer duration) {
        this.setDuration(duration);
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Boolean getPetFriendly() {
        return this.petFriendly;
    }

    public Tour petFriendly(Boolean petFriendly) {
        this.setPetFriendly(petFriendly);
        return this;
    }

    public void setPetFriendly(Boolean petFriendly) {
        this.petFriendly = petFriendly;
    }

    public Boolean getKidsAllowed() {
        return this.kidsAllowed;
    }

    public Tour kidsAllowed(Boolean kidsAllowed) {
        this.setKidsAllowed(kidsAllowed);
        return this;
    }

    public void setKidsAllowed(Boolean kidsAllowed) {
        this.kidsAllowed = kidsAllowed;
    }

    public LocalDate getAvailableFromDate() {
        return this.availableFromDate;
    }

    public Tour availableFromDate(LocalDate availableFromDate) {
        this.setAvailableFromDate(availableFromDate);
        return this;
    }

    public void setAvailableFromDate(LocalDate availableFromDate) {
        this.availableFromDate = availableFromDate;
    }

    public LocalDate getAvailableToDate() {
        return this.availableToDate;
    }

    public Tour availableToDate(LocalDate availableToDate) {
        this.setAvailableToDate(availableToDate);
        return this;
    }

    public void setAvailableToDate(LocalDate availableToDate) {
        this.availableToDate = availableToDate;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public Tour enabled(Boolean enabled) {
        this.setEnabled(enabled);
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Double getInitialPrice() {
        return this.initialPrice;
    }

    public Tour initialPrice(Double initialPrice) {
        this.setInitialPrice(initialPrice);
        return this;
    }

    public void setInitialPrice(Double initialPrice) {
        this.initialPrice = initialPrice;
    }

    public Double getPrice() {
        return this.price;
    }

    public Tour price(Double price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getBadge() {
        return this.badge;
    }

    public Tour badge(String badge) {
        this.setBadge(badge);
        return this;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public Integer getRating() {
        return this.rating;
    }

    public Tour rating(Integer rating) {
        this.setRating(rating);
        return this;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getWidgetId() {
        return this.widgetId;
    }

    public Tour widgetId(String widgetId) {
        this.setWidgetId(widgetId);
        return this;
    }

    public void setWidgetId(String widgetId) {
        this.widgetId = widgetId;
    }

    public String getExternalId() {
        return this.externalId;
    }

    public Tour externalId(String externalId) {
        this.setExternalId(externalId);
        return this;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public Tour createdDate(LocalDate createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getDefaultImage() {
        return this.defaultImage;
    }

    public Tour defaultImage(String defaultImage) {
        this.setDefaultImage(defaultImage);
        return this;
    }

    public void setDefaultImage(String defaultImage) {
        this.defaultImage = defaultImage;
    }

    public byte[] getDefaultImageData() {
        return this.defaultImageData;
    }

    public Tour defaultImageData(byte[] defaultImageData) {
        this.setDefaultImageData(defaultImageData);
        return this;
    }

    public void setDefaultImageData(byte[] defaultImageData) {
        this.defaultImageData = defaultImageData;
    }

    public String getDefaultImageDataContentType() {
        return this.defaultImageDataContentType;
    }

    public Tour defaultImageDataContentType(String defaultImageDataContentType) {
        this.defaultImageDataContentType = defaultImageDataContentType;
        return this;
    }

    public void setDefaultImageDataContentType(String defaultImageDataContentType) {
        this.defaultImageDataContentType = defaultImageDataContentType;
    }

    public String getCssStyle() {
        return cssStyle;
    }

    public void setCssStyle(String cssStyle) {
        this.cssStyle = cssStyle;
    }

    public Set<TourStep> getSteps() {
        return this.steps;
    }

    public void setSteps(Set<TourStep> tourSteps) {
        if (this.steps != null) {
            this.steps.forEach(i -> i.setTour(null));
        }
        if (tourSteps != null) {
            tourSteps.forEach(i -> i.setTour(this));
        }
        this.steps = tourSteps;
    }

    public Tour steps(Set<TourStep> tourSteps) {
        this.setSteps(tourSteps);
        return this;
    }

    public Tour addSteps(TourStep tourStep) {
        this.steps.add(tourStep);
        tourStep.setTour(this);
        return this;
    }

    public Tour removeSteps(TourStep tourStep) {
        this.steps.remove(tourStep);
        tourStep.setTour(null);
        return this;
    }

    public Set<ImageFile> getImages() {
        return this.images;
    }

    public void setImages(Set<ImageFile> imageFiles) {
        if (this.images != null) {
            this.images.forEach(i -> i.setTour(null));
        }
        if (imageFiles != null) {
            imageFiles.forEach(i -> i.setTour(this));
        }
        this.images = imageFiles;
    }

    public Tour images(Set<ImageFile> imageFiles) {
        this.setImages(imageFiles);
        return this;
    }

    public Tour addImages(ImageFile imageFile) {
        this.images.add(imageFile);
        imageFile.setTour(this);
        return this;
    }

    public Tour removeImages(ImageFile imageFile) {
        this.images.remove(imageFile);
        imageFile.setTour(null);
        return this;
    }

    public Set<Content> getExtraInfos() {
        return this.extraInfos;
    }

    public void setExtraInfos(Set<Content> contents) {
        if (this.extraInfos != null) {
            this.extraInfos.forEach(i -> i.setTourExtraInfo(null));
        }
        if (contents != null) {
            contents.forEach(i -> i.setTourExtraInfo(this));
        }
        this.extraInfos = contents;
    }

    public Tour extraInfos(Set<Content> contents) {
        this.setExtraInfos(contents);
        return this;
    }

    public Tour addExtraInfo(Content content) {
        this.extraInfos.add(content);
        content.setTourExtraInfo(this);
        return this;
    }

    public Tour removeExtraInfo(Content content) {
        this.extraInfos.remove(content);
        content.setTourExtraInfo(null);
        return this;
    }

    public Set<Content> getContents() {
        return this.contents;
    }

    public void setContents(Set<Content> contents) {
        if (this.contents != null) {
            this.contents.forEach(i -> i.setTour(null));
        }
        if (contents != null) {
            contents.forEach(i -> i.setTour(this));
        }
        this.contents = contents;
    }

    public Tour contents(Set<Content> contents) {
        this.setContents(contents);
        return this;
    }

    public Tour addContents(Content content) {
        this.contents.add(content);
        content.setTour(this);
        return this;
    }

    public Tour removeContents(Content content) {
        this.contents.remove(content);
        content.setTour(null);
        return this;
    }

    public User getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(User user) {
        this.createdBy = user;
    }

    public Tour createdBy(User user) {
        this.setCreatedBy(user);
        return this;
    }

    public Place getMeetingPoint() {
        return this.meetingPoint;
    }

    public void setMeetingPoint(Place place) {
        this.meetingPoint = place;
    }

    public Tour meetingPoint(Place place) {
        this.setMeetingPoint(place);
        return this;
    }

    public Place getFinishPoint() {
        return this.finishPoint;
    }

    public void setFinishPoint(Place place) {
        this.finishPoint = place;
    }

    public Tour finishPoint(Place place) {
        this.setFinishPoint(place);
        return this;
    }

    public Set<TourExtra> getTourExtras() {
        return this.tourExtras;
    }

    public void setTourExtras(Set<TourExtra> tourExtras) {
        this.tourExtras = tourExtras;
    }

    public Tour tourExtras(Set<TourExtra> tourExtras) {
        this.setTourExtras(tourExtras);
        return this;
    }

    public Tour addTourExtra(TourExtra tourExtra) {
        this.tourExtras.add(tourExtra);
        return this;
    }

    public Tour removeTourExtra(TourExtra tourExtra) {
        this.tourExtras.remove(tourExtra);
        return this;
    }

    public Set<Tag> getTags() {
        return this.tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Tour tags(Set<Tag> tags) {
        this.setTags(tags);
        return this;
    }

    public Tour addTags(Tag tag) {
        this.tags.add(tag);
        return this;
    }

    public Tour removeTags(Tag tag) {
        this.tags.remove(tag);
        return this;
    }

    public Set<Promotion> getPromotions() {
        return this.promotions;
    }

    public void setPromotions(Set<Promotion> promotions) {
        this.promotions = promotions;
    }

    public Tour promotions(Set<Promotion> promotions) {
        this.setPromotions(promotions);
        return this;
    }

    public Tour addPromotions(Promotion promotion) {
        this.promotions.add(promotion);
        return this;
    }

    public Tour removePromotions(Promotion promotion) {
        this.promotions.remove(promotion);
        return this;
    }

    public Set<TourCategory> getCategories() {
        return this.categories;
    }

    public void setCategories(Set<TourCategory> tourCategories) {
        this.categories = tourCategories;
    }

    public Tour categories(Set<TourCategory> tourCategories) {
        this.setCategories(tourCategories);
        return this;
    }

    public Tour addCategory(TourCategory tourCategory) {
        this.categories.add(tourCategory);
        return this;
    }

    public Tour removeCategory(TourCategory tourCategory) {
        this.categories.remove(tourCategory);
        return this;
    }

    public Destination getDestination() {
        return this.destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public Tour destination(Destination destination) {
        this.setDestination(destination);
        return this;
    }

    public Boolean getAudioGuide() {
        return audioGuide;
    }

    public void setAudioGuide(Boolean audioGuide) {
        this.audioGuide = audioGuide;
    }

    public Boolean getTourGuide() {
        return tourGuide;
    }

    public void setTourGuide(Boolean tourGuide) {
        this.tourGuide = tourGuide;
    }

    public Boolean getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(Boolean accessibility) {
        this.accessibility = accessibility;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tour)) {
            return false;
        }
        return getId() != null && getId().equals(((Tour) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tour{"
                + "id=" + getId()
                + ", code='" + getCode() + "'"
                + ", mode='" + getMode() + "'"
                + ", duration=" + getDuration()
                + ", petFriendly='" + getPetFriendly() + "'"
                + ", kidsAllowed='" + getKidsAllowed() + "'"
                + ", availableFromDate='" + getAvailableFromDate() + "'"
                + ", availableToDate='" + getAvailableToDate() + "'"
                + ", enabled='" + getEnabled() + "'"
                + ", initialPrice=" + getInitialPrice()
                + ", price=" + getPrice()
                + ", badge='" + getBadge() + "'"
                + ", rating=" + getRating()
                + ", widgetId='" + getWidgetId() + "'"
                + ", externalId='" + getExternalId() + "'"
                + ", createdDate='" + getCreatedDate() + "'"
                + ", defaultImage='" + getDefaultImage() + "'"
                + ", defaultImageDataContentType='" + getDefaultImageDataContentType() + "'"
                + ", accessibility='" + getAccessibility() + "'"
                + ", audioGuide='" + getAudioGuide() + "'"
                + ", tourGuide='" + getTourGuide() + "'"
                + ", icon='" + getIcon() + "'"
                + "}";
    }
}
