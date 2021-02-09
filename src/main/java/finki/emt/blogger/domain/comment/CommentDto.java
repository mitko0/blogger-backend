package finki.emt.blogger.domain.comment;

import finki.emt.blogger.domain.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    public String id;
    public String articleId;
    public String content;
    public Date createdAt;
    public UserDto creator;
}
