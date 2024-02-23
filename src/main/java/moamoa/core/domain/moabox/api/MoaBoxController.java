package moamoa.core.domain.moabox.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import moamoa.core.domain.Location;
import moamoa.core.domain.moabox.MoaBoxService;
import moamoa.core.domain.moabox.dto.MoaBoxDto;
import moamoa.core.domain.moabox.dto.MoaBoxListDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "모아함 API", description = "모아함 정보 제공용 API.")
@RequestMapping("/moabox")
public class MoaBoxController {

    private final MoaBoxService moaBoxService;
    @GetMapping("/all")
    @Operation(summary = "모아함 리스트 조회 API", description = "모든 모아함을 조회하는 API입니다.")
    public ResponseEntity<MoaBoxListDto> getAllMoaBoxes() {
        MoaBoxListDto allMoaBoxes = moaBoxService.findAllMoaBoxes();
        return ResponseEntity.ok(allMoaBoxes);
    }

//    public ResponseEntity<MoaBoxDto> findMoaBox(Authentication authentication) {
//        // 사용자 위치 정보를 가져오는 로직
//        // 이 부분은 프론트엔드에서 사용자의 위치 정보를 받아와야 할 수 있습니다.
//        // 예시를 위해 더미 위치 정보를 사용합니다.
//        Location userLocation = new Location(37.5665, 126.9780); // 더미 사용자 위치
//
//        // 서비스를 호출하여 가장 가까운 모아함 정보를 조회
//        MoaBoxDto findMoaBoxDto = moaBoxService.findNearestMoaBox(userLocation);
//
//        // 조회된 정보를 ResponseEntity로 감싸 반환
//        return ResponseEntity.ok(findMoaBoxDto);
//    }
}
