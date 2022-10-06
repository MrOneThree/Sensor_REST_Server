package ru.education.sensorrestserver.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import ru.education.sensorrestserver.services.UserService;

import java.util.Collections;

/**
 * @author Kirill Popov
 */
@Component
@RequiredArgsConstructor
public class AuthProviderImpl implements AuthenticationProvider {

    private final UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var userDetails = userService.loadUserByUsername(authentication.getName());
        var password = authentication.getCredentials();

        if (password.equals(userDetails.getPassword())) {
            return new UsernamePasswordAuthenticationToken(userDetails, password, Collections.emptyList());
        } else {
            throw new BadCredentialsException("Incorrect Password");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
