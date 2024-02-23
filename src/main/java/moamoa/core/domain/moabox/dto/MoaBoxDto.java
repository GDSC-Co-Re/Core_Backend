package moamoa.core.domain.moabox.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import moamoa.core.domain.Location;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MoaBoxDto {
    @Schema(description = "모아함 명", example = "럭키 아파트 모아함")
    private String moaBoxName;

    @Schema(description = "모아함의 경도, 위도")
    private Location moaBoxLocation;

    @Schema(description = "모아함이 속한 커뮤니티 명", example = "럭키 아파트")
    private String communityName;
}
