package gr.adr.xplora.admin.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link gr.adr.xplora.admin.domain.ImageFile} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ImageFileDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;

    private String title;

    private String alt;

    private String filename;

    @Lob
    private byte[] data;

    private String dataContentType;

    private LocalDate createdDate;

    private UserDTO createdBy;

    private DestinationDTO destination;

    private TourDTO tour;

    private TourCategoryDTO tourCategory;

    private PlaceDTO place;

    private PlaceCategoryDTO placeCategory;

    private TourExtraCategoryDTO tourExtraCategory;

    private TourExtraDTO tourExtra;

    private VehicleDTO vehicle;

    private DriverDTO driver;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getDataContentType() {
        return dataContentType;
    }

    public void setDataContentType(String dataContentType) {
        this.dataContentType = dataContentType;
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

    public DestinationDTO getDestination() {
        return destination;
    }

    public void setDestination(DestinationDTO destination) {
        this.destination = destination;
    }

    public TourDTO getTour() {
        return tour;
    }

    public void setTour(TourDTO tour) {
        this.tour = tour;
    }

    public TourCategoryDTO getTourCategory() {
        return tourCategory;
    }

    public void setTourCategory(TourCategoryDTO tourCategory) {
        this.tourCategory = tourCategory;
    }

    public PlaceDTO getPlace() {
        return place;
    }

    public void setPlace(PlaceDTO place) {
        this.place = place;
    }

    public PlaceCategoryDTO getPlaceCategory() {
        return placeCategory;
    }

    public void setPlaceCategory(PlaceCategoryDTO placeCategory) {
        this.placeCategory = placeCategory;
    }

    public TourExtraCategoryDTO getTourExtraCategory() {
        return tourExtraCategory;
    }

    public void setTourExtraCategory(TourExtraCategoryDTO tourExtraCategory) {
        this.tourExtraCategory = tourExtraCategory;
    }

    public TourExtraDTO getTourExtra() {
        return tourExtra;
    }

    public void setTourExtra(TourExtraDTO tourExtra) {
        this.tourExtra = tourExtra;
    }

    public VehicleDTO getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleDTO vehicle) {
        this.vehicle = vehicle;
    }

    public DriverDTO getDriver() {
        return driver;
    }

    public void setDriver(DriverDTO driver) {
        this.driver = driver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImageFileDTO)) {
            return false;
        }

        ImageFileDTO imageFileDTO = (ImageFileDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, imageFileDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ImageFileDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", title='" + getTitle() + "'" +
            ", alt='" + getAlt() + "'" +
            ", filename='" + getFilename() + "'" +
            ", data='" + getData() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", destination=" + getDestination() +
            ", tour=" + getTour() +
            ", tourCategory=" + getTourCategory() +
            ", place=" + getPlace() +
            ", placeCategory=" + getPlaceCategory() +
            ", tourExtraCategory=" + getTourExtraCategory() +
            ", tourExtra=" + getTourExtra() +
            ", vehicle=" + getVehicle() +
            ", driver=" + getDriver() +
            "}";
    }
}
