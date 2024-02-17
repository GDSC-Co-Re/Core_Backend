package moamoa.core.domain.moaboxCollectionHistory;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import moamoa.core.domain.moabox.MoaBox;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "MOABOX_COLLECTION_HISTORY_TB")
public class MoaBoxCollectionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="moabox_collection_history_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "moabox_id")
    private MoaBox moaBox;

    @Column(name = "moabox_collection_history_paper_quantity")
    private Long paperCartonQuantity; //일반팩

    @Column(name = "moabox_collection_history_aseptic_quantity")
    private Long asepticCartonQuantity; //멸균팩

    @Column(name = "moabox_collection_history_collection_time")
    private LocalDateTime collectionTime;

    @Builder
    public MoaBoxCollectionHistory(Long id, MoaBox moaBox, Long paperCartonQuantity, Long asepticCartonQuantity, LocalDateTime collectionTime) {
        this.id = id;
        this.moaBox = moaBox;
        this.paperCartonQuantity = paperCartonQuantity;
        this.asepticCartonQuantity = asepticCartonQuantity;
        this.collectionTime = collectionTime;
    }
}
