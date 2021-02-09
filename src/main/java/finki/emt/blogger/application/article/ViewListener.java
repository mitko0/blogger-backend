package finki.emt.blogger.application.article;

import finki.emt.blogger.domain.article.Article;
import finki.emt.blogger.domain.article.ArticleRepository;
import finki.emt.blogger.domain.user.User;
import finki.emt.blogger.domain.user.UserRepository;
import finki.emt.blogger.domain.user.ViewEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ViewListener {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    public ViewListener(UserRepository userRepository, ArticleRepository articleRepository) {
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
    }

    @Async
    @EventListener
    public void onDomainEvent(ViewEvent viewEvent) {
        User viewer = userRepository.findById(viewEvent.getViewerId())
                .orElseThrow(() -> new RuntimeException("User not found!"));

        Article article = articleRepository.findById(viewEvent.getArticleId())
                .orElseThrow(() -> new RuntimeException("Article not found!"));

        article.updateViewCount(viewer);
        articleRepository.saveAndFlush(article);
    }
}
