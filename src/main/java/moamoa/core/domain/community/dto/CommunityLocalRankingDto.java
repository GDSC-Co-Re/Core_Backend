package moamoa.core.domain.community.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import moamoa.core.domain.Address;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityLocalRankingDto {
    @Schema(description = "커뮤니티 명", example = "럭키 아파트")
    private String communityName;

    @Schema(description = "커뮤니티 주소", example = "서대문구")
    private Address communityAddress;

    @Schema(description = "구(depth2)에서 커뮤니티 순위", example = "1")
    private Long communityRanking;
}
