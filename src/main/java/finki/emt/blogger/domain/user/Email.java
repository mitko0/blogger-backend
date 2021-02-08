package finki.emt.blogger.domain.user;

import finki.emt.blogger.domain.base.ValueObject;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.regex.Pattern;

@Getter
@EqualsAndHashCode
@Embeddable
public class Email implements ValueObject {

    @Column(name = "email", unique = true)
    private String value;

    protected Email() {
        this.value = null;
    }

    public Email(String value) {
        setValue(value);
    }

    private void setValue(String value) {
        if (!Pattern.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", value)) {
            throw new IllegalArgumentException("Provided value is not an email!");
        }

        this.value = value;
    }
}
