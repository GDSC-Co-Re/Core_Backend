package moamoa.core.domain.community.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import moamoa.core.domain.wasteDisposalHistory.dto.WasteDisposalHistoryDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocalRankingDto {
    @Schema(description = "커뮤니티 명", example = "럭키 아파트")
    private String communityName;

    @Schema(description = "구 이름", example = "서대문구")
    private String communityDepth2;

    @Schema(description = "구(depth2)에서 커뮤니티 순위", example = "1")
    private int communityRanking;

    @Schema(description = "top3 커뮤니티 리스트")
    private List<CommunityLocalRankingDto> CommunityLocalRanking;
}
