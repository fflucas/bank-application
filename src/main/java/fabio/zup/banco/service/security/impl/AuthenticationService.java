package fabio.zup.banco.service.security.impl;

import fabio.zup.banco.model.UserApi;
import fabio.zup.banco.service.UserApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService implements UserDetailsService {

    private final UserApiService userApiService;

    @Autowired
    public AuthenticationService(UserApiService userApiService) {
        this.userApiService = userApiService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserApi> user = userApiService.findByEmail(email);
        if (user.isPresent())
            return  user.get();
        throw new UsernameNotFoundException("Incorrect username or password");
    }
}
