package moamoa.core.domain.wasteDisposalHistory;

import moamoa.core.domain.wasteDisposalHistory.dto.WasteDisposalHistoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    Page<WasteDisposalHistory> findByUserId(Long userId, Pageable pageable);

    @Query(value = "SELECT YEAR(waste_disposal_history_disposal_time) AS year, WEEK(waste_disposal_history_disposal_time) AS week, SUM(waste_disposal_history_aseptic_quantity+waste_disposal_history_paper_quantity) AS sum " +
            "FROM waste_disposal_history_tb " +
            "WHERE user_id = :userId " +
            "GROUP BY year, week " +
            "ORDER BY year DESC, week DESC " +
            "LIMIT 4", nativeQuery = true)
    List<Object[]> get4WeeksUserEmission(@Param("userId") Long userId);

    @Query(value = "SELECT YEAR(waste_disposal_history_disposal_time) AS year, MONTH(waste_disposal_history_disposal_time) AS month, SUM(waste_disposal_history_aseptic_quantity+waste_disposal_history_paper_quantity) AS sum " +
            "FROM waste_disposal_history_tb " +
            "WHERE user_id = :userId " +
            "GROUP BY year, month " +
            "ORDER BY year DESC, month DESC " +
            "LIMIT 4", nativeQuery = true)
    List<Object[]> get4MonthsUserEmission(@Param("userId") Long userId);

    @Query(value = "SELECT YEAR(waste_disposal_history_disposal_time) AS year, SUM(waste_disposal_history_aseptic_quantity+waste_disposal_history_paper_quantity) AS sum " +
            "FROM waste_disposal_history_tb " +
            "WHERE user_id = :userId " +
            "GROUP BY year " +
            "ORDER BY year DESC " +
            "LIMIT 4", nativeQuery = true)
    List<Object[]> get4YearsUserEmission(@Param("userId") Long userId);

    @Query(value = "SELECT YEAR(waste_disposal_history_disposal_time) AS year, WEEK(waste_disposal_history_disposal_time) AS week, SUM(waste_disposal_history_aseptic_quantity+waste_disposal_history_paper_quantity) AS sum " +
            "FROM waste_disposal_history_tb " +
            "WHERE community_id = :communityId " +
            "GROUP BY year, week " +
            "ORDER BY year DESC, week DESC " +
            "LIMIT 4", nativeQuery = true)
    List<Object[]> get4WeeksCommunityEmission(@Param("communityId") Long communityId);

    @Query(value = "SELECT YEAR(waste_disposal_history_disposal_time) AS year, MONTH(waste_disposal_history_disposal_time) AS month, SUM(waste_disposal_history_aseptic_quantity+waste_disposal_history_paper_quantity) AS sum " +
            "FROM waste_disposal_history_tb " +
            "WHERE community_id = :communityId " +
            "GROUP BY year, month " +
            "ORDER BY year DESC, month DESC " +
            "LIMIT 4", nativeQuery = true)
    List<Object[]> get4MonthsCommunityEmission(@Param("communityId") Long communityId);

    @Query(value = "SELECT YEAR(waste_disposal_history_disposal_time) AS year, SUM(waste_disposal_history_aseptic_quantity+waste_disposal_history_paper_quantity) AS sum " +
            "FROM waste_disposal_history_tb " +
            "WHERE community_id = :communityId " +
            "GROUP BY year " +
            "ORDER BY year DESC " +
            "LIMIT 4", nativeQuery = true)
    List<Object[]> get4YearsCommunityEmission(@Param("communityId") Long communityId);

    @Query("SELECT SUM(wdh.asepticCartonQuantity + wdh.paperCartonQuantity) " +
            "FROM WasteDisposalHistory wdh " +
            "WHERE wdh.user.id = :userId " +
            "AND WEEK(wdh.disposalTime) = WEEK(CURRENT_DATE()) " +
            "AND YEAR(wdh.disposalTime) = YEAR(CURRENT_DATE())")
    Long calculateUserWeeklyTotalEmissions(@Param("userId") Long userId);

    @Query("SELECT SUM(wdh.asepticCartonQuantity) " +
            "FROM WasteDisposalHistory wdh " +
            "WHERE wdh.user.id = :userId " +
            "AND WEEK(wdh.disposalTime) = WEEK(CURRENT_DATE()) " +
            "AND YEAR(wdh.disposalTime) = YEAR(CURRENT_DATE())")
    Long calculateUserWeeklyAsepticCartonEmissions(@Param("userId") Long userId);

    @Query("SELECT SUM(wdh.paperCartonQuantity) " +
            "FROM WasteDisposalHistory wdh " +
            "WHERE wdh.user.id = :userId " +
            "AND WEEK(wdh.disposalTime) = WEEK(CURRENT_DATE()) " +
            "AND YEAR(wdh.disposalTime) = YEAR(CURRENT_DATE())")
    Long calculateUserWeeklyPaperCartonEmissions(@Param("userId") Long userId);

    @Query("SELECT SUM(wdh.asepticCartonQuantity + wdh.paperCartonQuantity) " +
            "FROM WasteDisposalHistory wdh " +
            "WHERE wdh.user.id = :userId " +
            "AND MONTH(wdh.disposalTime) = MONTH(CURRENT_DATE()) " +
            "AND YEAR(wdh.disposalTime) = YEAR(CURRENT_DATE())")
    Long calculateUserMonthlyTotalEmissions(@Param("userId") Long userId);

    @Query("SELECT SUM(wdh.asepticCartonQuantity) " +
            "FROM WasteDisposalHistory wdh " +
            "WHERE wdh.user.id = :userId " +
            "AND MONTH(wdh.disposalTime) = MONTH(CURRENT_DATE()) " +
            "AND YEAR(wdh.disposalTime) = YEAR(CURRENT_DATE())")
    Long calculateUserMonthlyAsepticCartonEmissions(@Param("userId") Long userId);

    @Query("SELECT SUM(wdh.paperCartonQuantity) " +
            "FROM WasteDisposalHistory wdh " +
            "WHERE wdh.user.id = :userId " +
            "AND MONTH(wdh.disposalTime) = MONTH(CURRENT_DATE()) " +
            "AND YEAR(wdh.disposalTime) = YEAR(CURRENT_DATE())")
    Long calculateUserMonthlyPaperCartonEmissions(@Param("userId") Long userId);

    @Query("SELECT SUM(wdh.asepticCartonQuantity + wdh.paperCartonQuantity) " +
            "FROM WasteDisposalHistory wdh " +
            "WHERE wdh.user.id = :userId " +
            "AND YEAR(wdh.disposalTime) = YEAR(CURRENT_DATE())")
    Long calculateUserYearlyTotalEmissions(@Param("userId") Long userId);

    @Query("SELECT SUM(wdh.asepticCartonQuantity) " +
            "FROM WasteDisposalHistory wdh " +
            "WHERE wdh.user.id = :userId " +
            "AND YEAR(wdh.disposalTime) = YEAR(CURRENT_DATE())")
    Long calculateUserYearlyAsepticCartonEmissions(@Param("userId") Long userId);

    @Query("SELECT SUM(wdh.paperCartonQuantity) " +
            "FROM WasteDisposalHistory wdh " +
            "WHERE wdh.user.id = :userId " +
            "AND YEAR(wdh.disposalTime) = YEAR(CURRENT_DATE())")
    Long calculateUserYearlyPaperCartonEmissions(@Param("userId") Long userId);
}
