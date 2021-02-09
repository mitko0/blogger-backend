package finki.emt.blogger.domain.authentication;

import finki.emt.blogger.domain.user.*;
import org.springframework.stereotype.Service;

@Service
public class AuthManager {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public AuthManager(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    public User getCurrentUser(String jwt) {

        Email email = new Email(jwtUtil.extractUsername(jwt));
        return userRepository.findUserByEmail(email).orElseThrow();
    }
}
