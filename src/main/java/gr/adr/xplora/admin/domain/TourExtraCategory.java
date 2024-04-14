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
 * A TourExtraCategory.
 */
@Entity
@Table(name = "tour_extra_category")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TourExtraCategory implements Serializable {

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

    @Column(name = "default_image")
    private String defaultImage;

    @Lob
    @Column(name = "default_image_data")
    private byte[] defaultImageData;

    @Column(name = "default_image_data_content_type")
    private String defaultImageDataContentType;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "shop_category_id")
    private String shopCategoryId;

    @Column(name = "shop_url")
    private String shopUrl;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tourExtraCategory")
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
    @JsonIgnoreProperties(value = { "contents", "createdBy", "tags", "categories", "tours" }, allowSetters = true)
    private Set<TourExtra> extras = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TourExtraCategory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public TourExtraCategory code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIcon() {
        return this.icon;
    }

    public TourExtraCategory icon(String icon) {
        this.setIcon(icon);
        return this;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDefaultImage() {
        return this.defaultImage;
    }

    public TourExtraCategory defaultImage(String defaultImage) {
        this.setDefaultImage(defaultImage);
        return this;
    }

    public void setDefaultImage(String defaultImage) {
        this.defaultImage = defaultImage;
    }

    public byte[] getDefaultImageData() {
        return this.defaultImageData;
    }

    public TourExtraCategory defaultImageData(byte[] defaultImageData) {
        this.setDefaultImageData(defaultImageData);
        return this;
    }

    public void setDefaultImageData(byte[] defaultImageData) {
        this.defaultImageData = defaultImageData;
    }

    public String getDefaultImageDataContentType() {
        return this.defaultImageDataContentType;
    }

    public TourExtraCategory defaultImageDataContentType(String defaultImageDataContentType) {
        this.defaultImageDataContentType = defaultImageDataContentType;
        return this;
    }

    public void setDefaultImageDataContentType(String defaultImageDataContentType) {
        this.defaultImageDataContentType = defaultImageDataContentType;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public TourExtraCategory enabled(Boolean enabled) {
        this.setEnabled(enabled);
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getShopCategoryId() {
        return this.shopCategoryId;
    }

    public TourExtraCategory shopCategoryId(String shopCategoryId) {
        this.setShopCategoryId(shopCategoryId);
        return this;
    }

    public void setShopCategoryId(String shopCategoryId) {
        this.shopCategoryId = shopCategoryId;
    }

    public String getShopUrl() {
        return this.shopUrl;
    }

    public TourExtraCategory shopUrl(String shopUrl) {
        this.setShopUrl(shopUrl);
        return this;
    }

    public void setShopUrl(String shopUrl) {
        this.shopUrl = shopUrl;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public TourExtraCategory createdDate(LocalDate createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Set<Content> getContents() {
        return this.contents;
    }

    public void setContents(Set<Content> contents) {
        if (this.contents != null) {
            this.contents.forEach(i -> i.setTourExtraCategory(null));
        }
        if (contents != null) {
            contents.forEach(i -> i.setTourExtraCategory(this));
        }
        this.contents = contents;
    }

    public TourExtraCategory contents(Set<Content> contents) {
        this.setContents(contents);
        return this;
    }

    public TourExtraCategory addContents(Content content) {
        this.contents.add(content);
        content.setTourExtraCategory(this);
        return this;
    }

    public TourExtraCategory removeContents(Content content) {
        this.contents.remove(content);
        content.setTourExtraCategory(null);
        return this;
    }

    public User getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(User user) {
        this.createdBy = user;
    }

    public TourExtraCategory createdBy(User user) {
        this.setCreatedBy(user);
        return this;
    }

    public Set<TourExtra> getExtras() {
        return this.extras;
    }

    public void setExtras(Set<TourExtra> tourExtras) {
        if (this.extras != null) {
            this.extras.forEach(i -> i.removeCategory(this));
        }
        if (tourExtras != null) {
            tourExtras.forEach(i -> i.addCategory(this));
        }
        this.extras = tourExtras;
    }

    public TourExtraCategory extras(Set<TourExtra> tourExtras) {
        this.setExtras(tourExtras);
        return this;
    }

    public TourExtraCategory addExtra(TourExtra tourExtra) {
        this.extras.add(tourExtra);
        tourExtra.getCategories().add(this);
        return this;
    }

    public TourExtraCategory removeExtra(TourExtra tourExtra) {
        this.extras.remove(tourExtra);
        tourExtra.getCategories().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TourExtraCategory)) {
            return false;
        }
        return getId() != null && getId().equals(((TourExtraCategory) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TourExtraCategory{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", icon='" + getIcon() + "'" +
            ", defaultImage='" + getDefaultImage() + "'" +
            ", defaultImageData='" + getDefaultImageData() + "'" +
            ", defaultImageDataContentType='" + getDefaultImageDataContentType() + "'" +
            ", enabled='" + getEnabled() + "'" +
            ", shopCategoryId='" + getShopCategoryId() + "'" +
            ", shopUrl='" + getShopUrl() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
