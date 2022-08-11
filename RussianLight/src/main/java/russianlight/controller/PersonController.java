package russianlight.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import russianlight.model.Person;
import russianlight.model.Role;
import russianlight.service.PersonService;
import russianlight.service.RoleService;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
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
    @PostMapping("/sign-up")
    public void signUp(@Valid @RequestBody Person person) {
        person.setPassword(encoder.encode(person.getPassword()));
        Role role;
        if (person.getRole().getId() == 2) {
            role = roleService.findByName("admin").orElse(new Role());
        } else {
            role = roleService.findByName("user").orElse(new Role());
        }
        person.setRole(role);
        personService.save(person);
    }

    /**
     * get List of users
     * @return
     */

    @GetMapping("/")
    public List<Person> findAll() {
        return personService.findAll();
    }


    /**
     * update user data
     */
    @PutMapping("/")
    public ResponseEntity<Void> update(@Valid @RequestBody Person person) {
        Optional<Person> result = Optional.ofNullable(person);
        if (result.isPresent()) {
            if (person.getName() == null) {
                throw new NullPointerException("Name cannot be empty");
            }
            if (person.getEmail() == null) {
                throw new NullPointerException("Email cannot be empty");
            }
            personService.save(person);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * delete user
     */
    @DeleteMapping("/")
    public ResponseEntity<Void> delete(@Valid @PathVariable int id) {
        personService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * partially update user data
     */
    @PatchMapping("/person/patch/{id}")
    public Person patch(@Valid @PathVariable int id, @Valid  @RequestBody Person person) throws InvocationTargetException, IllegalAccessException {
        return personService.patch(id, person);
    }

}
