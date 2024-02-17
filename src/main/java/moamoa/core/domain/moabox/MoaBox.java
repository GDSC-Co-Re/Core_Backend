package moamoa.core.domain.moabox;

import jakarta.persistence.*;
import lombok.Getter;
import moamoa.core.domain.Address;
import moamoa.core.domain.moaboxCollectionHistory.MoaBoxCollectionHistory;
import moamoa.core.domain.community.Community;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "MOABOX_TB")
public class MoaBox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="moabox_id")
    private Long id;

    @Embedded
    @Column(name = "moabox_address")
    private Address address;

    @Column(name = "moabox_capacity")
    private Long capacity;

    @Column(name = "moabox_roadname_address")
    private String roadNameAddress;

    @Column(name = "moabox_location")
    private String location;

    @OneToOne
    @JoinColumn(name = "community_id") //외래키
    private Community community;

    @OneToMany(mappedBy = "moaBox")
    private List<MoaBoxCollectionHistory> moaBoxCollectionHistoryList = new ArrayList<>();
}
