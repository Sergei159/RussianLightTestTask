package russianlight.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import russianlight.handlers.Operation;
import russianlight.model.Person;
import russianlight.service.PersonService;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/users")
@Validated
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/")
    @ApiOperation("get List of users")
    public List<Person> findAll() {
        return personService.findAll();
    }


    @GetMapping("/{id}")
    @ApiOperation("get user by id")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        Optional<Person> person = personService.findById(id);
        return new ResponseEntity<>(
                person.orElse(new Person()),
                person.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }


    @PutMapping("/{id}")
    @ApiOperation("update user data")
    @Validated(Operation.OnUpdate.class)
    public ResponseEntity<Void> update(@PathVariable int id, @Valid @RequestBody Person person) {
            return personService.update(person, id)
                    ? ResponseEntity.ok().build()
                    : ResponseEntity.notFound().build();
        }


    @DeleteMapping("/{id}")
    @ApiOperation("delete user")
    @Validated(Operation.OnDelete.class)
    public ResponseEntity<Void> delete(@PathVariable int id) {
        personService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("patch/{id}")
    @ApiOperation("partially update user data")
    public Person patch(@PathVariable int id,@RequestBody Person person) throws InvocationTargetException, IllegalAccessException {
        return personService.patch(id, person);
    }


}
