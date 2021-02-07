package finki.emt.blogger.domain.user;

import finki.emt.blogger.domain.base.AbstractEntity;
import finki.emt.blogger.domain.base.DomainObjectId;
import lombok.Getter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Table(name = "users")
@Entity
public class User extends AbstractEntity<UserId> {

    private String username;

    @Embedded
    private Email email;

    @Embedded
    private Password password;

    @Enumerated(EnumType.STRING)
    private Subscription subscription = Subscription.silver;

    @Version
    @Temporal(TemporalType.TIMESTAMP)
    private Date version;

    @OneToMany(mappedBy = "viewer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<View> views;

    protected User() {
        super(DomainObjectId.generateId(UserId.class));
    }

    public User(Email email, Password password) {
        super(DomainObjectId.generateId(UserId.class));

        this.email = email;
        this.password = password;
        this.subscription = Subscription.silver;
    }

    @Override
    public UserId id() {
        return this.id;
    }

    public Password changePassword(String newPassword) {
        this.password = new Password(newPassword);

        return this.password;
    }

    public Subscription updateSubscription(Subscription subscription) {
        this.subscription = subscription;

        return this.subscription;
    }
}
