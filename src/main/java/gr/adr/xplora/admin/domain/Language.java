package gr.adr.xplora.admin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Language.
 */
@Entity
@Table(name = "language")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Language implements Serializable {

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

    @Column(name = "created_date")
    private LocalDate createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;

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
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "language")
    private Content content;

    @JsonIgnoreProperties(value = { "language", "tour" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "language")
    private TourContent tourContent;

    @JsonIgnoreProperties(value = { "language" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "language")
    private Prompt prompt;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Language id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Language code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIcon() {
        return this.icon;
    }

    public Language icon(String icon) {
        this.setIcon(icon);
        return this;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDefaultImage() {
        return this.defaultImage;
    }

    public Language defaultImage(String defaultImage) {
        this.setDefaultImage(defaultImage);
        return this;
    }

    public void setDefaultImage(String defaultImage) {
        this.defaultImage = defaultImage;
    }

    public byte[] getDefaultImageData() {
        return this.defaultImageData;
    }

    public Language defaultImageData(byte[] defaultImageData) {
        this.setDefaultImageData(defaultImageData);
        return this;
    }

    public void setDefaultImageData(byte[] defaultImageData) {
        this.defaultImageData = defaultImageData;
    }

    public String getDefaultImageDataContentType() {
        return this.defaultImageDataContentType;
    }

    public Language defaultImageDataContentType(String defaultImageDataContentType) {
        this.defaultImageDataContentType = defaultImageDataContentType;
        return this;
    }

    public void setDefaultImageDataContentType(String defaultImageDataContentType) {
        this.defaultImageDataContentType = defaultImageDataContentType;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public Language createdDate(LocalDate createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public User getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(User user) {
        this.createdBy = user;
    }

    public Language createdBy(User user) {
        this.setCreatedBy(user);
        return this;
    }

    public Content getContent() {
        return this.content;
    }

    public void setContent(Content content) {
        if (this.content != null) {
            this.content.setLanguage(null);
        }
        if (content != null) {
            content.setLanguage(this);
        }
        this.content = content;
    }

    public Language content(Content content) {
        this.setContent(content);
        return this;
    }

    public TourContent getTourContent() {
        return this.tourContent;
    }

    public void setTourContent(TourContent tourContent) {
        if (this.tourContent != null) {
            this.tourContent.setLanguage(null);
        }
        if (tourContent != null) {
            tourContent.setLanguage(this);
        }
        this.tourContent = tourContent;
    }

    public Language tourContent(TourContent tourContent) {
        this.setTourContent(tourContent);
        return this;
    }

    public Prompt getPrompt() {
        return this.prompt;
    }

    public void setPrompt(Prompt prompt) {
        if (this.prompt != null) {
            this.prompt.setLanguage(null);
        }
        if (prompt != null) {
            prompt.setLanguage(this);
        }
        this.prompt = prompt;
    }

    public Language prompt(Prompt prompt) {
        this.setPrompt(prompt);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Language)) {
            return false;
        }
        return getId() != null && getId().equals(((Language) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Language{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", icon='" + getIcon() + "'" +
            ", defaultImage='" + getDefaultImage() + "'" +
            ", defaultImageData='" + getDefaultImageData() + "'" +
            ", defaultImageDataContentType='" + getDefaultImageDataContentType() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
