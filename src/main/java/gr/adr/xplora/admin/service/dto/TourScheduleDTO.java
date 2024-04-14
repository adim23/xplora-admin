package gr.adr.xplora.admin.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link gr.adr.xplora.admin.domain.TourSchedule} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TourScheduleDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;

    @NotNull
    private LocalDate startDatetime;

    private Integer noPassengers;

    private Integer noKids;

    private Integer noPets;

    private LocalDate createdDate;

    private UserDTO createdBy;

    @NotNull
    private TourDTO tour;

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

    public LocalDate getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(LocalDate startDatetime) {
        this.startDatetime = startDatetime;
    }

    public Integer getNoPassengers() {
        return noPassengers;
    }

    public void setNoPassengers(Integer noPassengers) {
        this.noPassengers = noPassengers;
    }

    public Integer getNoKids() {
        return noKids;
    }

    public void setNoKids(Integer noKids) {
        this.noKids = noKids;
    }

    public Integer getNoPets() {
        return noPets;
    }

    public void setNoPets(Integer noPets) {
        this.noPets = noPets;
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

    public TourDTO getTour() {
        return tour;
    }

    public void setTour(TourDTO tour) {
        this.tour = tour;
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
        if (!(o instanceof TourScheduleDTO)) {
            return false;
        }

        TourScheduleDTO tourScheduleDTO = (TourScheduleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tourScheduleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TourScheduleDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", startDatetime='" + getStartDatetime() + "'" +
            ", noPassengers=" + getNoPassengers() +
            ", noKids=" + getNoKids() +
            ", noPets=" + getNoPets() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", tour=" + getTour() +
            ", vehicle=" + getVehicle() +
            ", driver=" + getDriver() +
            "}";
    }
}
