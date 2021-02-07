package finki.emt.blogger.domain.user;

import finki.emt.blogger.domain.base.DomainObjectId;
import lombok.EqualsAndHashCode;

import javax.persistence.Embeddable;

@EqualsAndHashCode(callSuper = true)
@Embeddable
public class UserId extends DomainObjectId {

    protected UserId() {
        super("");
    }

    public UserId(String id) {
        super(id);
    }
}
