package moamoa.core.domain.wasteDisposalHistory;

import moamoa.core.domain.community.CommunityRepository;
import moamoa.core.domain.user.User;
import moamoa.core.domain.user.UserRepository;
import moamoa.core.domain.user.UserService;
import moamoa.core.domain.wasteDisposalHistory.dto.WasteDisposalHistoryRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDateTime;

@Service
public class WasteDisposalHistoryService {
    private final UserRepository userRepository;
    private final WasteDisposalHistoryRepository wasteDisposalHistoryRepository;
    @Autowired
    public WasteDisposalHistoryService(UserRepository userRepository, WasteDisposalHistoryRepository wasteDisposalHistoryRepository) {
        this.userRepository = userRepository;
        this.wasteDisposalHistoryRepository = wasteDisposalHistoryRepository;
    }

    public void createWasteDisposal(String email, WasteDisposalHistoryRequestDto requestDto) {
        User user = userRepository.findMemberByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        // 현재 날짜 및 시간을 가져옵니다.
        LocalDateTime now = LocalDateTime.now();

        // paperQuantity가 유효한 경우 저장
        if (requestDto.getPaperQuantity() != null && requestDto.getPaperQuantity() > 0) {
            WasteDisposalHistory paperHistory = WasteDisposalHistory.builder()
                    .user(user)
                    .community(user.getCommunity())
                    .paperCartonQuantity(requestDto.getPaperQuantity())
                    .asepticCartonQuantity(0L) // aseptic 값을 0으로 설정
                    .disposalTime(now)
                    .likes(0L)
                    .collect(false)
                    .paperOrAseptic(false) // paper 배출로 설정
                    .build();

            wasteDisposalHistoryRepository.save(paperHistory);
        }

        // asepticQuantity가 유효한 경우 저장
        if (requestDto.getAsepticQuantity() != null && requestDto.getAsepticQuantity() > 0) {
            WasteDisposalHistory asepticHistory = WasteDisposalHistory.builder()
                    .user(user)
                    .community(user.getCommunity())
                    .paperCartonQuantity(0L) // paper 값을 0으로 설정
                    .asepticCartonQuantity(requestDto.getAsepticQuantity())
                    .disposalTime(now)
                    .likes(0L)
                    .collect(false)
                    .paperOrAseptic(true) // aseptic 배출로 설정
                    .build();

            wasteDisposalHistoryRepository.save(asepticHistory);
        }
    }
}
