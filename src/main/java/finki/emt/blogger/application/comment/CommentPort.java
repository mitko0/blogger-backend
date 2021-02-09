package finki.emt.blogger.application.comment;

import finki.emt.blogger.domain.comment.CommentDto;
import org.springframework.data.domain.Page;

public interface CommentPort {

    Page<CommentDto> listCommentsForArticle(String articleId, int page, int size);

    CommentDto storeComment(CommentDto commentDto, String jwt);

    void deleteComment(String commentId);
}
