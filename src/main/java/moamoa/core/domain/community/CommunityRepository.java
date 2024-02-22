package moamoa.core.domain.community;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommunityRepository extends JpaRepository<Community, Long> {
    // 커뮤니티 총 배출량 계산
    // 일반팩과 멸균팩을 합쳐서 계산한다.
    @Query("SELECT SUM(wdh.asepticCartonQuantity) + SUM(wdh.paperCartonQuantity) " +
            "FROM WasteDisposalHistory wdh " +
            "WHERE wdh.community.id = :communityId")
    Long calculateTotalEmissions(@Param("communityId") Long communityId);

    // 커뮤니티 내 사용자의 랭킹 계산
    // 일반팩과 멸균팩을 합쳐서 계산한다.
    // JPQL에서는 RANK가 지원되지 않는다고 한다. 따라서 네이티브 쿼리로 구현한다.
//    @Query(value = "SELECT rank FROM (" +
//            "SELECT user_id, (SUM(waste_disposal_history_aseptic_quantity) + SUM(waste_disposal_history_paper_quantity)) AS totalEmissions, " +
//            "RANK() OVER (ORDER BY (SUM(waste_disposal_history_aseptic_quantity) + SUM(waste_disposal_history_paper_quantity)) DESC) AS rank " +
//            "FROM waste_disposal_history_tb WHERE community_id = :communityId " +
//            "GROUP BY user_id) AS rankings WHERE user_id = :userId",
//            nativeQuery = true)
    //select (rank() over(order by sum(waste_disposal_history_aseptic_quantity)+sum(waste_disposal_history_paper_quantity) desc))
    //from waste_disposal_history_tb
    //where community_id = 1
    //group by user_id;
    @Query(value = "SELECT R.ranking FROM (" +
            "SELECT user_id, " +
            "RANK() OVER (ORDER BY SUM(waste_disposal_history_aseptic_quantity) + SUM(waste_disposal_history_paper_quantity) DESC) AS ranking " +
            "FROM waste_disposal_history_tb " +
            "WHERE community_id = :communityId " +
            "GROUP BY user_id) R " +
            "WHERE R.user_id = :userId", nativeQuery = true)
    int findUserRanking(@Param("userId") Long userId, @Param("communityId") Long communityId);

    //reason: Validation failed for query for method public abstract java.lang.Long moamoa.core.domain.community.CommunityRepository.calculateUserTotalEmissions(java.lang.Long)
//    // 사용자의 총 배출량 계산
//    // 일반팩과 멸균팩을 합쳐서 계산한다.
//    @Query("SELECT SUM(h.aseptic_quantity) + SUM(h.paper_quantity) FROM WasteDisposalHistory h WHERE h.user.id = :userId")
//    Long calculateUserTotalEmissions(@Param("userId") Long userId);
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
}
