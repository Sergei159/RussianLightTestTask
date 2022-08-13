package russianlight.service;

import org.springframework.stereotype.Service;
import russianlight.model.Role;
import russianlight.repository.RoleRepository;

import java.util.Optional;

@Service
public class RoleService implements IService {
    private final RoleRepository repository;

    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    public Optional<Role> findByName(String user) {
        return repository.findByName(user);

    }
}
