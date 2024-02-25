package moamoa.core.domain.community.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityMissionsDto {
    @Schema(description = "커뮤니티 레벨", example = "10")
    private int communityLevel;

    @Schema(description = "커뮤니티 멤버 수", example = "10")
    private Long communityMembers;

    @Schema(description = "커뮤니티 총 배출량", example = "10")
    private Long communityTotalEmissions;

    @Schema(description = "지난 주별 배출량", example = "[10, 20, 15, 5]")
    private List<Long> weeksEmission;

    @Schema(description = "지난 달별 배출량", example = "[50, 70, 40, 60]")
    private List<Long> monthsEmission;

    @Schema(description = "연간 배출량", example = "[550, 630, 480, 720]")
    private List<Long> yearsEmission;
}
