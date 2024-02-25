package moamoa.core.domain.community.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import moamoa.core.domain.community.CommunityService;
import moamoa.core.domain.community.dto.CommunityInfoDto;
import moamoa.core.domain.community.dto.CommunityMissionsDto;
import moamoa.core.domain.community.dto.LocalRankingDto;
import moamoa.core.domain.community.dto.RecentCoreInfoDto;
import moamoa.core.domain.user.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@Tag(name = "커뮤니티 페이지 API", description = "커뮤니티 페이지 정보 제공용 API.")
@RequestMapping("/community")
public class CommunityController {
    private final CommunityService communityService; // UserService를 주입
    private final UserService userService;
    @GetMapping("/info")
    public ResponseEntity<CommunityInfoDto> getCommunityInfo(Authentication authentication,
                                                             @PageableDefault(size = 5, sort = "disposalTime", direction = org.springframework.data.domain.Sort.Direction.DESC) Pageable pageable) {
        // 인증된 사용자의 이메일 또는 ID를 가져옴
        String userEmail = authentication.getName(); // 또는 다른 식별 정보

        // UserService를 통해 필요한 정보 조회
        CommunityInfoDto communityInfoDto = communityService.getCommunityInfo(userEmail, pageable);

        // 조회된 정보를 MainInfoDto에 담아 반환
        return ResponseEntity.ok(communityInfoDto);
    }

    @GetMapping("/recent-core")
    public ResponseEntity<RecentCoreInfoDto> getRecentCoreInfo(Authentication authentication,
                                                               @PageableDefault(size = 5, sort = "disposalTime", direction = org.springframework.data.domain.Sort.Direction.DESC) Pageable pageable){

        // 인증된 사용자의 이메일 또는 ID를 가져옴
        String userEmail = authentication.getName(); // 또는 다른 식별 정보

        // UserService를 통해 필요한 정보 조회
        RecentCoreInfoDto recentCoreInfoDto = communityService.getRecentCoreInfo(userEmail, pageable);

        // 조회된 정보를 MainInfoDto에 담아 반환
        return ResponseEntity.ok(recentCoreInfoDto);
    }

    @GetMapping("/local-ranking")
    public ResponseEntity<LocalRankingDto> getLocalRankingInfo(Authentication authentication){
        // 인증된 사용자의 이메일 또는 ID를 가져옴
        String userEmail = authentication.getName(); // 또는 다른 식별 정보

        // UserService를 통해 필요한 정보 조회
        LocalRankingDto localRankingDto = communityService.getLocalRankingInfo(userEmail);

        // 조회된 정보를 MainInfoDto에 담아 반환
        return ResponseEntity.ok(localRankingDto);
    }

    @GetMapping("/missions")
    public ResponseEntity<CommunityMissionsDto> getCommunityMissionsInfo(Authentication authentication){
        // 인증된 사용자의 이메일 또는 ID를 가져옴
        String userEmail = authentication.getName(); // 또는 다른 식별 정보

        // UserService를 통해 필요한 정보 조회
        CommunityMissionsDto communityMissionsDto = communityService.getCommunityMissionsInfo(userEmail);

        // 조회된 정보를 MainInfoDto에 담아 반환
        return ResponseEntity.ok(communityMissionsDto);
    }
}
