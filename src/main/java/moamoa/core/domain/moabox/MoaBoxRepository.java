package moamoa.core.domain.moabox;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoaBoxRepository extends JpaRepository<MoaBox, Long> {
    MoaBox findMoaBoxById(Long MoaBoxId);
}
