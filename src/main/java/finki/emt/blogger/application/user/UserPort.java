package finki.emt.blogger.application.user;

import finki.emt.blogger.domain.article.PublicArticleDto;
import finki.emt.blogger.domain.user.Subscription;
import finki.emt.blogger.domain.user.UserDto;
import org.springframework.web.multipart.MultipartFile;

public interface UserPort {

    UserDto changePassword(String password, String jwt);

    UserDto updateSubscription(Subscription subscription, String jwt);

    UserDto updateProfilePicture(MultipartFile file, String jwt);

    PublicArticleDto viewArticle(String articleId, String jwt);
}
