package finki.emt.blogger.domain.user;

import finki.emt.blogger.domain.base.ValueObject;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.regex.Pattern;

@Getter
@EqualsAndHashCode
@Embeddable
public class Password implements ValueObject {

    @Column(name = "password")
    private String value;

    protected Password() {
        this.value = null;
    }

    public Password(String value) {
        setPassword(value);
    }

    private void setPassword(String value) {
        if (!Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!.?@#$%^&-+=()]).{8,20}$", value)) {
            throw new IllegalArgumentException("Invalid password");
        }

        this.value = new BCryptPasswordEncoder().encode(value);
    }
}
