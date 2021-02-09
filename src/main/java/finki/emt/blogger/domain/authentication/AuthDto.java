package finki.emt.blogger.domain.authentication;

import finki.emt.blogger.domain.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class AuthDto {
    public String jwt;
    public UserDto user;
}
