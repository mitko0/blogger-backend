package finki.emt.blogger.domain.base;

import javax.persistence.EmbeddedId;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractEntity<ID extends DomainObjectId> implements IdentifiableDomainObject<ID> {

    @EmbeddedId
    protected final ID id;

    /*protected AbstractEntity() {
        this(id);
    }*/

    public AbstractEntity(ID id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return id == null
                ? super.hashCode()
                : id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;

        var other = (AbstractEntity<?>) obj;
        return id != null && id.equals(other.id);
    }

    @Override
    public String toString() {
        return String.format("%s[%s]", this.getClass().getSimpleName(), id);
    }
}
