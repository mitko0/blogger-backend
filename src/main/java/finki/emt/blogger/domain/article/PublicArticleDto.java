package finki.emt.blogger.domain.article;

import finki.emt.blogger.domain.user.UserDto;

import java.util.Date;

public class PublicArticleDto extends SecretArticleDto {
    public String body;

    public PublicArticleDto() {
    }


    public PublicArticleDto(String id, String title, String body, Date createdAt, UserDto userDto) {
        super(id, title, createdAt, userDto);

        this.body = body;
    }
}
