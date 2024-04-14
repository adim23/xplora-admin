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
 * A ImageFile.
 */
@Entity
@Table(name = "image_file")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ImageFile implements Serializable {

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

    @Column(name = "alt")
    private String alt;

    @Column(name = "filename")
    private String filename;

    @Lob
    @Column(name = "data")
    private byte[] data;

    @Column(name = "data_content_type")
    private String dataContentType;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "imageFile")
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
    private Set<Content> captions = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "tours", "places", "images", "contents", "createdBy" }, allowSetters = true)
    private Destination destination;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "steps",
            "images",
            "extraInfos",
            "contents",
            "createdBy",
            "meetingPoint",
            "finishPoint",
            "tourExtras",
            "tags",
            "promotions",
            "categories",
            "destination",
        },
        allowSetters = true
    )
    private Tour tour;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "children", "images", "menus", "contents", "createdBy", "parent", "tours" }, allowSetters = true)
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
    @JsonIgnoreProperties(value = { "images" }, allowSetters = true)
    private Vehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "images" }, allowSetters = true)
    private Driver driver;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ImageFile id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public ImageFile code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public ImageFile title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlt() {
        return this.alt;
    }

    public ImageFile alt(String alt) {
        this.setAlt(alt);
        return this;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getFilename() {
        return this.filename;
    }

    public ImageFile filename(String filename) {
        this.setFilename(filename);
        return this;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public byte[] getData() {
        return this.data;
    }

    public ImageFile data(byte[] data) {
        this.setData(data);
        return this;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getDataContentType() {
        return this.dataContentType;
    }

    public ImageFile dataContentType(String dataContentType) {
        this.dataContentType = dataContentType;
        return this;
    }

    public void setDataContentType(String dataContentType) {
        this.dataContentType = dataContentType;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public ImageFile createdDate(LocalDate createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Set<Content> getCaptions() {
        return this.captions;
    }

    public void setCaptions(Set<Content> contents) {
        if (this.captions != null) {
            this.captions.forEach(i -> i.setImageFile(null));
        }
        if (contents != null) {
            contents.forEach(i -> i.setImageFile(this));
        }
        this.captions = contents;
    }

    public ImageFile captions(Set<Content> contents) {
        this.setCaptions(contents);
        return this;
    }

    public ImageFile addCaptions(Content content) {
        this.captions.add(content);
        content.setImageFile(this);
        return this;
    }

    public ImageFile removeCaptions(Content content) {
        this.captions.remove(content);
        content.setImageFile(null);
        return this;
    }

    public User getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(User user) {
        this.createdBy = user;
    }

    public ImageFile createdBy(User user) {
        this.setCreatedBy(user);
        return this;
    }

    public Destination getDestination() {
        return this.destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public ImageFile destination(Destination destination) {
        this.setDestination(destination);
        return this;
    }

    public Tour getTour() {
        return this.tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public ImageFile tour(Tour tour) {
        this.setTour(tour);
        return this;
    }

    public TourCategory getTourCategory() {
        return this.tourCategory;
    }

    public void setTourCategory(TourCategory tourCategory) {
        this.tourCategory = tourCategory;
    }

    public ImageFile tourCategory(TourCategory tourCategory) {
        this.setTourCategory(tourCategory);
        return this;
    }

    public Place getPlace() {
        return this.place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public ImageFile place(Place place) {
        this.setPlace(place);
        return this;
    }

    public PlaceCategory getPlaceCategory() {
        return this.placeCategory;
    }

    public void setPlaceCategory(PlaceCategory placeCategory) {
        this.placeCategory = placeCategory;
    }

    public ImageFile placeCategory(PlaceCategory placeCategory) {
        this.setPlaceCategory(placeCategory);
        return this;
    }

    public Vehicle getVehicle() {
        return this.vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public ImageFile vehicle(Vehicle vehicle) {
        this.setVehicle(vehicle);
        return this;
    }

    public Driver getDriver() {
        return this.driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public ImageFile driver(Driver driver) {
        this.setDriver(driver);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImageFile)) {
            return false;
        }
        return getId() != null && getId().equals(((ImageFile) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ImageFile{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", title='" + getTitle() + "'" +
            ", alt='" + getAlt() + "'" +
            ", filename='" + getFilename() + "'" +
            ", data='" + getData() + "'" +
            ", dataContentType='" + getDataContentType() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
