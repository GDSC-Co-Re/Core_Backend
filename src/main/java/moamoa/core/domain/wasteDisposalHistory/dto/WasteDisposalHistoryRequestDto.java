package moamoa.core.domain.wasteDisposalHistory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WasteDisposalHistoryRequestDto {
    private Long paperQuantity; // 종이 배출량
    private Long asepticQuantity; // 멸균 배출량
}
