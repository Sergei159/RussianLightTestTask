package russianlight.model;


import russianlight.handlers.Operation;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "categoryName must not be null", groups = {Operation.OnCreate.class, Operation.OnUpdate.class})
    @Size(min = 2, message = "categoryName must have at least 2 symbols", groups = {
            Operation.OnCreate.class, Operation.OnUpdate.class})
    private String name;

    @NotNull(message = "description must not be null", groups = {Operation.OnCreate.class, Operation.OnUpdate.class})
    @Size(min = 2, message = "description must have at least 2 symbols", groups = {
            Operation.OnCreate.class, Operation.OnUpdate.class})
    private String description;


    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Category category = (Category) o;
        return id == category.id && Objects.equals(name, category.name) && Objects.equals(description, category.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }
}
