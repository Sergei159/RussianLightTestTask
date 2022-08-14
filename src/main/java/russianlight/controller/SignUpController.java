package russianlight.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import russianlight.handlers.Operation;
import russianlight.model.Person;
import russianlight.model.Role;
import russianlight.service.PersonService;
import russianlight.service.RoleService;

import javax.validation.Valid;
import java.util.Optional;

import static russianlight.security.JWTAuthenticationFilter.SIGN_UP_URL;

@RestController
@Validated
public class SignUpController {
    private final PersonService personService;
    private final BCryptPasswordEncoder encoder;
    private final RoleService roleService;

    public SignUpController(PersonService personService, BCryptPasswordEncoder encoder, RoleService roleService) {
        this.personService = personService;
        this.encoder = encoder;
        this.roleService = roleService;
    }


    @PostMapping(SIGN_UP_URL)
    @ApiOperation("sign-up user. role_id == 1 is USER, role_id == 2 is ADMIN")
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
            if ("ADMIN".equalsIgnoreCase(person.getRole().getName())) {
                role = roleService.findByName("ADMIN").orElse(new Role());
            } else {
                role = roleService.findByName("USER").orElse(new Role());
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
}
