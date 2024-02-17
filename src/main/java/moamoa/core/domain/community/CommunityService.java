package moamoa.core.domain.community;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommunityService {
    private final CommunityRepository communityRepository;

    @Autowired
    public CommunityService(CommunityRepository communityRepository) {
        this.communityRepository = communityRepository;
    }

    public Long calculateCommunityTotalEmissions(Long communityId) {
        return communityRepository.calculateTotalEmissions(communityId);
    }

    public int calculateUserRanking(Long userId, Long communityId) {
        return communityRepository.findUserRanking(userId, communityId);
    }

//    // 이건 나중에 UserService로 옮겨야 할 듯
//    public Long calculateUserTotalEmissions(Long userId) {
//        return communityRepository.calculateUserTotalEmissions(userId);
//    }
}
