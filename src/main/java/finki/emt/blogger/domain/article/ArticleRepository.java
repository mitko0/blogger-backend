package finki.emt.blogger.domain.article;

import finki.emt.blogger.domain.user.UserId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface ArticleRepository extends JpaRepository<Article, ArticleId> {

    Page<Article> findByCreatedAtBeforeOrderByCreatedAtDesc(Date createdAt, Pageable pageable);

    Page<Article> findByCreatorIdOrderByCreatedAtDesc(UserId creatorId, Pageable pageable);
}
