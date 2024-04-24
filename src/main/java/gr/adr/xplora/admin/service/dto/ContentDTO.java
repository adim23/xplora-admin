package gr.adr.xplora.admin.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link gr.adr.xplora.admin.domain.Content} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ContentDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;

    private String title;

    @Lob
    private String shortDescription;

    @Lob
    private String data;

    @Lob
    private String meta;

    private LocalDate createdDate;

    @NotNull
    private LanguageDTO language;

    private UserDTO createdBy;

    private DestinationDTO destination;

    private TourCategoryDTO tourCategory;

    private PlaceDTO place;

    private PlaceCategoryDTO placeCategory;

    private TourExtraCategoryDTO tourExtraCategory;

    private TourExtraDTO tourExtra;

    private MenuDTO menu;

    private WebPageDTO webPage;

    private TagDTO tag;

    private TourStepDTO tourStep;

    private PromotionDTO promotion;

    private ImageFileDTO imageFile;

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

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LanguageDTO getLanguage() {
        return language;
    }

    public void setLanguage(LanguageDTO language) {
        this.language = language;
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

    public MenuDTO getMenu() {
        return menu;
    }

    public void setMenu(MenuDTO menu) {
        this.menu = menu;
    }

    public WebPageDTO getWebPage() {
        return webPage;
    }

    public void setWebPage(WebPageDTO webPage) {
        this.webPage = webPage;
    }

    public TagDTO getTag() {
        return tag;
    }

    public void setTag(TagDTO tag) {
        this.tag = tag;
    }

    public TourStepDTO getTourStep() {
        return tourStep;
    }

    public void setTourStep(TourStepDTO tourStep) {
        this.tourStep = tourStep;
    }

    public PromotionDTO getPromotion() {
        return promotion;
    }

    public void setPromotion(PromotionDTO promotion) {
        this.promotion = promotion;
    }

    public ImageFileDTO getImageFile() {
        return imageFile;
    }

    public void setImageFile(ImageFileDTO imageFile) {
        this.imageFile = imageFile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContentDTO)) {
            return false;
        }

        ContentDTO contentDTO = (ContentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, contentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContentDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", title='" + getTitle() + "'" +
            ", shortDescription='" + getShortDescription() + "'" +
            ", data='" + getData() + "'" +
            ", meta='" + getMeta() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", language=" + getLanguage() +
            ", createdBy=" + getCreatedBy() +
            ", destination=" + getDestination() +
            ", tourCategory=" + getTourCategory() +
            ", place=" + getPlace() +
            ", placeCategory=" + getPlaceCategory() +
            ", tourExtraCategory=" + getTourExtraCategory() +
            ", tourExtra=" + getTourExtra() +
            ", menu=" + getMenu() +
            ", webPage=" + getWebPage() +
            ", tag=" + getTag() +
            ", tourStep=" + getTourStep() +
            ", promotion=" + getPromotion() +
            ", imageFile=" + getImageFile() +
            "}";
    }
}
