package finki.emt.blogger.domain.comment;

import finki.emt.blogger.domain.article.ArticleId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, CommentId> {

    Page<Comment> findByArticleIdOrderByCreatedAtDesc(ArticleId articleId, Pageable pageable);
}
