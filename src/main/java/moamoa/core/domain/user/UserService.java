package moamoa.core.domain.user;

import moamoa.core.domain.community.CommunityService;
import moamoa.core.domain.community.dto.CommunityInfoDto;
import moamoa.core.domain.user.dto.MainInfoDto;
import moamoa.core.domain.user.dto.MyPageDto;
import moamoa.core.domain.wasteDisposalHistory.WasteDisposalHistory;
import moamoa.core.domain.wasteDisposalHistory.WasteDisposalHistoryRepository;
import moamoa.core.domain.wasteDisposalHistory.dto.WasteDisposalHistoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<Long> get4WeeksUserEmission(Long userId) {
        List<Object[]> results = wasteDisposalHistoryRepository.get4WeeksUserEmission(userId);
        return results.stream()
                .map(result -> ((Number) result[2]).longValue())
                .collect(Collectors.toList());
    }

    public List<Long> get4MonthsUserEmission(Long userId) {
        List<Object[]> results = wasteDisposalHistoryRepository.get4MonthsUserEmission(userId);
        return results.stream()
                .map(result -> ((Number) result[2]).longValue())
                .collect(Collectors.toList());
    }

    public List<Long> get4YearsUserEmission(Long userId) {
        List<Object[]> results = wasteDisposalHistoryRepository.get4YearsUserEmission(userId);
        return results.stream()
                .map(result -> ((Number) result[1]).longValue())
                .collect(Collectors.toList());

    }

    public MyPageDto getMyPage(String email, Pageable pageable){
        User user = userRepository.findMemberByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }


        String nickname = user.getNickname();

        String communityName = user.getCommunity().getName();

        // 주별 배출량 조회
        List<Long> weeksEmission = get4WeeksUserEmission(user.getId());

        // 월별 배출량 조회
        List<Long> monthsEmission = get4MonthsUserEmission(user.getId());

        // 연별 배출량 조회
        List<Long> yearsEmission = get4YearsUserEmission(user.getId());

        // DTO로 매핑
        Page<WasteDisposalHistory> historyPage = wasteDisposalHistoryRepository.findByUserId(user.getId(), pageable);
        List<WasteDisposalHistoryDto> wasteDisposalHistories = historyPage.getContent()
                .stream()
                .map(history -> new WasteDisposalHistoryDto(
                        history.getUser().getNickname(),
                        history.getDisposalTime(),
                        history.getAsepticCartonQuantity(),
                        history.getPaperCartonQuantity(),
                        history.getLikes(),
                        history.getPaperOrAseptic()))
                .collect(Collectors.toList());

        // myPageInfoDto 객체 생성
        return MyPageDto.builder()
                .nickname(nickname)
                .communityName(communityName)
                .weeksEmission(weeksEmission)
                .monthsEmission(monthsEmission)
                .yearsEmission(yearsEmission)
                .wasteDisposalHistories(wasteDisposalHistories)
                .build();

    }
}
