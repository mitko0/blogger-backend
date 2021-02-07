package finki.emt.blogger.domain.comment;

import finki.emt.blogger.domain.base.DomainObjectId;
import lombok.EqualsAndHashCode;

import javax.persistence.Embeddable;

@EqualsAndHashCode(callSuper = true)
@Embeddable
public class CommentId extends DomainObjectId {

    protected CommentId() {
        super("");
    }

    public CommentId(String id) {
        super(id);
    }
}
