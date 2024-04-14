package gr.adr.xplora.admin.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link gr.adr.xplora.admin.domain.TourStep} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TourStepDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;

    @NotNull
    private Integer stepOrder;

    @NotNull
    private Integer waitTime;

    @NotNull
    private Integer driveTime;

    private LocalDate createdDate;

    private UserDTO createdBy;

    @NotNull
    private TourDTO tour;

    @NotNull
    private PlaceDTO place;

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

    public Integer getStepOrder() {
        return stepOrder;
    }

    public void setStepOrder(Integer stepOrder) {
        this.stepOrder = stepOrder;
    }

    public Integer getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(Integer waitTime) {
        this.waitTime = waitTime;
    }

    public Integer getDriveTime() {
        return driveTime;
    }

    public void setDriveTime(Integer driveTime) {
        this.driveTime = driveTime;
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

    public PlaceDTO getPlace() {
        return place;
    }

    public void setPlace(PlaceDTO place) {
        this.place = place;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TourStepDTO)) {
            return false;
        }

        TourStepDTO tourStepDTO = (TourStepDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tourStepDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TourStepDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", stepOrder=" + getStepOrder() +
            ", waitTime=" + getWaitTime() +
            ", driveTime=" + getDriveTime() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", tour=" + getTour() +
            ", place=" + getPlace() +
            "}";
    }
}
