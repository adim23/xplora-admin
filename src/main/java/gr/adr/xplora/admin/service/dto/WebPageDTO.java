package gr.adr.xplora.admin.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link gr.adr.xplora.admin.domain.WebPage} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WebPageDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;

    @NotNull
    private Boolean enabled;

    private String uriPath;

    private LocalDate publishDate;

    private LocalDate createdDate;

    private UserDTO createdBy;

    private Set<TagDTO> tags = new HashSet<>();

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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getUriPath() {
        return uriPath;
    }

    public void setUriPath(String uriPath) {
        this.uriPath = uriPath;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
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

    public Set<TagDTO> getTags() {
        return tags;
    }

    public void setTags(Set<TagDTO> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WebPageDTO)) {
            return false;
        }

        WebPageDTO webPageDTO = (WebPageDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, webPageDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WebPageDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", enabled='" + getEnabled() + "'" +
            ", uriPath='" + getUriPath() + "'" +
            ", publishDate='" + getPublishDate() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", tags=" + getTags() +
            "}";
    }
}
