package finki.emt.blogger.domain.article;

import finki.emt.blogger.domain.user.User;
import finki.emt.blogger.domain.user.UserDomainService;
import finki.emt.blogger.domain.user.UserDto;
import org.springframework.stereotype.Service;

@Service
public class ArticleDomainService {

    private final UserDomainService userDomainService;

    public ArticleDomainService(UserDomainService userDomainService) {
        this.userDomainService = userDomainService;
    }

    public SecretArticleDto mapArticleToDto(Article article, User user, Class<?> mapClass) {
        UserDto userDto = null;

        if (user != null) {
            userDto = userDomainService.mapUserToDto(user);

        }

        SecretArticleDto articleDto = new SecretArticleDto(article.id().getId(), article.getTitle(), article.getCreatedAt(), userDto);

        if (mapClass == PublicArticleDto.class)
            articleDto = new PublicArticleDto(article.id().getId(), article.getTitle(), article.getBody(), article.getCreatedAt(), userDto);

        return articleDto;
    }

}
