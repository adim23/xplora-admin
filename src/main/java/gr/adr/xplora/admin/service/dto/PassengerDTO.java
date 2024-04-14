package gr.adr.xplora.admin.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link gr.adr.xplora.admin.domain.Passenger} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PassengerDTO implements Serializable {

    private Long id;

    private String name;

    private String email;

    private String mobile;

    private Integer age;

    private String gender;

    private String nationality;

    private Instant createdDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PassengerDTO)) {
            return false;
        }

        PassengerDTO passengerDTO = (PassengerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, passengerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PassengerDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", age=" + getAge() +
            ", gender='" + getGender() + "'" +
            ", nationality='" + getNationality() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
