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
public class MyPageDto {
    
    @Schema(description = "유저의 닉네임", example = "화연")
    private String nickname;

    @Schema(description = "커뮤니티 명", example = "럭키 아파트")
    private String communityName;
    
    @Schema(description = "최신 배출 히스토리")
    private List<WasteDisposalHistoryDto> wasteDisposalHistories;

    @Schema(description = "지난 주별 배출량", example = "[10, 20, 15, 5]")
    private List<Long> weeksEmission;

    @Schema(description = "지난 달별 배출량", example = "[50, 70, 40, 60]")
    private List<Long> monthsEmission;

    @Schema(description = "연간 배출량", example = "[550, 630, 480, 720]")
    private List<Long> yearsEmission;
}
