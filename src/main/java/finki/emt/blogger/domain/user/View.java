package finki.emt.blogger.domain.user;

import finki.emt.blogger.domain.article.ArticleId;
import finki.emt.blogger.domain.base.AbstractEntity;
import finki.emt.blogger.domain.base.DomainObjectId;
import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Table(name = "views")
@Entity
public class View extends AbstractEntity<ViewId> {

    @Column(name = "viewed_at")
    @Temporal(TemporalType.TIMESTAMP)
    private final Date viewedAt;

    @Version
    @Temporal(TemporalType.TIMESTAMP)
    private Date version;

    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "article_id"))
    })
    @Embedded
    private ArticleId articleId;

    @ManyToOne
    private User viewer;

    protected View(User viewer, ArticleId articleId) {
        super(DomainObjectId.generateId(ViewId.class));

        this.viewer = viewer;
        this.articleId = articleId;
        this.viewedAt = new Date();
    }

    @Override
    public ViewId id() {
        return this.id;
    }
}
