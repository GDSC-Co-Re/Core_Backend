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
public class RecentCoreInfoDto {
    @Schema(description = "커뮤니티 명", example = "럭키 아파트")
    private String communityName;

    @Schema(description = "커뮤니티 주 배출 총량", example = "3500")
    private Long communityTotalEmissionsWeek;

    @Schema(description = "커뮤니티 주 멸균팩 배출 총량", example = "1500")
    private Long communityTotalAsepticEmissionsWeek;

    @Schema(description = "커뮤니티 주 일반팩 배출 총량", example = "2000")
    private Long communityTotalRefrigeratedEmissionsWeek;

    @Schema(description = "커뮤니티 월 배출 총량", example = "3500")
    private Long communityTotalEmissionsMonth;

    @Schema(description = "커뮤니티 월 멸균팩 배출 총량", example = "1500")
    private Long communityTotalAsepticEmissionsMonth;

    @Schema(description = "커뮤니티 월 일반팩 배출 총량", example = "2000")
    private Long communityTotalRefrigeratedEmissionsMonth;

    @Schema(description = "커뮤니티 연 배출 총량", example = "3500")
    private Long communityTotalEmissionsYear;

    @Schema(description = "커뮤니티 연 멸균팩 배출 총량", example = "1500")
    private Long communityTotalAsepticEmissionsYear;

    @Schema(description = "커뮤니티 연 일반팩 배출 총량", example = "2000")
    private Long communityTotalRefrigeratedEmissionsYear;

    @Schema(description = "커뮤니티 최신 배출 히스토리")
    private List<WasteDisposalHistoryDto> wasteDisposalHistories;
}
