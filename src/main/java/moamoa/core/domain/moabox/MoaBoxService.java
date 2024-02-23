package moamoa.core.domain.moabox;

import lombok.RequiredArgsConstructor;
import moamoa.core.domain.Location;
import moamoa.core.domain.moabox.dto.MoaBoxDto;
import moamoa.core.domain.moabox.dto.MoaBoxListDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MoaBoxService {

    private final MoaBoxRepository moaBoxRepository;

    public MoaBoxListDto findAllMoaBoxes() {
        List<MoaBox> moaBoxList = moaBoxRepository.findAll();

        // MoaBoxListDto에 매핑
        List<MoaBoxDto> moaBoxDtos = moaBoxList.stream()
                .map(moaBox -> MoaBoxDto.builder()
                        .moaBoxName(moaBox.getName())
                        .moaBoxLocation(moaBox.getLocation())
                        .communityName(moaBox.getCommunity().getName())
                        .build())
                .toList(); // Java 16 이상의 버전에서 사용 가능

        // MoaBoxDto 리스트를 MoaBoxListDto에 담아 반환
        return new MoaBoxListDto(moaBoxDtos);
    }

    // MoaBoxService.java

//    public MoaBoxDto findNearestMoaBox(Location userLocation) {
//        // 여기에 사용자 위치를 기준으로 가장 가까운 모아함을 찾는 로직을 구현
//        // 예를 들어, 모아함의 위치를 모두 가져와서 userLocation과 비교하여 가장 가까운 모아함을 찾는다.
//        // 이 부분은 실제로 데이터베이스의 위치 데이터와 비교하는 복잡한 로직이 될 수 있습니다.
//        // 일단은 로직이 없으므로 더미 데이터를 반환합니다.
//
//        return MoaBoxDto.builder()
//                .moaBoxName("가까운 모아함 이름")
//                .moaBoxLocation(new Location(37.5665, 126.9780)) // 더미 경도, 위도
//                .communityName("가까운 모아함이 속한 커뮤니티 이름")
//                .build();
//    }


}
