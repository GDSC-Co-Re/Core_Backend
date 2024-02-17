package moamoa.core.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import moamoa.core.domain.Address;
import moamoa.core.domain.community.Community;
import moamoa.core.domain.profile.Profile;
import moamoa.core.domain.wasteDisposalHistory.WasteDisposalHistory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USER_TB")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    @Column(name="user_email")
    private String email;

    @Column(name="user_name")
    private String name;

    @Column(name="user_password")
    private String password;

    @Column(name="user_phone")
    private String phone;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Embedded
    @Column(name="user_address")
    private Address address;

    @Column(name="user_created_at")
    private LocalDateTime createdAt;

    @Column(name="user_updated_at")
    private LocalDateTime updatedAt;

    @Column(name="user_token")
    private String token;

    @Column(name="user_nickname")
    private String nickname;

    @ManyToOne
    @JoinColumn(name = "community_id") //외래키
    private Community community;

    @OneToOne(mappedBy = "user")
    private Profile profile;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<WasteDisposalHistory> wasteDisposalHistories = new ArrayList<>();

    @Builder
    public User(String email, String name, String password, String phone, Address address, LocalDateTime createdAt, LocalDateTime updatedAt, String token, String nickname){
        this.email = email;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.userRole = Objects.requireNonNullElse(userRole, userRole.ROLE_USER); //값이 없다면, ROLE_USER로 초기화
        this.address = address;
        this.userRole = Objects.requireNonNullElse(userRole, userRole.ROLE_USER); //값이 없다면, ROLE_USER로 초기화
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.token = token;
        this.nickname = nickname;
    }

}
