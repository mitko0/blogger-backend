package finki.emt.blogger.application.authentication;

import finki.emt.blogger.domain.authentication.AuthDto;
import finki.emt.blogger.domain.authentication.AuthUserDetails;
import finki.emt.blogger.domain.authentication.JwtUtil;
import finki.emt.blogger.domain.user.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthenticationService implements AuthenticationPort {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final UserDomainService userDomainService;

    public AuthenticationService(AuthenticationManager authenticationManager,
                                 JwtUtil jwtUtil,
                                 UserRepository userRepository,
                                 UserDomainService userDomainService) {

        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.userDomainService = userDomainService;
    }

    @Override
    public AuthDto authenticate(UserDto userDto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDto.email, userDto.password)
        );

        User user = userRepository.findUserByEmail(new Email(userDto.email))
                .orElseThrow(() -> new RuntimeException("User not found!"));

        UserDetails userDetails = new AuthUserDetails(user);

        String jwt = jwtUtil.generateToken(userDetails);
        userDto = userDomainService.mapUserToDto(user);

        return new AuthDto(jwt, userDto);
    }

    @Override
    public UserDto register(UserDto userDto) {
        Email email = new Email(userDto.email);
        Password password = new Password(userDto.password);

        User user = new User(email, password);
        userRepository.saveAndFlush(user);

        return userDomainService.mapUserToDto(user);
    }
}
