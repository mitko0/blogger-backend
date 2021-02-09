package finki.emt.blogger.domain.user;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDomainService {

    private final ViewRepository viewRepository;

    public UserDomainService(ViewRepository viewRepository) {
        this.viewRepository = viewRepository;
    }

    public UserDto mapUserToDto(User user) {

        byte[] profilePic = user.getProfilePicture() != null
                ? user.getProfilePicture().getContent()
                : null;

        return new UserDto(user.id().getId(), user.getEmail().getValue(), user.getPassword().getValue(), profilePic);
    }

    public boolean isUserAllowed(User viewer) {
        DateTime midnight = new DateTime(DateTimeZone.UTC).withTimeAtStartOfDay();

        List<View> allViews = viewRepository.findByViewerAndViewedAtBetween(viewer, midnight.toDate(), midnight.plusDays(1).toDate());

        long views = allViews.stream().filter(view -> view.getViewer().id() != viewer.id()).count();

        return viewer.getSubscription().amount >= views;
    }
}
