package finki.emt.blogger.domain.comment;

import finki.emt.blogger.domain.user.User;
import finki.emt.blogger.domain.user.UserDomainService;
import finki.emt.blogger.domain.user.UserDto;
import org.springframework.stereotype.Service;

@Service
public class CommentDomainService {

    private final UserDomainService userDomainService;

    public CommentDomainService(UserDomainService userDomainService) {
        this.userDomainService = userDomainService;
    }

    public CommentDto mapCommentToDto(Comment comment, User user) {
        UserDto userDto = null;

        if (user != null) {
            userDto = userDomainService.mapUserToDto(user);
        }

        return new CommentDto(comment.id().getId(), comment.getArticleId().getId(), comment.getContent(), comment.getCreatedAt(), userDto);
    }
}
