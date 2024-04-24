package gr.adr.xplora.admin.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link gr.adr.xplora.admin.domain.Place} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PlaceDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;

    @NotNull
    private Boolean enabled;

    private String icon;

    @NotNull
    private Boolean destinationSight;

    private String longitude;

    private String latitude;

    private LocalDate createdDate;

    private String defaultImage;

    @Lob
    private byte[] defaultImageData;

    private String defaultImageDataContentType;

    private UserDTO createdBy;

    private Set<TagDTO> tags = new HashSet<>();

    private Set<PlaceCategoryDTO> categories = new HashSet<>();

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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean getDestinationSight() {
        return destinationSight;
    }

    public void setDestinationSight(Boolean destinationSight) {
        this.destinationSight = destinationSight;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
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

    public Set<TagDTO> getTags() {
        return tags;
    }

    public void setTags(Set<TagDTO> tags) {
        this.tags = tags;
    }

    public Set<PlaceCategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(Set<PlaceCategoryDTO> categories) {
        this.categories = categories;
    }

    public DestinationDTO getDestination() {
        return destination;
    }

    public void setDestination(DestinationDTO destination) {
        this.destination = destination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlaceDTO)) {
            return false;
        }

        PlaceDTO placeDTO = (PlaceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, placeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlaceDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", enabled='" + getEnabled() + "'" +
            ", icon='" + getIcon() + "'" +
            ", destinationSight='" + getDestinationSight() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", defaultImage='" + getDefaultImage() + "'" +
            ", defaultImageData='" + getDefaultImageData() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", tags=" + getTags() +
            ", categories=" + getCategories() +
            ", destination=" + getDestination() +
            "}";
    }
}
