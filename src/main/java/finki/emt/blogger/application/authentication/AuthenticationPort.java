package finki.emt.blogger.application.authentication;

import finki.emt.blogger.domain.authentication.AuthDto;
import finki.emt.blogger.domain.user.UserDto;

public interface AuthenticationPort {

    AuthDto authenticate(UserDto user);

    UserDto register(UserDto user);
}
