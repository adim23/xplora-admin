package gr.adr.xplora.admin.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link gr.adr.xplora.admin.domain.Prompt} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PromptDTO implements Serializable {

    private Long id;

    private String key;

    private String value;

    @NotNull
    private LanguageDTO language;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LanguageDTO getLanguage() {
        return language;
    }

    public void setLanguage(LanguageDTO language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PromptDTO)) {
            return false;
        }

        PromptDTO promptDTO = (PromptDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, promptDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PromptDTO{" +
            "id=" + getId() +
            ", key='" + getKey() + "'" +
            ", value='" + getValue() + "'" +
            ", language=" + getLanguage() +
            "}";
    }
}
