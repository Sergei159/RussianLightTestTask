package russianlight.repository;

import russianlight.model.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonRepository
        extends CrudRepository<Person, Integer> {

    Person findByName(String name);

    Person save(Person person);

    List<Person> findAll();

    Person findByEmail(String email);

}
