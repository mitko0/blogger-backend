package finki.emt.blogger.domain.article;

import finki.emt.blogger.domain.base.AbstractEntity;
import finki.emt.blogger.domain.base.DomainObjectId;
import finki.emt.blogger.domain.user.User;
import finki.emt.blogger.domain.user.UserId;
import lombok.Getter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

@Getter
@Table(name = "articles")
@Where(clause = "active = true")
@Entity
public class Article extends AbstractEntity<ArticleId> {

    private String title;

    private String body;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    private boolean active = true;

    @Column(name = "total_views")
    private long totalViews = 0;

    @Version
    @Temporal(TemporalType.TIMESTAMP)
    private Date version;

    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "creator_id"))
    })
    @Embedded
    private UserId creatorId;

    protected Article() {
        super(DomainObjectId.generateId(ArticleId.class));
    }

    public Article(String title, String body, UserId creatorId) {
        super(DomainObjectId.generateId(ArticleId.class));

        this.title = title;
        this.body = body;
        this.creatorId = creatorId;
        this.createdAt = new Date();
    }

    @Override
    public ArticleId id() {
        return this.id;
    }

    public void updateViewCount(User viewer) {
        if (!this.getCreatorId().getId().equals(viewer.id().getId())) {
            this.totalViews++;
        }
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateBody(String body) {
        this.body = body;
    }

    public void deleteArticle() {
        this.active = false;
    }
}
