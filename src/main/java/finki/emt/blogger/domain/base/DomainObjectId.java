package finki.emt.blogger.domain.base;

import lombok.EqualsAndHashCode;
import org.springframework.lang.NonNull;

import javax.persistence.MappedSuperclass;
import java.util.Objects;
import java.util.UUID;

@EqualsAndHashCode
@MappedSuperclass
public abstract class DomainObjectId implements ValueObject {

    protected final String id;

    public DomainObjectId(String id) {
        this.id = id;
    }

    @NonNull
    public static <ID extends DomainObjectId> ID generateId(@NonNull Class<ID> idClass) {
        Objects.requireNonNull(idClass, "idClass cannot be null");

        try {
            return idClass.getConstructor(String.class).newInstance(UUID.randomUUID().toString());
        } catch (Exception e) {
            throw new RuntimeException("Could not create new instance of " + idClass, e);
        }
    }
}
