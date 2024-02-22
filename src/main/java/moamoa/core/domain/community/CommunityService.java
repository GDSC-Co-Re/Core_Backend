package moamoa.core.domain.community;

import moamoa.core.domain.Address;
import moamoa.core.domain.community.dto.CommunityInfoDto;
import moamoa.core.domain.user.User;
import moamoa.core.domain.user.UserRepository;
import moamoa.core.domain.user.dto.MainInfoDto;
import moamoa.core.domain.wasteDisposalHistory.WasteDisposalHistory;
import moamoa.core.domain.wasteDisposalHistory.WasteDisposalHistoryRepository;
import moamoa.core.domain.wasteDisposalHistory.dto.WasteDisposalHistoryDto;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Long calculateCommunityMonthlyTotalEmissions(Long communityId) {
        return communityRepository.calculateMonthlyTotalEmissions(communityId);
    }

    public Long calculateCommunityMonthlyAsepticCartonEmissions(Long communityId){
        return communityRepository.calculateMonthlyAsepticCartonEmissions(communityId);
    }

    public Long calculateCommunityMonthlyPaperCartonEmissions(Long communityId){
        return communityRepository.calculateMonthlyPaperCartonEmissions(communityId);
    }

    public int calculateUserRanking(Long userId, Long communityId) {
        return communityRepository.findUserRanking(userId, communityId);
    }

    public int calculateDepth2CommunityRanking(Long communityId, String depth2) {
        return communityRepository.findDepth2CommunityRanking(communityId, depth2);
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
                        history.getUser().getName(),
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
}
