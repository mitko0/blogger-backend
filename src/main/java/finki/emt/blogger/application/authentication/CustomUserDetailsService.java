package finki.emt.blogger.application.authentication;

import finki.emt.blogger.domain.authentication.AuthUserDetails;
import finki.emt.blogger.domain.user.Email;
import finki.emt.blogger.domain.user.User;
import finki.emt.blogger.domain.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findUserByEmail(new Email(email))
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        return new AuthUserDetails(user);
    }
}
