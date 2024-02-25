package moamoa.core.domain.community;

import moamoa.core.domain.Address;
import moamoa.core.domain.community.dto.*;
import moamoa.core.domain.user.User;
import moamoa.core.domain.user.UserRepository;
import moamoa.core.domain.user.dto.MainInfoDto;
import moamoa.core.domain.user.dto.MyPageDto;
import moamoa.core.domain.wasteDisposalHistory.WasteDisposalHistory;
import moamoa.core.domain.wasteDisposalHistory.WasteDisposalHistoryRepository;
import moamoa.core.domain.wasteDisposalHistory.dto.WasteDisposalHistoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommunityService {
    private final UserRepository userRepository;
    private final CommunityRepository communityRepository;
    private final WasteDisposalHistoryRepository wasteDisposalHistoryRepository;

    @Autowired
    public CommunityService(UserRepository userRepository, CommunityRepository communityRepository, WasteDisposalHistoryRepository wasteDisposalHistoryRepository) {
        this.userRepository = userRepository;
        this.communityRepository = communityRepository;
        this.wasteDisposalHistoryRepository = wasteDisposalHistoryRepository;
    }

    public Long calculateCommunityTotalEmissions(Long communityId) {
        return communityRepository.calculateTotalEmissions(communityId);
    }

    public Long calculateCommunityWeeklyTotalEmissions(Long communityId) {
        return communityRepository.calculateWeeklyTotalEmissions(communityId);
    }

    public Long calculateCommunityWeeklyAsepticCartonEmissions(Long communityId){
        return communityRepository.calculateWeeklyAsepticCartonEmissions(communityId);
    }

    public Long calculateCommunityWeeklyPaperCartonEmissions(Long communityId){
        return communityRepository.calculateWeeklyPaperCartonEmissions(communityId);
    }

    public Long calculateCommunityMonthlyTotalEmissions(Long communityId) {
        return communityRepository.calculateMonthlyTotalEmissions(communityId);
    }

    public Long calculateCommunityMonthlyAsepticCartonEmissions(Long communityId){
        return communityRepository.calculateMonthlyAsepticCartonEmissions(communityId);
    }

    public Long calculateCommunityMonthlyPaperCartonEmissions(Long communityId){
        return communityRepository.calculateMonthlyPaperCartonEmissions(communityId);
    }

    public Long calculateCommunityYearlyTotalEmissions(Long communityId) {
        return communityRepository.calculateYearlyTotalEmissions(communityId);
    }

    public Long calculateCommunityYearlyAsepticCartonEmissions(Long communityId){
        return communityRepository.calculateYearlyAsepticCartonEmissions(communityId);
    }

    public Long calculateCommunityYearlyPaperCartonEmissions(Long communityId){
        return communityRepository.calculateYearlyPaperCartonEmissions(communityId);
    }

    public int calculateUserRanking(Long userId, Long communityId) {
        return communityRepository.findUserRanking(userId, communityId);
    }

    public int calculateDepth2CommunityRanking(Long communityId, String depth2) {
        return communityRepository.findDepth2CommunityRanking(communityId, depth2);
    }

    public List<Long> get4WeeksCommunityEmission(Long communityId) {
        List<Object[]> results = wasteDisposalHistoryRepository.get4WeeksCommunityEmission(communityId);
        return results.stream()
                .map(result -> ((Number) result[2]).longValue())
                .collect(Collectors.toList());
    }

    public List<Long> get4MonthsCommunityEmission(Long communityId) {
        List<Object[]> results = wasteDisposalHistoryRepository.get4MonthsCommunityEmission(communityId);
        return results.stream()
                .map(result -> ((Number) result[2]).longValue())
                .collect(Collectors.toList());
    }

    public List<Long> get4YearsCommunityEmission(Long communityId) {
        List<Object[]> results = wasteDisposalHistoryRepository.get4YearsCommunityEmission(communityId);
        return results.stream()
                .map(result -> ((Number) result[1]).longValue())
                .collect(Collectors.toList());

    }

    public CommunityInfoDto getCommunityInfo(String email, Pageable pageable){
        User user = userRepository.findMemberByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        String communityName = user.getCommunity().getName();
        Long communityTotalEmissions = calculateCommunityTotalEmissions(user.getCommunity().getId());
        Long monthlyCommunityAsepticCartonEmissions = calculateCommunityMonthlyAsepticCartonEmissions(user.getCommunity().getId());
        Long monthlyCommunityPaperCartonEmissions = calculateCommunityMonthlyPaperCartonEmissions(user.getCommunity().getId());
        String depth2Name = user.getCommunity().getAddress().getDepth2();
        int depth2CommunityRanking = calculateDepth2CommunityRanking(user.getCommunity().getId(), depth2Name);

//        // 페이지 요청 정보에 해당하는 Pageable 객체 생성
//        Pageable pageRequest = PageRequest.of(0, 5, Sort.by("disposalTime").descending()); // 예시로 페이지 0, 사이즈 5개로 설정
//
//        // 커뮤니티 히스토리 페이지 조회
//        Page<WasteDisposalHistoryDto> historyPage = wasteDisposalHistoryRepository.findCommunityWasteDisposalHistory(user.getCommunity().getId(), pageRequest);
//
//        // DTO 리스트 생성
//        List<WasteDisposalHistoryDto> wasteDisposalHistories = historyPage.getContent();

        // DTO로 매핑
        Page<WasteDisposalHistory> historyPage = wasteDisposalHistoryRepository.findByCommunityId(user.getCommunity().getId(), pageable);
        List<WasteDisposalHistoryDto> wasteDisposalHistories = historyPage.getContent()
                .stream()
                .map(history -> new WasteDisposalHistoryDto(
                        history.getId(),
                        history.getUser().getNickname(),
                        history.getDisposalTime(),
                        history.getAsepticCartonQuantity(),
                        history.getPaperCartonQuantity(),
                        history.getLikes(),
                        history.getPaperOrAseptic()))
                .collect(Collectors.toList());


        // CommunityInfoDto 객체 생성
        return CommunityInfoDto.builder()
                .communityName(communityName)
                .communityTotalEmissions(communityTotalEmissions)
                .communityTotalAsepticEmissions(monthlyCommunityAsepticCartonEmissions)
                .communityTotalRefrigeratedEmissions(monthlyCommunityPaperCartonEmissions)
                .communityDepth2(depth2Name)
                .communityRanking(depth2CommunityRanking)
                .wasteDisposalHistories(wasteDisposalHistories)
                .build();

    }

    public RecentCoreInfoDto getRecentCoreInfo(String email, Pageable pageable) {

        User user = userRepository.findMemberByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        String communityName = user.getCommunity().getName();

        // 주
        Long weeklyCommunityTotalEmissions = calculateCommunityWeeklyTotalEmissions(user.getCommunity().getId());
        Long weeklyCommunityAsepticCartonEmissions = calculateCommunityWeeklyAsepticCartonEmissions(user.getCommunity().getId());
        Long weeklyCommunityPaperCartonEmissions = calculateCommunityWeeklyPaperCartonEmissions(user.getCommunity().getId());

        // 월
        Long monthlyCommunityTotalEmissions = calculateCommunityMonthlyTotalEmissions(user.getCommunity().getId());
        Long monthlyCommunityAsepticCartonEmissions = calculateCommunityMonthlyAsepticCartonEmissions(user.getCommunity().getId());
        Long monthlyCommunityPaperCartonEmissions = calculateCommunityMonthlyPaperCartonEmissions(user.getCommunity().getId());

        // 년
        Long yearlyCommunityTotalEmissions = calculateCommunityYearlyTotalEmissions(user.getCommunity().getId());
        Long yearlyCommunityAsepticCartonEmissions = calculateCommunityYearlyAsepticCartonEmissions(user.getCommunity().getId());
        Long yearlyCommunityPaperCartonEmissions = calculateCommunityYearlyPaperCartonEmissions(user.getCommunity().getId());

        // DTO로 매핑
        Page<WasteDisposalHistory> historyPage = wasteDisposalHistoryRepository.findByCommunityId(user.getCommunity().getId(), pageable);
        List<WasteDisposalHistoryDto> wasteDisposalHistories = historyPage.getContent()
                .stream()
                .map(history -> new WasteDisposalHistoryDto(
                        history.getId(),
                        history.getUser().getNickname(),
                        history.getDisposalTime(),
                        history.getAsepticCartonQuantity(),
                        history.getPaperCartonQuantity(),
                        history.getLikes(),
                        history.getPaperOrAseptic()))
                .collect(Collectors.toList());


        // CommunityInfoDto 객체 생성
        return RecentCoreInfoDto.builder()
                .communityName(communityName)
                .communityTotalEmissionsWeek(weeklyCommunityTotalEmissions)
                .communityTotalAsepticEmissionsWeek(weeklyCommunityAsepticCartonEmissions)
                .communityTotalRefrigeratedEmissionsWeek(weeklyCommunityPaperCartonEmissions)
                .communityTotalEmissionsMonth(monthlyCommunityTotalEmissions)
                .communityTotalAsepticEmissionsMonth(monthlyCommunityAsepticCartonEmissions)
                .communityTotalRefrigeratedEmissionsMonth(monthlyCommunityPaperCartonEmissions)
                .communityTotalEmissionsYear(yearlyCommunityTotalEmissions)
                .communityTotalAsepticEmissionsYear(yearlyCommunityAsepticCartonEmissions)
                .communityTotalRefrigeratedEmissionsYear(yearlyCommunityPaperCartonEmissions)
                .wasteDisposalHistories(wasteDisposalHistories)
                .build();

    }

    public LocalRankingDto getLocalRankingInfo(String email){
        User user = userRepository.findMemberByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        String communityName = user.getCommunity().getName();
        String depth2Name = user.getCommunity().getAddress().getDepth2();
        int depth2CommunityRanking = calculateDepth2CommunityRanking(user.getCommunity().getId(), depth2Name);


        List<Object[]> topCommunitiesResults = communityRepository.findTop3CommunitiesInDepth2(depth2Name);
        List<CommunityLocalRankingDto> topCommunities = topCommunitiesResults.stream().map(result -> {
            String name = (String) result[0];
            String country = (String) result[1];
            String depth1 = (String) result[2];
            String depth2 = (String) result[3];
            String depth3 = (String) result[4];
            String zipcode = (String) result[5];
            Long ranking = (Long) result[6];

            Address address = new Address(country, depth1, depth2, depth3, zipcode);
            return new CommunityLocalRankingDto(name, address, ranking);
        }).collect(Collectors.toList());

        return LocalRankingDto.builder()
                .communityName(communityName)
                .communityDepth2(depth2Name)
                .communityRanking(depth2CommunityRanking)
                .CommunityLocalRanking(topCommunities)
                .build();
    }

    public CommunityMissionsDto getCommunityMissionsInfo(String email){

        User user = userRepository.findMemberByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        int communityLevel = user.getCommunity().getLevel();

        Long communityMembers = communityRepository.getMemberCount(user.getCommunity().getId());

        Long communityTotalEmissions = communityRepository.calculateTotalEmissions(user.getCommunity().getId());

        // 주별 배출량 조회
        List<Long> weeksEmission = get4WeeksCommunityEmission(user.getCommunity().getId());

        // 월별 배출량 조회
        List<Long> monthsEmission = get4MonthsCommunityEmission(user.getCommunity().getId());

        // 연별 배출량 조회
        List<Long> yearsEmission = get4YearsCommunityEmission(user.getCommunity().getId());

        return CommunityMissionsDto.builder()
                .communityLevel(communityLevel)
                .communityMembers(communityMembers)
                .communityTotalEmissions(communityTotalEmissions)
                .weeksEmission(weeksEmission)
                .monthsEmission(monthsEmission)
                .yearsEmission(yearsEmission)
                .build();
    }
}
