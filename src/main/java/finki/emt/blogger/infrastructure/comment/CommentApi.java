package finki.emt.blogger.infrastructure.comment;

import finki.emt.blogger.application.comment.CommentPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/comments")
@RestController
public class CommentApi {

    private final CommentPort commentPort;

    public CommentApi(CommentPort commentPort) {
        this.commentPort = commentPort;
    }

    @GetMapping
    public ResponseEntity<?> index(@RequestParam String articleId,
                                   @RequestHeader(required = false, defaultValue = "0") int page,
                                   @RequestHeader(required = false, defaultValue = "10") int size) {

        return ResponseEntity.ok(commentPort.listCommentsForArticle(articleId, page, size));
    }
}
