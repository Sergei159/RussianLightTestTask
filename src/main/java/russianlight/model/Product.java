package russianlight.model;

import russianlight.handlers.Operation;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * сумма продукта указана в копейках
 */
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "productName must not be null", groups = {Operation.OnCreate.class, Operation.OnUpdate.class})
    @Size(min = 2, message = "name must have at least 2 symbols", groups = {
            Operation.OnCreate.class, Operation.OnUpdate.class})
    private String name;

    @NotNull(message = "description must not be null", groups = {Operation.OnCreate.class, Operation.OnUpdate.class})
    @Size(min = 2, message = "description must have at least 2 symbols", groups = {
            Operation.OnCreate.class, Operation.OnUpdate.class})
    private String description;

    @Min(value = 1, message = "price must be greater than 0", groups = {
            Operation.OnCreate.class, Operation.OnUpdate.class})
    private int price;

    private byte[] photo;

    private LocalDateTime created;

    private boolean status;


    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "category_id")
    private Category category;


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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
