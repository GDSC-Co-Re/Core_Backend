package moamoa.core.domain.community;

import moamoa.core.domain.community.dto.CommunityLocalRankingDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommunityRepository extends JpaRepository<Community, Long> {

    @Query("SELECT COUNT(u.id) FROM User u WHERE u.community.id = :communityId")
    Long getMemberCount(@Param("communityId") Long communityId);

    // 커뮤니티 총 배출량 계산
    // 일반팩과 멸균팩을 합쳐서 계산한다.
    @Query("SELECT SUM(wdh.asepticCartonQuantity) + SUM(wdh.paperCartonQuantity) " +
            "FROM WasteDisposalHistory wdh " +
            "WHERE wdh.community.id = :communityId")
    Long calculateTotalEmissions(@Param("communityId") Long communityId);

    @Query("SELECT SUM(wdh.paperCartonQuantity) " +
            "FROM WasteDisposalHistory wdh " +
            "WHERE wdh.community.id = :communityId")
    Long calculateTotalPaperEmissions(@Param("communityId") Long communityId);

    @Query("SELECT SUM(wdh.asepticCartonQuantity)" +
            "FROM WasteDisposalHistory wdh " +
            "WHERE wdh.community.id = :communityId")
    Long calculateTotalAsepticEmissions(@Param("communityId") Long communityId);

    @Query(value = "SELECT R.ranking FROM (" +
            "SELECT user_id, " +
            "RANK() OVER (ORDER BY SUM(waste_disposal_history_aseptic_quantity) + SUM(waste_disposal_history_paper_quantity) DESC) AS ranking " +
            "FROM waste_disposal_history_tb " +
            "WHERE community_id = :communityId " +
            "GROUP BY user_id) R " +
            "WHERE R.user_id = :userId", nativeQuery = true)
    int findUserRanking(@Param("userId") Long userId, @Param("communityId") Long communityId);


    @Query("SELECT SUM(wdh.asepticCartonQuantity + wdh.paperCartonQuantity) " +
            "FROM WasteDisposalHistory wdh " +
            "WHERE wdh.community.id = :communityId " +
            "AND WEEK(wdh.disposalTime) = WEEK(CURRENT_DATE()) " +
            "AND YEAR(wdh.disposalTime) = YEAR(CURRENT_DATE())")
    Long calculateWeeklyTotalEmissions(@Param("communityId") Long communityId);

    @Query("SELECT SUM(wdh.asepticCartonQuantity) " +
            "FROM WasteDisposalHistory wdh " +
            "WHERE wdh.community.id = :communityId " +
            "AND WEEK(wdh.disposalTime) = WEEK(CURRENT_DATE()) " +
            "AND YEAR(wdh.disposalTime) = YEAR(CURRENT_DATE())")
    Long calculateWeeklyAsepticCartonEmissions(@Param("communityId") Long communityId);

    @Query("SELECT SUM(wdh.paperCartonQuantity) " +
            "FROM WasteDisposalHistory wdh " +
            "WHERE wdh.community.id = :communityId " +
            "AND WEEK(wdh.disposalTime) = WEEK(CURRENT_DATE()) " +
            "AND YEAR(wdh.disposalTime) = YEAR(CURRENT_DATE())")
    Long calculateWeeklyPaperCartonEmissions(@Param("communityId") Long communityId);



    @Query("SELECT SUM(wdh.asepticCartonQuantity + wdh.paperCartonQuantity) " +
            "FROM WasteDisposalHistory wdh " +
            "WHERE wdh.community.id = :communityId " +
            "AND MONTH(wdh.disposalTime) = MONTH(CURRENT_DATE()) " +
            "AND YEAR(wdh.disposalTime) = YEAR(CURRENT_DATE())")
    Long calculateMonthlyTotalEmissions(@Param("communityId") Long communityId);

    @Query("SELECT SUM(wdh.asepticCartonQuantity) " +
            "FROM WasteDisposalHistory wdh " +
            "WHERE wdh.community.id = :communityId " +
            "AND MONTH(wdh.disposalTime) = MONTH(CURRENT_DATE()) " +
            "AND YEAR(wdh.disposalTime) = YEAR(CURRENT_DATE())")
    Long calculateMonthlyAsepticCartonEmissions(@Param("communityId") Long communityId);

    @Query("SELECT SUM(wdh.paperCartonQuantity) " +
            "FROM WasteDisposalHistory wdh " +
            "WHERE wdh.community.id = :communityId " +
            "AND MONTH(wdh.disposalTime) = MONTH(CURRENT_DATE()) " +
            "AND YEAR(wdh.disposalTime) = YEAR(CURRENT_DATE())")
    Long calculateMonthlyPaperCartonEmissions(@Param("communityId") Long communityId);

    @Query("SELECT SUM(wdh.asepticCartonQuantity + wdh.paperCartonQuantity) " +
            "FROM WasteDisposalHistory wdh " +
            "WHERE wdh.community.id = :communityId " +
            "AND YEAR(wdh.disposalTime) = YEAR(CURRENT_DATE())")
    Long calculateYearlyTotalEmissions(@Param("communityId") Long communityId);

    @Query("SELECT SUM(wdh.asepticCartonQuantity) " +
            "FROM WasteDisposalHistory wdh " +
            "WHERE wdh.community.id = :communityId " +
            "AND YEAR(wdh.disposalTime) = YEAR(CURRENT_DATE())")
    Long calculateYearlyAsepticCartonEmissions(@Param("communityId") Long communityId);

    @Query("SELECT SUM(wdh.paperCartonQuantity) " +
            "FROM WasteDisposalHistory wdh " +
            "WHERE wdh.community.id = :communityId " +
            "AND YEAR(wdh.disposalTime) = YEAR(CURRENT_DATE())")
    Long calculateYearlyPaperCartonEmissions(@Param("communityId") Long communityId);

//    @Query(value = "SELECT R.ranking FROM (" +
//            "SELECT user_id, " +
//            "RANK() OVER (ORDER BY SUM(waste_disposal_history_aseptic_quantity) + SUM(waste_disposal_history_paper_quantity) DESC) AS ranking " +
//            "FROM waste_disposal_history_tb " +
//            "WHERE community_id = :communityId " +
//            "GROUP BY user_id) R " +
//            "WHERE R.user_id = :userId", nativeQuery = true)

    @Query(value = "SELECT ranking FROM (" +
            "SELECT c.community_id, " +
            "SUM(wdh.waste_disposal_history_aseptic_quantity) + SUM(wdh.waste_disposal_history_paper_quantity) AS total_emissions, " +
            "RANK() OVER (ORDER BY SUM(wdh.waste_disposal_history_aseptic_quantity) + SUM(wdh.waste_disposal_history_paper_quantity) DESC) AS ranking " +
            "FROM community_tb c " +
            "JOIN waste_disposal_history_tb wdh ON c.community_id = wdh.community_id " +
            "WHERE c.depth2 = :depth2 " +
            "GROUP BY c.community_id) AS community_rankings " +
            "WHERE community_id = :communityId", nativeQuery = true)
    int findDepth2CommunityRanking(@Param("communityId") Long communityId, @Param("depth2") String depth2);

    @Query(value = "SELECT " +
            "c.community_name AS communityName, " +
            "c.country AS country, " +
            "c.depth1 AS depth1, " +
            "c.depth2 AS depth2, " +
            "c.depth3 AS depth3, " +
            "c.zipcode AS zipcode, " +
            "ranked.ranking AS communityRanking " +
            "FROM ( " +
            "SELECT " +
            "c.community_id, " +
            "SUM(wdh.waste_disposal_history_aseptic_quantity) + SUM(wdh.waste_disposal_history_paper_quantity) AS total_emissions, " +
            "RANK() OVER (ORDER BY SUM(wdh.waste_disposal_history_aseptic_quantity) + SUM(wdh.waste_disposal_history_paper_quantity) DESC) AS ranking " +
            "FROM community_tb c " +
            "JOIN waste_disposal_history_tb wdh ON c.community_id = wdh.community_id " +
            "WHERE c.depth2 = :depth2 " +
            "GROUP BY c.community_id " +
            ") AS ranked " +
            "JOIN community_tb c ON ranked.community_id = c.community_id " +
            "WHERE ranked.ranking <= 3 " +
            "ORDER BY ranked.ranking ASC", nativeQuery = true)
    List<Object[]> findTop3CommunitiesInDepth2(@Param("depth2") String depth2);
}
