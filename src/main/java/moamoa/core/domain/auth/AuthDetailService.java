package moamoa.core.domain.auth;
import lombok.RequiredArgsConstructor;
import moamoa.core.domain.user.User;
import moamoa.core.domain.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthDetailService implements UserDetailsService {
    /* 사용자 정보를 데이터베이스에서 로드 */
    private final UserRepository userRepository;

    @Override
    public AuthDetails loadUserByUsername(String email) {
        User user = userRepository.findMemberByEmail(email);
        return new AuthDetails(user);
    }
}
