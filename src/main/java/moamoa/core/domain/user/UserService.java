package moamoa.core.domain.user;

import moamoa.core.domain.community.CommunityService;
import moamoa.core.domain.user.dto.MainInfoDto;
import moamoa.core.domain.wasteDisposalHistory.WasteDisposalHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final CommunityService communityService;
    private final WasteDisposalHistoryRepository wasteDisposalHistoryRepository;
    // 기타 필요한 서비스들을 주입받을 필드

    @Autowired
    public UserService(UserRepository userRepository, CommunityService communityService, WasteDisposalHistoryRepository wasteDisposalHistoryRepository) {
        this.userRepository = userRepository;
        this.communityService = communityService;
        this.wasteDisposalHistoryRepository = wasteDisposalHistoryRepository;
        // 기타 필요한 서비스들을 초기화
    }

    public MainInfoDto getMainInfo(String email) {
        User user = userRepository.findMemberByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        // 메인 페이지에 필요한 데이터를 계산하거나 조회합니다.
        Long communityTotalEmissions = communityService.calculateCommunityTotalEmissions(user.getCommunity().getId());
        int userRanking = communityService.calculateUserRanking(user.getId(), user.getCommunity().getId());
        //Long userTotalEmissions = communityService.calculateUserTotalEmissions(user.getId());
        // 사용자의 총 배출량을 계산하기 위해 wasteDisposalHistoryRepository를 사용합니다.
        Long userTotalEmissions = wasteDisposalHistoryRepository.calculateUserTotalEmissions(user.getId());
        String nickname = user.getNickname();
        String communityName = user.getCommunity().getName();

        // 조회된 데이터를 MainInfoDto 객체에 매핑합니다.
        return MainInfoDto.builder()
                .communityTotalEmissions(communityTotalEmissions)
                .userRanking(userRanking)
                .userTotalEmissions(userTotalEmissions)
                .nickname(nickname)
                .communityName(communityName)
                .build();
    }
}
