package russianlight.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import russianlight.handlers.Operation;
import russianlight.model.Person;
import russianlight.model.Role;
import russianlight.service.PersonService;
import russianlight.service.RoleService;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

import static russianlight.security.JWTAuthenticationFilter.SIGN_UP_URL;

@RestController
@RequestMapping("/")
@Validated
public class PersonController {
    private final PersonService personService;
    private final BCryptPasswordEncoder encoder;
    private final RoleService roleService;

    public PersonController(PersonService personService, BCryptPasswordEncoder encoder, RoleService roleService) {
        this.personService = personService;
        this.encoder = encoder;
        this.roleService = roleService;
    }

    /**
     * role_id == 1 is USER
     * role_id == 2 is ADMIN
     * @param person
     */
    @PostMapping(SIGN_UP_URL)
    @Validated(Operation.OnCreate.class)
    public ResponseEntity<Person> signUp(@Valid @RequestBody Person person) {
        Optional<Person> result = Optional.ofNullable(person);
        if (result.isPresent()) {
            String email = result.get().getEmail();
            if (personService.findByEmail(email) != null) {
                throw new IllegalArgumentException("user with this email is already exists");
            }
            person.setPassword(encoder.encode(person.getPassword()));
            Role role;
            if (person.getRole().getId() == 2) {
                role = roleService.findByName("admin").orElse(new Role());
            } else {
                role = roleService.findByName("user").orElse(new Role());
            }
            person.setRole(role);
            personService.save(person);
            return new ResponseEntity<>(
                    result.get(),
                    HttpStatus.CREATED
            );
        } else {
            return new ResponseEntity<>(
                    new Person(),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    /**
     * get List of users
     * @return
     */

    @GetMapping("users/")
    public List<Person> findAll() {
        return personService.findAll();
    }

    /**
     * get user by id
     */
    @GetMapping("users/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        Optional<Person> person = personService.findById(id);
        return new ResponseEntity<>(
                person.orElse(new Person()),
                person.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }


    /**
     * update user data
     */
    @PutMapping("users/{id}")
    @Validated(Operation.OnUpdate.class)
    public ResponseEntity<Void> update(@PathVariable int id, @Valid @RequestBody Person person) {
        Optional<Person> result = Optional.ofNullable(person);
        if (result.isPresent()) {
            person.setId(id);
            personService.update(person);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * delete user
     */
    @DeleteMapping("users/{id}")
    @Validated(Operation.OnDelete.class)
    public ResponseEntity<Void> delete(@PathVariable int id) {
        personService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * partially update user data
     */
    @PatchMapping("users/patch/{id}")
    public Person patch(@PathVariable int id,@RequestBody Person person) throws InvocationTargetException, IllegalAccessException {
        return personService.patch(id, person);
    }


}
