package gr.adr.xplora.admin.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link gr.adr.xplora.admin.domain.Booking} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BookingDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate bookDatetime;

    private Integer noPersons;

    private Integer noKids;

    private Integer noPets;

    private Double total;

    private String paymentType;

    private Boolean valid;

    private LocalDate cancelledAt;

    @Lob
    private String remoteData;

    private String remoteId;

    private LocalDate createdDate;

    private TourScheduleDTO schedule;

    private PassengerDTO passenger;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getBookDatetime() {
        return bookDatetime;
    }

    public void setBookDatetime(LocalDate bookDatetime) {
        this.bookDatetime = bookDatetime;
    }

    public Integer getNoPersons() {
        return noPersons;
    }

    public void setNoPersons(Integer noPersons) {
        this.noPersons = noPersons;
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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public LocalDate getCancelledAt() {
        return cancelledAt;
    }

    public void setCancelledAt(LocalDate cancelledAt) {
        this.cancelledAt = cancelledAt;
    }

    public String getRemoteData() {
        return remoteData;
    }

    public void setRemoteData(String remoteData) {
        this.remoteData = remoteData;
    }

    public String getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(String remoteId) {
        this.remoteId = remoteId;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public TourScheduleDTO getSchedule() {
        return schedule;
    }

    public void setSchedule(TourScheduleDTO schedule) {
        this.schedule = schedule;
    }

    public PassengerDTO getPassenger() {
        return passenger;
    }

    public void setPassenger(PassengerDTO passenger) {
        this.passenger = passenger;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BookingDTO)) {
            return false;
        }

        BookingDTO bookingDTO = (BookingDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bookingDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BookingDTO{" +
            "id=" + getId() +
            ", bookDatetime='" + getBookDatetime() + "'" +
            ", noPersons=" + getNoPersons() +
            ", noKids=" + getNoKids() +
            ", noPets=" + getNoPets() +
            ", total=" + getTotal() +
            ", paymentType='" + getPaymentType() + "'" +
            ", valid='" + getValid() + "'" +
            ", cancelledAt='" + getCancelledAt() + "'" +
            ", remoteData='" + getRemoteData() + "'" +
            ", remoteId='" + getRemoteId() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", schedule=" + getSchedule() +
            ", passenger=" + getPassenger() +
            "}";
    }
}
