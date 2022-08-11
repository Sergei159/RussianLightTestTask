package russianlight.service;

import org.springframework.stereotype.Service;
import russianlight.model.Person;
import russianlight.repository.PersonRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService implements IService {
    private final PersonRepository repository;

    public PersonService(PersonRepository reposiory) {
        this.repository = reposiory;
    }

    public List<Person> findAll() {
        return repository.findAll();
    }

    public void deleteById(int id) {
        repository.deleteById(id);
    }

    public Optional<Person> findById(int id) {
        return repository.findById(id);
    }

    public Person patch(int id, Person person) throws InvocationTargetException, IllegalAccessException {
        return (Person) patch(repository, id, person);
    }

    public Person findByName(String name) {
        return repository.findByName(name);
    }

    public void save(Person person) {
        repository.save(person);
    }
}
