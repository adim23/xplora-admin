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
 * A Menu.
 */
@Entity
@Table(name = "menu")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Menu implements Serializable {

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

    @Column(name = "uri")
    private String uri;

    @Column(name = "default_image")
    private String defaultImage;

    @Lob
    @Column(name = "default_image_data")
    private byte[] defaultImageData;

    @Column(name = "default_image_data_content_type")
    private String defaultImageDataContentType;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "children", "names", "createdBy", "page", "parent", "tourCategory", "destination" },
        allowSetters = true
    )
    private Set<Menu> children = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "menu")
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
    private Set<Content> names = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "menus", "contents", "createdBy", "tags" }, allowSetters = true)
    private WebPage page;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "children", "names", "createdBy", "page", "parent", "tourCategory", "destination" },
        allowSetters = true
    )
    private Menu parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "defaultTours", "children", "images", "menus", "contents", "createdBy", "parent", "tours" },
        allowSetters = true
    )
    private TourCategory tourCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "tours", "places", "images", "menus", "contents", "createdBy" }, allowSetters = true)
    private Destination destination;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Menu id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Menu code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public Menu enabled(Boolean enabled) {
        this.setEnabled(enabled);
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getIcon() {
        return this.icon;
    }

    public Menu icon(String icon) {
        this.setIcon(icon);
        return this;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUri() {
        return this.uri;
    }

    public Menu uri(String uri) {
        this.setUri(uri);
        return this;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getDefaultImage() {
        return this.defaultImage;
    }

    public Menu defaultImage(String defaultImage) {
        this.setDefaultImage(defaultImage);
        return this;
    }

    public void setDefaultImage(String defaultImage) {
        this.defaultImage = defaultImage;
    }

    public byte[] getDefaultImageData() {
        return this.defaultImageData;
    }

    public Menu defaultImageData(byte[] defaultImageData) {
        this.setDefaultImageData(defaultImageData);
        return this;
    }

    public void setDefaultImageData(byte[] defaultImageData) {
        this.defaultImageData = defaultImageData;
    }

    public String getDefaultImageDataContentType() {
        return this.defaultImageDataContentType;
    }

    public Menu defaultImageDataContentType(String defaultImageDataContentType) {
        this.defaultImageDataContentType = defaultImageDataContentType;
        return this;
    }

    public void setDefaultImageDataContentType(String defaultImageDataContentType) {
        this.defaultImageDataContentType = defaultImageDataContentType;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public Menu createdDate(LocalDate createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Set<Menu> getChildren() {
        return this.children;
    }

    public void setChildren(Set<Menu> menus) {
        if (this.children != null) {
            this.children.forEach(i -> i.setParent(null));
        }
        if (menus != null) {
            menus.forEach(i -> i.setParent(this));
        }
        this.children = menus;
    }

    public Menu children(Set<Menu> menus) {
        this.setChildren(menus);
        return this;
    }

    public Menu addChildren(Menu menu) {
        this.children.add(menu);
        menu.setParent(this);
        return this;
    }

    public Menu removeChildren(Menu menu) {
        this.children.remove(menu);
        menu.setParent(null);
        return this;
    }

    public Set<Content> getNames() {
        return this.names;
    }

    public void setNames(Set<Content> contents) {
        if (this.names != null) {
            this.names.forEach(i -> i.setMenu(null));
        }
        if (contents != null) {
            contents.forEach(i -> i.setMenu(this));
        }
        this.names = contents;
    }

    public Menu names(Set<Content> contents) {
        this.setNames(contents);
        return this;
    }

    public Menu addNames(Content content) {
        this.names.add(content);
        content.setMenu(this);
        return this;
    }

    public Menu removeNames(Content content) {
        this.names.remove(content);
        content.setMenu(null);
        return this;
    }

    public User getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(User user) {
        this.createdBy = user;
    }

    public Menu createdBy(User user) {
        this.setCreatedBy(user);
        return this;
    }

    public WebPage getPage() {
        return this.page;
    }

    public void setPage(WebPage webPage) {
        this.page = webPage;
    }

    public Menu page(WebPage webPage) {
        this.setPage(webPage);
        return this;
    }

    public Menu getParent() {
        return this.parent;
    }

    public void setParent(Menu menu) {
        this.parent = menu;
    }

    public Menu parent(Menu menu) {
        this.setParent(menu);
        return this;
    }

    public TourCategory getTourCategory() {
        return this.tourCategory;
    }

    public void setTourCategory(TourCategory tourCategory) {
        this.tourCategory = tourCategory;
    }

    public Menu tourCategory(TourCategory tourCategory) {
        this.setTourCategory(tourCategory);
        return this;
    }

    public Destination getDestination() {
        return this.destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public Menu destination(Destination destination) {
        this.setDestination(destination);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Menu)) {
            return false;
        }
        return getId() != null && getId().equals(((Menu) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Menu{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", enabled='" + getEnabled() + "'" +
            ", icon='" + getIcon() + "'" +
            ", uri='" + getUri() + "'" +
            ", defaultImage='" + getDefaultImage() + "'" +
            ", defaultImageData='" + getDefaultImageData() + "'" +
            ", defaultImageDataContentType='" + getDefaultImageDataContentType() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
