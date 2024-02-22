package moamoa.core.domain.wasteDisposalHistory.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WasteDisposalHistoryDto {

    @Schema(description = "유저명", example = "홍길동")
    private String userName;

    @Schema(description = "배출 시각", example = "2024-01-28 12:34:56")
    private LocalDateTime disposalTime;

    @Schema(description = "멸균팩 배출량", example = "1500")
    private Long asepticCartonQuantity;

    @Schema(description = "일반팩 배출량", example = "1500")
    private Long paperCartonQuantity;

    @Schema(description = "좋아요 수", example = "15")
    private Long likes;

    @Schema(description = "일반팩/멸균팩 여부", example = "0") //0이 일반팩
    private boolean paperOrAseptic;
}
