package moamoa.core.domain.user.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import moamoa.core.domain.user.UserService;
import moamoa.core.domain.user.dto.MainInfoDto;
import moamoa.core.domain.user.dto.MyPageDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "마이 페이지 API", description = "마이 페이지 정보 제공용 API.")
@RequestMapping("/mypage")
public class MyPageController {
    private final UserService userService; // UserService를 주입

    @GetMapping("/info")
    public ResponseEntity<MyPageDto> getMyInfo(Authentication authentication,
                                                   @PageableDefault(size = 5, sort = "disposalTime", direction = org.springframework.data.domain.Sort.Direction.DESC) Pageable pageable) {
        // 인증된 사용자의 이메일 또는 ID를 가져옴
        String userEmail = authentication.getName(); // 또는 다른 식별 정보

        // UserService를 통해 필요한 정보 조회
        MyPageDto myPageDto = userService.getMyPage(userEmail, pageable);

        // 조회된 정보를 MainInfoDto에 담아 반환
        return ResponseEntity.ok(myPageDto);
    }
}
