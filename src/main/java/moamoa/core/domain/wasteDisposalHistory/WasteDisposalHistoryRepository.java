package moamoa.core.domain.wasteDisposalHistory;

import moamoa.core.domain.wasteDisposalHistory.dto.WasteDisposalHistoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WasteDisposalHistoryRepository extends JpaRepository<WasteDisposalHistory, Long> {
    @Query("SELECT SUM(wdh.asepticCartonQuantity) + SUM(wdh.paperCartonQuantity) " +
            "FROM WasteDisposalHistory wdh WHERE wdh.user.id = :userId")
    Long calculateUserTotalEmissions(@Param("userId") Long userId);

//    // 페이징 처리를 위한 쿼리 추가
//    @Query("SELECT wdh FROM WasteDisposalHistory wdh WHERE wdh.community.id = :communityId ORDER BY wdh.disposalTime DESC")
//    Page<WasteDisposalHistory> findCommunityWasteDisposalHistory(@Param("communityId") Long communityId, Pageable pageable);
//
//    @Query("SELECT new moamoa.core.domain.wasteDisposalHistory.dto.WasteDisposalHistoryDto(" +
//            "u.name, wdh.disposalTime, wdh.asepticCartonQuantity, wdh.paperCartonQuantity, wdh.likes, wdh.paperOrAseptic) " +
//            "FROM WasteDisposalHistory wdh " +
//            "JOIN wdh.user u " +
//            "WHERE wdh.community.id = :communityId " +
//            "ORDER BY wdh.disposalTime DESC")
//    Page<WasteDisposalHistoryDto> findCommunityWasteDisposalHistory(@Param("communityId") Long communityId, Pageable pageable);
    Page<WasteDisposalHistory> findByCommunityId(Long communityId, Pageable pageable);

}
