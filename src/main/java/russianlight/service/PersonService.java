package russianlight.service;

import org.springframework.stereotype.Service;
import russianlight.model.Person;
import russianlight.model.Role;
import russianlight.repository.PersonRepository;
import russianlight.repository.RoleRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService implements IService {
    private final PersonRepository repository;
    private final RoleRepository roleRepository;

    public PersonService(PersonRepository repository, RoleRepository roleRepository) {
        this.repository = repository;
        this.roleRepository = roleRepository;
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

    public void update(Person person) {
        Optional<Person> optPerson = repository.findById(person.getId());
        if (optPerson.isPresent()) {
            if (person.getRole() != null) {
                Optional<Role> optRole = roleRepository.findById(optPerson.get().getRole().getId());
                optRole.ifPresent(person::setRole);
            } else {
                throw new NullPointerException("role must not be null!");
            }
            repository.save(person);
        }
    }

    public void save(Person person) {
            repository.save(person);
    }

    public Person findByEmail(String email) {
        return repository.findByEmail(email);
    }
}
