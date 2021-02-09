package finki.emt.blogger.application.user;

import finki.emt.blogger.domain.article.*;
import finki.emt.blogger.domain.authentication.AuthManager;
import finki.emt.blogger.domain.user.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class UserService implements UserPort {

    private final AuthManager authManager;
    private final UserRepository userRepository;
    private final UserDomainService userDomainService;
    private final ArticleRepository articleRepository;
    private final ArticleDomainService articleDomainService;
    private final ApplicationEventPublisher eventPublisher;

    public UserService(AuthManager authManager, UserRepository userRepository, UserDomainService userDomainService, ArticleRepository articleRepository, ArticleDomainService articleDomainService, ApplicationEventPublisher eventPublisher) {
        this.authManager = authManager;
        this.userRepository = userRepository;
        this.userDomainService = userDomainService;
        this.articleRepository = articleRepository;
        this.articleDomainService = articleDomainService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public UserDto changePassword(String password, String jwt) {

        User user = authManager.getCurrentUser(jwt);
        user.changePassword(password);
        userRepository.saveAndFlush(user);

        return userDomainService.mapUserToDto(user);
    }

    @Override
    public UserDto updateSubscription(Subscription subscription, String jwt) {

        User user = authManager.getCurrentUser(jwt);
        user.updateSubscription(subscription);
        userRepository.saveAndFlush(user);

        return userDomainService.mapUserToDto(user);
    }

    @Override
    public UserDto updateProfilePicture(MultipartFile file, String jwt) {

        User user = authManager.getCurrentUser(jwt);
        user.updateProfilePicture(file);
        userRepository.saveAndFlush(user);

        return userDomainService.mapUserToDto(user);
    }

    @Override
    public PublicArticleDto viewArticle(String articleId, String jwt) {
        User currentUser = authManager.getCurrentUser(jwt);

        if (!userDomainService.isUserAllowed(currentUser)) {
            throw new RuntimeException("Update subscription!");
        }

        Article article = articleRepository.findById(new ArticleId(articleId))
                .orElseThrow(() -> new RuntimeException("Article not found!"));

        currentUser.addView(new View(currentUser, article.id()));
        userRepository.saveAndFlush(currentUser);

        eventPublisher.publishEvent(new ViewEvent(article.id(), currentUser.id()));

        return (PublicArticleDto) articleDomainService.mapArticleToDto(article, currentUser, PublicArticleDto.class);
    }
}
