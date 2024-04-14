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
 * A WebPage.
 */
@Entity
@Table(name = "web_page")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WebPage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "uri_path")
    private String uriPath;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "publish_date")
    private LocalDate publishDate;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "page")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "children", "names", "createdBy", "page", "parent", "tourCategory" }, allowSetters = true)
    private Set<Menu> menus = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "webPage")
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_web_page__tags",
        joinColumns = @JoinColumn(name = "web_page_id"),
        inverseJoinColumns = @JoinColumn(name = "tags_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "names", "places", "tours", "tourExtras", "webPages" }, allowSetters = true)
    private Set<Tag> tags = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public WebPage id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public WebPage code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUriPath() {
        return this.uriPath;
    }

    public WebPage uriPath(String uriPath) {
        this.setUriPath(uriPath);
        return this;
    }

    public void setUriPath(String uriPath) {
        this.uriPath = uriPath;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public WebPage enabled(Boolean enabled) {
        this.setEnabled(enabled);
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public LocalDate getPublishDate() {
        return this.publishDate;
    }

    public WebPage publishDate(LocalDate publishDate) {
        this.setPublishDate(publishDate);
        return this;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public WebPage createdDate(LocalDate createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Set<Menu> getMenus() {
        return this.menus;
    }

    public void setMenus(Set<Menu> menus) {
        if (this.menus != null) {
            this.menus.forEach(i -> i.setPage(null));
        }
        if (menus != null) {
            menus.forEach(i -> i.setPage(this));
        }
        this.menus = menus;
    }

    public WebPage menus(Set<Menu> menus) {
        this.setMenus(menus);
        return this;
    }

    public WebPage addMenus(Menu menu) {
        this.menus.add(menu);
        menu.setPage(this);
        return this;
    }

    public WebPage removeMenus(Menu menu) {
        this.menus.remove(menu);
        menu.setPage(null);
        return this;
    }

    public Set<Content> getContents() {
        return this.contents;
    }

    public void setContents(Set<Content> contents) {
        if (this.contents != null) {
            this.contents.forEach(i -> i.setWebPage(null));
        }
        if (contents != null) {
            contents.forEach(i -> i.setWebPage(this));
        }
        this.contents = contents;
    }

    public WebPage contents(Set<Content> contents) {
        this.setContents(contents);
        return this;
    }

    public WebPage addContents(Content content) {
        this.contents.add(content);
        content.setWebPage(this);
        return this;
    }

    public WebPage removeContents(Content content) {
        this.contents.remove(content);
        content.setWebPage(null);
        return this;
    }

    public User getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(User user) {
        this.createdBy = user;
    }

    public WebPage createdBy(User user) {
        this.setCreatedBy(user);
        return this;
    }

    public Set<Tag> getTags() {
        return this.tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public WebPage tags(Set<Tag> tags) {
        this.setTags(tags);
        return this;
    }

    public WebPage addTags(Tag tag) {
        this.tags.add(tag);
        return this;
    }

    public WebPage removeTags(Tag tag) {
        this.tags.remove(tag);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WebPage)) {
            return false;
        }
        return getId() != null && getId().equals(((WebPage) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WebPage{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", uriPath='" + getUriPath() + "'" +
            ", enabled='" + getEnabled() + "'" +
            ", publishDate='" + getPublishDate() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
