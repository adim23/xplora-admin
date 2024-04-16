package gr.adr.xplora.admin.service.dto;

import gr.adr.xplora.admin.domain.enumeration.TourMode;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
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
    private TourMode mode;

    @NotNull
    private Integer duration;

    @NotNull
    private Boolean petFriendly;

    @NotNull
    private Boolean kidsAllowed;

    private LocalDate availableFromDate;

    private LocalDate availableToDate;

    @NotNull
    private Boolean enabled;

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

    private Boolean audioGuide;

    private Boolean tourGuide;

    private Boolean accessibility;

    private String icon;

    private UserDTO createdBy;

    private PlaceDTO meetingPoint;

    private PlaceDTO finishPoint;

    private Set<TourExtraDTO> tourExtras = new HashSet<>();

    private Set<TagDTO> tags = new HashSet<>();

    private Set<PromotionDTO> promotions = new HashSet<>();

    private Set<TourCategoryDTO> categories = new HashSet<>();

    private DestinationDTO destination;

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

    public TourMode getMode() {
        return mode;
    }

    public void setMode(TourMode mode) {
        this.mode = mode;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
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
        return "TourDTO{"
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
                + ", accessibility='" + getAccessibility() + "'"
                + ", audioGuide='" + getAudioGuide() + "'"
                + ", tourGuide='" + getTourGuide() + "'"
                + ", icon='" + getIcon() + "'"
                + ", createdBy=" + getCreatedBy()
                + ", meetingPoint=" + getMeetingPoint()
                + ", finishPoint=" + getFinishPoint()
                + ", tourExtras=" + getTourExtras()
                + ", tags=" + getTags()
                + ", promotions=" + getPromotions()
                + ", categories=" + getCategories()
                + ", destination=" + getDestination()
                + "}";
    }
}
