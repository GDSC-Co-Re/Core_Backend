package moamoa.core.domain.wasteDisposalHistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WasteDisposalHistoryRepository extends JpaRepository<WasteDisposalHistory, Long> {
    @Query("SELECT SUM(wdh.asepticCartonQuantity) + SUM(wdh.paperCartonQuantity) " +
            "FROM WasteDisposalHistory wdh WHERE wdh.user.id = :userId")
    Long calculateUserTotalEmissions(@Param("userId") Long userId);
}
