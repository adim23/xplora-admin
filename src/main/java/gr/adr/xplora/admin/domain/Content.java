package gr.adr.xplora.admin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Content.
 */
@Entity
@Table(name = "content")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Content implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "short_description")
    private String shortDescription;

    @Lob
    @Column(name = "data")
    private String data;

    @Lob
    @Column(name = "meta")
    private String meta;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @JsonIgnoreProperties(value = { "createdBy", "content", "tourContent", "prompt" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Language language;

    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "tours", "places", "images", "menus", "contents", "createdBy" }, allowSetters = true)
    private Destination destination;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "defaultTours", "children", "images", "menus", "contents", "createdBy", "parent", "tours" },
        allowSetters = true
    )
    private TourCategory tourCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "tourSteps", "images", "contents", "createdBy", "tags", "categories", "destination" },
        allowSetters = true
    )
    private Place place;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "images", "contents", "createdBy", "places" }, allowSetters = true)
    private PlaceCategory placeCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "images", "contents", "createdBy", "extras" }, allowSetters = true)
    private TourExtraCategory tourExtraCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "images", "contents", "createdBy", "tags", "categories", "tours" }, allowSetters = true)
    private TourExtra tourExtra;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "children", "names", "createdBy", "page", "parent", "tourCategory", "destination" },
        allowSetters = true
    )
    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "menus", "contents", "createdBy", "tags" }, allowSetters = true)
    private WebPage webPage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "names", "places", "tours", "tourExtras", "webPages" }, allowSetters = true)
    private Tag tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "infos", "createdBy", "tour", "place" }, allowSetters = true)
    private TourStep tourStep;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "titles", "tours" }, allowSetters = true)
    private Promotion promotion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "captions",
            "createdBy",
            "destination",
            "tour",
            "tourCategory",
            "place",
            "placeCategory",
            "tourExtraCategory",
            "tourExtra",
            "vehicle",
            "driver",
        },
        allowSetters = true
    )
    private ImageFile imageFile;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Content id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Content code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public Content title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return this.shortDescription;
    }

    public Content shortDescription(String shortDescription) {
        this.setShortDescription(shortDescription);
        return this;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getData() {
        return this.data;
    }

    public Content data(String data) {
        this.setData(data);
        return this;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMeta() {
        return this.meta;
    }

    public Content meta(String meta) {
        this.setMeta(meta);
        return this;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public Content createdDate(LocalDate createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Language getLanguage() {
        return this.language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Content language(Language language) {
        this.setLanguage(language);
        return this;
    }

    public User getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(User user) {
        this.createdBy = user;
    }

    public Content createdBy(User user) {
        this.setCreatedBy(user);
        return this;
    }

    public Destination getDestination() {
        return this.destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public Content destination(Destination destination) {
        this.setDestination(destination);
        return this;
    }

    public TourCategory getTourCategory() {
        return this.tourCategory;
    }

    public void setTourCategory(TourCategory tourCategory) {
        this.tourCategory = tourCategory;
    }

    public Content tourCategory(TourCategory tourCategory) {
        this.setTourCategory(tourCategory);
        return this;
    }

    public Place getPlace() {
        return this.place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public Content place(Place place) {
        this.setPlace(place);
        return this;
    }

    public PlaceCategory getPlaceCategory() {
        return this.placeCategory;
    }

    public void setPlaceCategory(PlaceCategory placeCategory) {
        this.placeCategory = placeCategory;
    }

    public Content placeCategory(PlaceCategory placeCategory) {
        this.setPlaceCategory(placeCategory);
        return this;
    }

    public TourExtraCategory getTourExtraCategory() {
        return this.tourExtraCategory;
    }

    public void setTourExtraCategory(TourExtraCategory tourExtraCategory) {
        this.tourExtraCategory = tourExtraCategory;
    }

    public Content tourExtraCategory(TourExtraCategory tourExtraCategory) {
        this.setTourExtraCategory(tourExtraCategory);
        return this;
    }

    public TourExtra getTourExtra() {
        return this.tourExtra;
    }

    public void setTourExtra(TourExtra tourExtra) {
        this.tourExtra = tourExtra;
    }

    public Content tourExtra(TourExtra tourExtra) {
        this.setTourExtra(tourExtra);
        return this;
    }

    public Menu getMenu() {
        return this.menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Content menu(Menu menu) {
        this.setMenu(menu);
        return this;
    }

    public WebPage getWebPage() {
        return this.webPage;
    }

    public void setWebPage(WebPage webPage) {
        this.webPage = webPage;
    }

    public Content webPage(WebPage webPage) {
        this.setWebPage(webPage);
        return this;
    }

    public Tag getTag() {
        return this.tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public Content tag(Tag tag) {
        this.setTag(tag);
        return this;
    }

    public TourStep getTourStep() {
        return this.tourStep;
    }

    public void setTourStep(TourStep tourStep) {
        this.tourStep = tourStep;
    }

    public Content tourStep(TourStep tourStep) {
        this.setTourStep(tourStep);
        return this;
    }

    public Promotion getPromotion() {
        return this.promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public Content promotion(Promotion promotion) {
        this.setPromotion(promotion);
        return this;
    }

    public ImageFile getImageFile() {
        return this.imageFile;
    }

    public void setImageFile(ImageFile imageFile) {
        this.imageFile = imageFile;
    }

    public Content imageFile(ImageFile imageFile) {
        this.setImageFile(imageFile);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Content)) {
            return false;
        }
        return getId() != null && getId().equals(((Content) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Content{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", title='" + getTitle() + "'" +
            ", shortDescription='" + getShortDescription() + "'" +
            ", data='" + getData() + "'" +
            ", meta='" + getMeta() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
