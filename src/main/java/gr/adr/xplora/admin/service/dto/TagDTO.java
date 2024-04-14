package gr.adr.xplora.admin.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link gr.adr.xplora.admin.domain.Tag} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TagDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;

    private Set<PlaceDTO> places = new HashSet<>();

    private Set<TourDTO> tours = new HashSet<>();

    private Set<TourExtraDTO> tourExtras = new HashSet<>();

    private Set<WebPageDTO> webPages = new HashSet<>();

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

    public Set<PlaceDTO> getPlaces() {
        return places;
    }

    public void setPlaces(Set<PlaceDTO> places) {
        this.places = places;
    }

    public Set<TourDTO> getTours() {
        return tours;
    }

    public void setTours(Set<TourDTO> tours) {
        this.tours = tours;
    }

    public Set<TourExtraDTO> getTourExtras() {
        return tourExtras;
    }

    public void setTourExtras(Set<TourExtraDTO> tourExtras) {
        this.tourExtras = tourExtras;
    }

    public Set<WebPageDTO> getWebPages() {
        return webPages;
    }

    public void setWebPages(Set<WebPageDTO> webPages) {
        this.webPages = webPages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TagDTO)) {
            return false;
        }

        TagDTO tagDTO = (TagDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tagDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TagDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", places=" + getPlaces() +
            ", tours=" + getTours() +
            ", tourExtras=" + getTourExtras() +
            ", webPages=" + getWebPages() +
            "}";
    }
}
