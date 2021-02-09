package finki.emt.blogger.infrastructure.authentication;

import finki.emt.blogger.application.authentication.AuthenticationPort;
import finki.emt.blogger.domain.user.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth")
@RestController
public class AuthApi {

    private final AuthenticationPort authenticationPort;

    public AuthApi(AuthenticationPort authenticationPort) {
        this.authenticationPort = authenticationPort;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(authenticationPort.authenticate(userDto));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(authenticationPort.register(userDto));
    }
}
