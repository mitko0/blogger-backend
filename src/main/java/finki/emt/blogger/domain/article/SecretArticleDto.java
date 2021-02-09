package finki.emt.blogger.domain.article;

import finki.emt.blogger.domain.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
public class SecretArticleDto {
    public String id;
    public String title;
    public Date createdAt;
    public UserDto creator;
}
