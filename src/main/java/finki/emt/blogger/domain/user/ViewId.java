package finki.emt.blogger.domain.user;

import finki.emt.blogger.domain.base.DomainObjectId;
import lombok.EqualsAndHashCode;

import javax.persistence.Embeddable;

@EqualsAndHashCode(callSuper = true)
@Embeddable
public class ViewId extends DomainObjectId {

    protected  ViewId() {
        super("");
    }

    public ViewId(String id) {
        super(id);
    }
}
