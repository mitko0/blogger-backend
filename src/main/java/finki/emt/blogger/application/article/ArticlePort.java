package finki.emt.blogger.application.article;

import finki.emt.blogger.domain.article.PublicArticleDto;
import finki.emt.blogger.domain.article.SecretArticleDto;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;

public interface ArticlePort {

    Page<SecretArticleDto> listArticles(DateTime beforeDate, int page, int size);

    Page<PublicArticleDto> listArticlesForCurrentUser(String jwt, int page, int size);

    PublicArticleDto storeArticle(PublicArticleDto articleDto, String jtw);

    PublicArticleDto showArticle(String articleId);

    PublicArticleDto updateArticle(PublicArticleDto articleDto);

    void deleteArticle(String articleId);

}
