package finki.emt.blogger.domain.base;

import java.io.Serializable;

public interface IdentifiableDomainObject<ID extends Serializable> extends DomainObject {

    ID id();
}
