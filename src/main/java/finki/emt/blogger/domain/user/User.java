package finki.emt.blogger.domain.user;

import finki.emt.blogger.domain.base.AbstractEntity;
import finki.emt.blogger.domain.base.DomainObjectId;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Table(name = "users")
@Entity
public class User extends AbstractEntity<UserId> {

    @Embedded
    private Email email;

    @Embedded
    private Password password;

    @Embedded
    private Image profilePicture;

    @Enumerated(EnumType.STRING)
    private Subscription subscription = Subscription.silver;

    @Version
    @Temporal(TemporalType.TIMESTAMP)
    private Date version;

    @OneToMany(mappedBy = "viewer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<View> views = new ArrayList<>();

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

    public void addView(View view) {
        this.views.add(view);
    }

    public void changePassword(String newPassword) {
        this.password = new Password(newPassword);
    }

    public void updateSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public void updateProfilePicture(MultipartFile file) {
        this.profilePicture = new Image(file);
    }
}
