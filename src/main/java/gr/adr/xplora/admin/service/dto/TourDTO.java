package gr.adr.xplora.admin.service.dto;

import gr.adr.xplora.admin.domain.enumeration.DurationMeasure;
import gr.adr.xplora.admin.domain.enumeration.TourKind;
import gr.adr.xplora.admin.domain.enumeration.TourMode;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link gr.adr.xplora.admin.domain.Tour} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TourDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;

    @NotNull
    private Boolean enabled;

    @NotNull
    private TourKind kind;

    private TourMode mode;

    private String icon;

    @NotNull
    private Integer duration;

    @NotNull
    private DurationMeasure durationMeasure;

    @NotNull
    private Boolean petFriendly;

    @NotNull
    private Boolean kidsAllowed;

    @NotNull
    private Boolean smoking;

    private LocalDate availableFromDate;

    private LocalDate availableToDate;

    private Double initialPrice;

    private Double price;

    private String badge;

    private Integer rating;

    private String widgetId;

    private String externalId;

    private LocalDate createdDate;

    private String defaultImage;

    @Lob
    private byte[] defaultImageData;

    private String defaultImageDataContentType;

    private Boolean accessibility;

    private Boolean audioGuide;

    private Boolean tourGuide;

    @Lob
    private String cssStyle;

    private LocalDate departure;

    private LocalDate returnTime;

    private Instant testIn;

    private ZonedDateTime testZ;

    private Duration dur;

    private TourContentDTO content;

    private UserDTO createdBy;

    private PlaceDTO meetingPoint;

    private PlaceDTO finishPoint;

    private Set<TourExtraDTO> tourExtras = new HashSet<>();

    private Set<TagDTO> tags = new HashSet<>();

    private Set<PromotionDTO> promotions = new HashSet<>();

    private Set<TourCategoryDTO> categories = new HashSet<>();

    private DestinationDTO destination;

    private TourCategoryDTO defaultCategory;

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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public TourKind getKind() {
        return kind;
    }

    public void setKind(TourKind kind) {
        this.kind = kind;
    }

    public TourMode getMode() {
        return mode;
    }

    public void setMode(TourMode mode) {
        this.mode = mode;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public DurationMeasure getDurationMeasure() {
        return durationMeasure;
    }

    public void setDurationMeasure(DurationMeasure durationMeasure) {
        this.durationMeasure = durationMeasure;
    }

    public Boolean getPetFriendly() {
        return petFriendly;
    }

    public void setPetFriendly(Boolean petFriendly) {
        this.petFriendly = petFriendly;
    }

    public Boolean getKidsAllowed() {
        return kidsAllowed;
    }

    public void setKidsAllowed(Boolean kidsAllowed) {
        this.kidsAllowed = kidsAllowed;
    }

    public Boolean getSmoking() {
        return smoking;
    }

    public void setSmoking(Boolean smoking) {
        this.smoking = smoking;
    }

    public LocalDate getAvailableFromDate() {
        return availableFromDate;
    }

    public void setAvailableFromDate(LocalDate availableFromDate) {
        this.availableFromDate = availableFromDate;
    }

    public LocalDate getAvailableToDate() {
        return availableToDate;
    }

    public void setAvailableToDate(LocalDate availableToDate) {
        this.availableToDate = availableToDate;
    }

    public Double getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(Double initialPrice) {
        this.initialPrice = initialPrice;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(String widgetId) {
        this.widgetId = widgetId;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getDefaultImage() {
        return defaultImage;
    }

    public void setDefaultImage(String defaultImage) {
        this.defaultImage = defaultImage;
    }

    public byte[] getDefaultImageData() {
        return defaultImageData;
    }

    public void setDefaultImageData(byte[] defaultImageData) {
        this.defaultImageData = defaultImageData;
    }

    public String getDefaultImageDataContentType() {
        return defaultImageDataContentType;
    }

    public void setDefaultImageDataContentType(String defaultImageDataContentType) {
        this.defaultImageDataContentType = defaultImageDataContentType;
    }

    public Boolean getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(Boolean accessibility) {
        this.accessibility = accessibility;
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

    public String getCssStyle() {
        return cssStyle;
    }

    public void setCssStyle(String cssStyle) {
        this.cssStyle = cssStyle;
    }

    public LocalDate getDeparture() {
        return departure;
    }

    public void setDeparture(LocalDate departure) {
        this.departure = departure;
    }

    public LocalDate getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(LocalDate returnTime) {
        this.returnTime = returnTime;
    }

    public Instant getTestIn() {
        return testIn;
    }

    public void setTestIn(Instant testIn) {
        this.testIn = testIn;
    }

    public ZonedDateTime getTestZ() {
        return testZ;
    }

    public void setTestZ(ZonedDateTime testZ) {
        this.testZ = testZ;
    }

    public Duration getDur() {
        return dur;
    }

    public void setDur(Duration dur) {
        this.dur = dur;
    }

    public TourContentDTO getContent() {
        return content;
    }

    public void setContent(TourContentDTO content) {
        this.content = content;
    }

    public UserDTO getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserDTO createdBy) {
        this.createdBy = createdBy;
    }

    public PlaceDTO getMeetingPoint() {
        return meetingPoint;
    }

    public void setMeetingPoint(PlaceDTO meetingPoint) {
        this.meetingPoint = meetingPoint;
    }

    public PlaceDTO getFinishPoint() {
        return finishPoint;
    }

    public void setFinishPoint(PlaceDTO finishPoint) {
        this.finishPoint = finishPoint;
    }

    public Set<TourExtraDTO> getTourExtras() {
        return tourExtras;
    }

    public void setTourExtras(Set<TourExtraDTO> tourExtras) {
        this.tourExtras = tourExtras;
    }

    public Set<TagDTO> getTags() {
        return tags;
    }

    public void setTags(Set<TagDTO> tags) {
        this.tags = tags;
    }

    public Set<PromotionDTO> getPromotions() {
        return promotions;
    }

    public void setPromotions(Set<PromotionDTO> promotions) {
        this.promotions = promotions;
    }

    public Set<TourCategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(Set<TourCategoryDTO> categories) {
        this.categories = categories;
    }

    public DestinationDTO getDestination() {
        return destination;
    }

    public void setDestination(DestinationDTO destination) {
        this.destination = destination;
    }

    public TourCategoryDTO getDefaultCategory() {
        return defaultCategory;
    }

    public void setDefaultCategory(TourCategoryDTO defaultCategory) {
        this.defaultCategory = defaultCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TourDTO)) {
            return false;
        }

        TourDTO tourDTO = (TourDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tourDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TourDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", enabled='" + getEnabled() + "'" +
            ", kind='" + getKind() + "'" +
            ", mode='" + getMode() + "'" +
            ", icon='" + getIcon() + "'" +
            ", duration=" + getDuration() +
            ", durationMeasure='" + getDurationMeasure() + "'" +
            ", petFriendly='" + getPetFriendly() + "'" +
            ", kidsAllowed='" + getKidsAllowed() + "'" +
            ", smoking='" + getSmoking() + "'" +
            ", availableFromDate='" + getAvailableFromDate() + "'" +
            ", availableToDate='" + getAvailableToDate() + "'" +
            ", initialPrice=" + getInitialPrice() +
            ", price=" + getPrice() +
            ", badge='" + getBadge() + "'" +
            ", rating=" + getRating() +
            ", widgetId='" + getWidgetId() + "'" +
            ", externalId='" + getExternalId() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", defaultImage='" + getDefaultImage() + "'" +
            ", defaultImageData='" + getDefaultImageData() + "'" +
            ", accessibility='" + getAccessibility() + "'" +
            ", audioGuide='" + getAudioGuide() + "'" +
            ", tourGuide='" + getTourGuide() + "'" +
            ", cssStyle='" + getCssStyle() + "'" +
            ", departure='" + getDeparture() + "'" +
            ", returnTime='" + getReturnTime() + "'" +
            ", testIn='" + getTestIn() + "'" +
            ", testZ='" + getTestZ() + "'" +
            ", dur='" + getDur() + "'" +
            ", content=" + getContent() +
            ", createdBy=" + getCreatedBy() +
            ", meetingPoint=" + getMeetingPoint() +
            ", finishPoint=" + getFinishPoint() +
            ", tourExtras=" + getTourExtras() +
            ", tags=" + getTags() +
            ", promotions=" + getPromotions() +
            ", categories=" + getCategories() +
            ", destination=" + getDestination() +
            ", defaultCategory=" + getDefaultCategory() +
            "}";
    }
}
