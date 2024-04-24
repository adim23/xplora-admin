package gr.adr.xplora.admin.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link gr.adr.xplora.admin.domain.TourExtra} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TourExtraDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;

    @NotNull
    private Boolean enabled;

    private String icon;

    private Double price;

    private Double offer;

    private String shopProductId;

    private String shopUrl;

    private LocalDate createdDate;

    private String defaultImage;

    @Lob
    private byte[] defaultImageData;

    private String defaultImageDataContentType;

    private UserDTO createdBy;

    private Set<TagDTO> tags = new HashSet<>();

    private Set<TourExtraCategoryDTO> categories = new HashSet<>();

    private Set<TourDTO> tours = new HashSet<>();

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getOffer() {
        return offer;
    }

    public void setOffer(Double offer) {
        this.offer = offer;
    }

    public String getShopProductId() {
        return shopProductId;
    }

    public void setShopProductId(String shopProductId) {
        this.shopProductId = shopProductId;
    }

    public String getShopUrl() {
        return shopUrl;
    }

    public void setShopUrl(String shopUrl) {
        this.shopUrl = shopUrl;
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

    public Set<TourExtraCategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(Set<TourExtraCategoryDTO> categories) {
        this.categories = categories;
    }

    public Set<TourDTO> getTours() {
        return tours;
    }

    public void setTours(Set<TourDTO> tours) {
        this.tours = tours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TourExtraDTO)) {
            return false;
        }

        TourExtraDTO tourExtraDTO = (TourExtraDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tourExtraDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TourExtraDTO{" +
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
            ", createdBy=" + getCreatedBy() +
            ", tags=" + getTags() +
            ", categories=" + getCategories() +
            ", tours=" + getTours() +
            "}";
    }
}
