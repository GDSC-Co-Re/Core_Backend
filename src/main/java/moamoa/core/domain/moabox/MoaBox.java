package moamoa.core.domain.moabox;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import moamoa.core.domain.Address;
import moamoa.core.domain.Location;
import moamoa.core.domain.moaboxCollectionHistory.MoaBoxCollectionHistory;
import moamoa.core.domain.community.Community;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "MOABOX_TB")
public class MoaBox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="moabox_id")
    private Long id;

    @Column(name="moabox_name")
    private String name;

    @Embedded
    @Column(name = "moabox_address")
    private Address address;

    @Column(name = "moabox_capacity")
    private Long capacity;

    @Column(name = "moabox_roadname_address")
    private String roadNameAddress;

    @Embedded
    @Column(name = "moabox_location")
    private Location location;

    @OneToOne
    @JoinColumn(name = "community_id") //외래키
    private Community community;

    @OneToMany(mappedBy = "moaBox")
    private List<MoaBoxCollectionHistory> moaBoxCollectionHistoryList = new ArrayList<>();

    @Builder
    public MoaBox(Long id, String name, Address address, Long capacity, String roadNameAddress, Location location){
        this.id = id;
        this.name = name;
        this.address = address;
        this.capacity = capacity;
        this.roadNameAddress = roadNameAddress;
        this.location = location;
    }
}
