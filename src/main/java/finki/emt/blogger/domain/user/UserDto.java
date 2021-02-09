package finki.emt.blogger.domain.user;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    public String id;
    public String email;
    public String password;
    public byte[] profilePicture;
}
