package moamoa.core.domain.community.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import moamoa.core.domain.wasteDisposalHistory.WasteDisposalHistory;
import moamoa.core.domain.wasteDisposalHistory.dto.WasteDisposalHistoryDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityInfoDto {

    @Schema(description = "커뮤니티 명", example = "럭키 아파트")
    private String communityName;

    @Schema(description = "커뮤니티 월별 배출 총량", example = "3500")
    private Long communityTotalEmissions;

    @Schema(description = "커뮤니티 월별 멸균팩 배출 총량", example = "1500")
    private Long communityTotalAsepticEmissions;

    @Schema(description = "커뮤니티 월별 일반팩 배출 총량", example = "2000")
    private Long communityTotalRefrigeratedEmissions;

    @Schema(description = "구 이름", example = "서대문구")
    private String communityDepth2;

    @Schema(description = "구(depth2)에서 커뮤니티 순위", example = "1")
    private int communityRanking;

    @Schema(description = "커뮤니티 최신 배출 히스토리")
    private List<WasteDisposalHistoryDto> wasteDisposalHistories;
}
