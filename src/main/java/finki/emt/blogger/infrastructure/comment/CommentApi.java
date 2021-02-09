package finki.emt.blogger.infrastructure.comment;

import finki.emt.blogger.application.comment.CommentPort;
import finki.emt.blogger.domain.comment.CommentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/comments")
@RestController
public class CommentApi {

    private final CommentPort commentPort;

    public CommentApi(CommentPort commentPort) {
        this.commentPort = commentPort;
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<?> index(@PathVariable String articleId,
                                   @RequestHeader(required = false, defaultValue = "0") int page,
                                   @RequestHeader(required = false, defaultValue = "10") int size) {

        return ResponseEntity.ok(commentPort.listCommentsForArticle(articleId, page, size));
    }

    @PostMapping
    public ResponseEntity<?> store(@RequestBody CommentDto commentDto,
                                   @RequestHeader("Authorization") String authorizationHeader) {

        String jwt = authorizationHeader.substring(7);
        return ResponseEntity.ok(commentPort.storeComment(commentDto, jwt));
    }

    @DeleteMapping("/{commentId}")
    public void delete(@PathVariable String commentId) {

        commentPort.deleteComment(commentId);
    }
}
