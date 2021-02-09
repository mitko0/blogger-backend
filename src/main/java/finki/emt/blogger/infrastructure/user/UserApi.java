package finki.emt.blogger.infrastructure.user;

import finki.emt.blogger.application.user.UserPort;
import finki.emt.blogger.domain.user.Subscription;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/users")
@RestController
public class UserApi {

    private final UserPort userPort;

    public UserApi(UserPort userPort) {
        this.userPort = userPort;
    }

    @GetMapping("/view/{articleId}")
    public ResponseEntity<?> index(@PathVariable String articleId,
                                   @RequestHeader("Authorization") String authorizationHeader) {

        String jwt = authorizationHeader.substring(7);
        return ResponseEntity.ok(userPort.viewArticle(articleId, jwt));
    }

    @PatchMapping("/update/password")
    public ResponseEntity<?> password(@RequestParam String password,
                                      @RequestHeader("Authorization") String authorizationHeader) {

        String jwt = authorizationHeader.substring(7);
        return ResponseEntity.ok(userPort.changePassword(password, jwt));
    }

    @PatchMapping("/update/subscription")
    public ResponseEntity<?> subscription(@RequestParam Subscription subscription,
                                          @RequestHeader("Authorization") String authorizationHeader) {

        String jwt = authorizationHeader.substring(7);
        return ResponseEntity.ok(userPort.updateSubscription(subscription, jwt));
    }

    @PatchMapping("/update/profile-picture")
    public ResponseEntity<?> profilePicture(@RequestParam MultipartFile picture,
                                            @RequestHeader("Authorization") String authorizationHeader) {

        String jwt = authorizationHeader.substring(7);
        return ResponseEntity.ok(userPort.updateProfilePicture(picture, jwt));
    }
}
