package russianlight.model;

import russianlight.handlers.Operation;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "persons")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "name must not be null", groups = {Operation.OnCreate.class, Operation.OnUpdate.class})
    @NotBlank(message = "name must contain symbols", groups = {
            Operation.OnCreate.class, Operation.OnUpdate.class})
    @Size(min = 2, message = "name must have at least 2 symbols", groups = {
            Operation.OnCreate.class, Operation.OnUpdate.class})
    private String name;

    @NotNull(message = "email must not be null", groups = {Operation.OnCreate.class, Operation.OnUpdate.class})
    @NotBlank(message = "email must contain symbols", groups = {
            Operation.OnCreate.class, Operation.OnUpdate.class})
    @Email(groups = {Operation.OnCreate.class, Operation.OnUpdate.class})
    private String email;

    @NotNull(message = "password must not be null", groups = {Operation.OnCreate.class, Operation.OnUpdate.class})
    @NotBlank(message = "password must contain symbols", groups = {
            Operation.OnCreate.class, Operation.OnUpdate.class})
    private String password;

    @NotNull(message = "role must not be null", groups = {Operation.OnCreate.class, Operation.OnUpdate.class})
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "role_id")
    private Role role;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Person person = (Person) o;
        return id == person.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
