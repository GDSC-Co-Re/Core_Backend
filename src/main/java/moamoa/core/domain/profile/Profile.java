package moamoa.core.domain.profile;

import jakarta.persistence.*;
import lombok.Getter;
import moamoa.core.domain.user.User;

@Entity
@Getter
@Table(name = "PROFILE_TB")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="profile_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id") //외래키
    private User user;
}
