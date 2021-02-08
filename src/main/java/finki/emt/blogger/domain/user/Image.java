package finki.emt.blogger.domain.user;

import finki.emt.blogger.domain.base.ValueObject;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Embeddable;
import javax.persistence.Lob;
import java.io.IOException;

@Getter
@EqualsAndHashCode
@Embeddable
public class Image implements ValueObject {

    @Lob
    private byte[] content;

    protected Image() {
    }

    public Image(MultipartFile file) {
        setContent(file);
    }

    private void setContent(MultipartFile file) {
        if (file == null || !file.getContentType().contains("image/")) {
            throw new IllegalArgumentException("Invalid file type!");
        }

        try {
            this.content = file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
