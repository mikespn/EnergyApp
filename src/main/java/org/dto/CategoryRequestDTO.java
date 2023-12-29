package org.dto;

import org.model.Category;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

public class CategoryRequestDTO {

    private Long id;

    @NotBlank(message = "Category name must not be blank")
    @Size(max = 100, message = "Category name must be less than 100 chars.")
    private String name;

    @Size(max = 500, message = "Description must be less than 500 chars")
    private String description;
    public CategoryRequestDTO(){}

    public CategoryRequestDTO(Long id, String name, String description){
        this.id = id;
        this.name = name;
        this.description = description;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryRequestDTO)) return false;
        CategoryRequestDTO that = (CategoryRequestDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    @Override
    public String toString() {
        return "CategoryRequestDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
