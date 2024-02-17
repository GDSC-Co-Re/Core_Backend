package moamoa.core.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MainInfoDto {
    @Schema(description = "커뮤니티 배출 총량", example = "3500")
    private Long communityTotalEmissions;

    @Schema(description = "커뮤니티 내 유저의 순위", example = "1")
    private int userRanking;

    @Schema(description = "유저의 총 배출량", example = "350")
    private Long userTotalEmissions;

    @Schema(description = "유저의 닉네임", example = "화연")
    private String nickname;

    @Schema(description = "커뮤니티 명", example = "럭키 아파트")
    private String communityName;

}
