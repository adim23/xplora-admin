package gr.adr.xplora.admin.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link gr.adr.xplora.admin.domain.PlaceCategory} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PlaceCategoryDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;

    private String icon;

    private Boolean enabled;

    private LocalDate createdDate;

    private String defaultImage;

    @Lob
    private byte[] defaultImageData;

    private String defaultImageDataContentType;

    private UserDTO createdBy;

    private Set<PlaceDTO> places = new HashSet<>();

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
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

    public Set<PlaceDTO> getPlaces() {
        return places;
    }

    public void setPlaces(Set<PlaceDTO> places) {
        this.places = places;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlaceCategoryDTO)) {
            return false;
        }

        PlaceCategoryDTO placeCategoryDTO = (PlaceCategoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, placeCategoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlaceCategoryDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", icon='" + getIcon() + "'" +
            ", enabled='" + getEnabled() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", defaultImage='" + getDefaultImage() + "'" +
            ", defaultImageData='" + getDefaultImageData() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", places=" + getPlaces() +
            "}";
    }
}
