package finki.emt.blogger.infrastructure.article;

import finki.emt.blogger.application.article.ArticlePort;
import finki.emt.blogger.domain.article.PublicArticleDto;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/articles")
@RestController
public class ArticleApi {

    private final ArticlePort articlePort;

    public ArticleApi(ArticlePort articlePort) {
        this.articlePort = articlePort;
    }

    @GetMapping
    public ResponseEntity<?> index(@RequestParam(required = false)
                                   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS", iso = DateTimeFormat.ISO.DATE_TIME)
                                           DateTime beforeDate,
                                   @RequestHeader(required = false, defaultValue = "0") int page,
                                   @RequestHeader(required = false, defaultValue = "10") int size) {

        return ResponseEntity.ok(articlePort.listArticles(beforeDate, page, size));
    }

    @GetMapping("/current-user")
    public ResponseEntity<?> forUser(@RequestHeader(required = false, defaultValue = "0") int page,
                                     @RequestHeader(required = false, defaultValue = "10") int size,
                                     @RequestHeader("Authorization") String authorizationHeader) {

        String jwt = authorizationHeader.substring(7);
        return ResponseEntity.ok(articlePort.listArticlesForCurrentUser(jwt, page, size));
    }

    @PostMapping("/create")
    public ResponseEntity<?> store(@RequestBody PublicArticleDto articleDto,
                                   @RequestHeader("Authorization") String authorizationHeader) {

        String jwt = authorizationHeader.substring(7);
        return ResponseEntity.ok(articlePort.storeArticle(articleDto, jwt));
    }

    @PatchMapping
    public ResponseEntity<?> update(@RequestBody PublicArticleDto articleDto) {

        return ResponseEntity.ok(articlePort.updateArticle(articleDto));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {

        articlePort.deleteArticle(id);
    }
}
