package finki.emt.blogger.domain.user;

import org.springframework.stereotype.Service;

@Service
public class UserDomainService {

    public UserDto mapUserToDto(User user) {

        byte[] profilePic = user.getProfilePicture() != null
                ? user.getProfilePicture().getContent()
                : null;

        return new UserDto(user.id().getId(), user.getEmail().getValue(), user.getPassword().getValue(), profilePic);
    }
}
