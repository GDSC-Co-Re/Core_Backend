package moamoa.core.domain.community;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import moamoa.core.domain.Address;
import moamoa.core.domain.wasteDisposalHistory.WasteDisposalHistory;
import moamoa.core.domain.user.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "COMMUNITY_TB")
public class Community {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_id")
    private Long id;

    @Column(name = "community_name")
    private String name;

    @Embedded
    @Column(name = "community_address")
    private Address address;

    @Column(name = "community_level")
    private int level;

    @OneToMany(mappedBy = "community")
    @JsonIgnore
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "community")
    @JsonIgnore
    private List<WasteDisposalHistory> wasteDisposalHistories = new ArrayList<>();
}