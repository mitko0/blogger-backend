package finki.emt.blogger.domain.article;

import finki.emt.blogger.domain.base.DomainObjectId;
import lombok.EqualsAndHashCode;

import javax.persistence.Embeddable;

@EqualsAndHashCode(callSuper = true)
@Embeddable
public class ArticleId extends DomainObjectId {

    protected ArticleId() {
        super("");
    }

    public ArticleId(String id) {
        super(id);
    }
}
