package finki.emt.blogger.domain.comment;

import finki.emt.blogger.domain.article.ArticleId;
import finki.emt.blogger.domain.base.AbstractEntity;
import finki.emt.blogger.domain.base.DomainObjectId;
import finki.emt.blogger.domain.user.UserId;
import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Table(name = "comments")
@Entity
public class Comment extends AbstractEntity<CommentId> {

    private String content;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Version
    @Temporal(TemporalType.TIMESTAMP)
    private Date version;

    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "creator_id"))
    })
    @Embedded
    private UserId creatorId;

    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "article_id"))
    })
    @Embedded
    private ArticleId articleId;

    protected Comment() {
        super(DomainObjectId.generateId(CommentId.class));
    }

    public Comment(String content, UserId creatorId, ArticleId articleId) {
        super(DomainObjectId.generateId(CommentId.class));

        this.content = content;
        this.creatorId = creatorId;
        this.articleId = articleId;
        this.createdAt = new Date();
    }

    @Override
    public CommentId id() {
        return this.id;
    }
}
