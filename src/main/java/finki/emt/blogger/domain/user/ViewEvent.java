package finki.emt.blogger.domain.user;

import finki.emt.blogger.domain.article.ArticleId;
import finki.emt.blogger.domain.base.DomainEvent;
import lombok.Getter;

import javax.persistence.Transient;
import java.util.Date;

@Getter
public class ViewEvent implements DomainEvent {

    private final ArticleId articleId;
    private final UserId viewerId;

    @Transient
    private final Date occurredOn;

    public ViewEvent(ArticleId articleId, UserId viewerId) {
        this.articleId = articleId;
        this.viewerId = viewerId;
        this.occurredOn = new Date();
    }

    @Override
    public Date occurredOn() {
        return this.occurredOn;
    }
}
