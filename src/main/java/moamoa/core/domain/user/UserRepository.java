package moamoa.core.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //CRUD 연산 메서드, 쿼리 메서드를 이미 지원하는 JpaRepository 상속
public interface UserRepository extends JpaRepository<User, Long> {
    User findMemberById(Long userId);

    // 특정 맴버 이메일로 맴버 엔티티 반환
    User findMemberByEmail(String email);
}
