package finki.emt.blogger.application.article;

import finki.emt.blogger.domain.article.*;
import finki.emt.blogger.domain.authentication.AuthManager;
import finki.emt.blogger.domain.user.User;
import finki.emt.blogger.domain.user.UserId;
import finki.emt.blogger.domain.user.UserRepository;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class ArticleService implements ArticlePort {

    private final ArticleRepository articleRepository;
    private final AuthManager authManager;
    private final UserRepository userRepository;
    private final ArticleDomainService articleDomainService;

    public ArticleService(ArticleRepository articleRepository, AuthManager authManager, UserRepository userRepository, ArticleDomainService articleDomainService) {
        this.articleRepository = articleRepository;
        this.authManager = authManager;
        this.userRepository = userRepository;
        this.articleDomainService = articleDomainService;
    }

    @Override
    public Page<SecretArticleDto> listArticles(DateTime beforeDate, int page, int size) {

        beforeDate = new DateTime(beforeDate, DateTimeZone.UTC);

        Page<Article> articles = articleRepository.findByCreatedAtBeforeOrderByCreatedAtDesc(beforeDate.toDate(), PageRequest.of(page, size));

        UserId[] creatorIds = articles.stream().map(Article::getCreatorId).toArray(UserId[]::new);
        List<User> creators = userRepository.findAllById(Arrays.asList(creatorIds));

        SecretArticleDto[] result = articles.stream()
                .map(article -> {
                    User creator = creators.stream()
                            .filter(user -> user.id().equals(article.getCreatorId()))
                            .findAny()
                            .orElseThrow();

                    return articleDomainService.mapArticleToDto(article, creator, SecretArticleDto.class);
                }).toArray(SecretArticleDto[]::new);

        return new PageImpl<>(Arrays.asList(result));
    }

    @Override
    public Page<PublicArticleDto> listArticlesForCurrentUser(String jwt, int page, int size) {

        User currentUser = authManager.getCurrentUser(jwt);

        Page<Article> articles = articleRepository.findByCreatorIdOrderByCreatedAtDesc(currentUser.id(), PageRequest.of(page, size));

        PublicArticleDto[] result = articles.stream()
                .map(article -> (PublicArticleDto) articleDomainService.mapArticleToDto(article, null, PublicArticleDto.class))
                .toArray(PublicArticleDto[]::new);

        return new PageImpl<>(Arrays.asList(result));
    }

    @Override
    public PublicArticleDto storeArticle(PublicArticleDto articleDto, String jwt) {

        User creator = authManager.getCurrentUser(jwt);

        Article article = new Article(articleDto.title, articleDto.body, creator.id());
        articleRepository.saveAndFlush(article);

        return (PublicArticleDto) articleDomainService.mapArticleToDto(article, creator, PublicArticleDto.class);
    }

    @Override
    public PublicArticleDto showArticle(String articleId) {
        Article article = articleRepository.findById(new ArticleId(articleId))
                .orElseThrow(() -> new RuntimeException("Article not found!"));

        User creator = userRepository.findById(article.getCreatorId())
                .orElseThrow(() -> new RuntimeException("User not found!"));

        return (PublicArticleDto) articleDomainService.mapArticleToDto(article, creator, PublicArticleDto.class);
    }

    @Override
    public PublicArticleDto updateArticle(PublicArticleDto articleDto) {
        Article article = articleRepository.findById(new ArticleId(articleDto.id))
                .orElseThrow(() -> new RuntimeException("Article not found"));

        article.updateTitle(articleDto.title);
        article.updateBody(articleDto.body);

        articleRepository.saveAndFlush(article);

        User creator = userRepository.findById(article.getCreatorId())
                .orElseThrow(() -> new RuntimeException("User not found!"));

        return (PublicArticleDto) articleDomainService.mapArticleToDto(article, creator, PublicArticleDto.class);
    }

    @Override
    public void deleteArticle(String articleId) {
        Article article = articleRepository.findById(new ArticleId(articleId))
                .orElseThrow(() -> new RuntimeException("Article not found!"));

        article.deleteArticle();
        articleRepository.saveAndFlush(article);
    }
}
