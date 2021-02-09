package finki.emt.blogger.application.authentication;

import finki.emt.blogger.domain.authentication.AuthDto;
import finki.emt.blogger.domain.user.UserDto;

public interface AuthenticationPort {

    AuthDto authenticate(UserDto userDto);

    UserDto register(UserDto userDto);
}
