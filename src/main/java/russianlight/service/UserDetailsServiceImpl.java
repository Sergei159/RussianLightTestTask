package russianlight.service;

import russianlight.model.Person;
import russianlight.repository.PersonRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * service for sign-up users with Spring Security
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final PersonRepository repository;

    public UserDetailsServiceImpl(PersonRepository repository, List<GrantedAuthority> authorities) {
        this.repository = repository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Person user = repository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getName().trim()));
        return new User(user.getEmail(), user.getPassword(), authorities);
    }

}