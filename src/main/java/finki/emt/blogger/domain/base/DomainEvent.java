package finki.emt.blogger.domain.base;

import java.util.Date;

public interface DomainEvent extends DomainObject {

    Date occurredOn();
}
