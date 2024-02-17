package moamoa.core.domain.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "인증 테스트 API", description = "JWT 토큰 인증 테스트용 API.")
public class UserController {
    private final UserRepository userRepository;
    @GetMapping("/user/info")
    @Operation(summary = "맴버 엔티티 반환 API", description = "JWT 토큰을 바탕으로 맴버 엔티티를 반환하는 테스트용 API 입니다.")
    public ResponseEntity<User> getMemberData(Authentication authentication) {
        // 인증된 사용자의 정보를 인증 객체(authentication)를 통해 가져오기
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername(); // 여기서는 username에 email을 담았습니다.

        // 사용자 ID를 이용하여 데이터베이스에서 사용자 정보 조회
        User user = userRepository.findMemberByEmail(email);

        return new ResponseEntity<>(user, HttpStatusCode.valueOf(200));
    }
}
