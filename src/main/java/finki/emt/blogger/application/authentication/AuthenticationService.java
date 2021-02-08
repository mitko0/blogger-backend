package finki.emt.blogger.application.authentication;

import finki.emt.blogger.domain.authentication.AuthDto;
import finki.emt.blogger.domain.authentication.JwtUtil;
import finki.emt.blogger.domain.user.User;
import finki.emt.blogger.domain.user.UserDto;
import finki.emt.blogger.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthenticationService implements AuthenticationPort {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public AuthenticationService(AuthenticationManager authenticationManager,
                                 JwtUtil jwtUtil,
                                 @Qualifier("customUserDetailsService") UserDetailsService userDetailsService, UserRepository userRepository) {

        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    public AuthDto authenticate(UserDto user) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

        return new AuthDto(jwtUtil.generateToken(userDetails));
    }

    @Override
    public UserDto register(UserDto user) {

        User newUser = new User(user);
        userRepository.saveAndFlush(newUser);

        return new UserDto(user.getEmail(), newUser.getPassword().getValue());
    }
}
