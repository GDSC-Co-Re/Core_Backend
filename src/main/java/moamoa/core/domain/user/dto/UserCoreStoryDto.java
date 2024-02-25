package moamoa.core.domain.user.dto;

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
public class UserCoreStoryDto {

    @Schema(description = "유저 주 배출 총량", example = "3500")
    private Long userTotalEmissionsWeek;

    @Schema(description = "유저 주 멸균팩 배출 총량", example = "1500")
    private Long userTotalAsepticEmissionsWeek;

    @Schema(description = "유저 주 일반팩 배출 총량", example = "2000")
    private Long userTotalRefrigeratedEmissionsWeek;

    @Schema(description = "유저 월 배출 총량", example = "3500")
    private Long userTotalEmissionsMonth;

    @Schema(description = "유저 월 멸균팩 배출 총량", example = "1500")
    private Long userTotalAsepticEmissionsMonth;

    @Schema(description = "유저 월 일반팩 배출 총량", example = "2000")
    private Long userTotalRefrigeratedEmissionsMonth;

    @Schema(description = "유저 연 배출 총량", example = "3500")
    private Long userTotalEmissionsYear;

    @Schema(description = "유저 연 멸균팩 배출 총량", example = "1500")
    private Long userTotalAsepticEmissionsYear;

    @Schema(description = "유저 연 일반팩 배출 총량", example = "2000")
    private Long userTotalRefrigeratedEmissionsYear;

    @Schema(description = "유저 최신 배출 히스토리")
    private List<WasteDisposalHistoryDto> wasteDisposalHistories;
}
