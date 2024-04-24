package gr.adr.xplora.admin.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link gr.adr.xplora.admin.domain.Destination} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DestinationDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;

    @NotNull
    private Boolean enabled;

    private String defaultImage;

    @Lob
    private byte[] defaultImageData;

    private String defaultImageDataContentType;

    @Lob
    private String cssStyle;

    private LocalDate createdDate;

    private UserDTO createdBy;

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

    public String getCssStyle() {
        return cssStyle;
    }

    public void setCssStyle(String cssStyle) {
        this.cssStyle = cssStyle;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public UserDTO getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserDTO createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DestinationDTO)) {
            return false;
        }

        DestinationDTO destinationDTO = (DestinationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, destinationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DestinationDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", enabled='" + getEnabled() + "'" +
            ", defaultImage='" + getDefaultImage() + "'" +
            ", defaultImageData='" + getDefaultImageData() + "'" +
            ", cssStyle='" + getCssStyle() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy=" + getCreatedBy() +
            "}";
    }
}
