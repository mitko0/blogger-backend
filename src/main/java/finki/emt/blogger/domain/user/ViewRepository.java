package finki.emt.blogger.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ViewRepository extends JpaRepository<View, ViewId> {

    List<View> findByViewerAndViewedAtBetween(User viewer, Date startDate, Date endDate);
}
