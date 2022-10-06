package ru.education.sensorrestserver.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.education.sensorrestserver.repo.UserRepository;
import ru.education.sensorrestserver.security.UserAuthDetails;

/**
 * @author Kirill Popov
 */
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = repository.findUserByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        return new UserAuthDetails(user);
    }
}
