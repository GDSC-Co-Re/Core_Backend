package moamoa.core.domain.wasteDisposalHistory;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import moamoa.core.domain.community.Community;
import moamoa.core.domain.user.User;

import java.time.LocalDateTime;

@Entity
@Getter
//@AllArgsConstructor
@NoArgsConstructor
@Table(name = "WASTE_DISPOSAL_HISTORY_TB")
public class WasteDisposalHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="waste_disposal_history_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "community_id")
    private Community community;

    @Column(name="waste_disposal_history_paper_quantity")
    private Long paperCartonQuantity; //일반팩

    @Column(name="waste_disposal_history_aseptic_quantity")
    private Long asepticCartonQuantity; //멸균팩

    @Column(name="waste_disposal_history_paper_or_aseptic")
    private Boolean paperOrAseptic;

    @Column(name="waste_disposal_history_disposal_time")
    private LocalDateTime disposalTime;

    @Column(name="waste_disposal_history_likes")
    private Long likes;

    @Column(name="waste_disposal_history_collect")
    private boolean collect;

    // 비즈니스 로직
    public void addLikes() { this.likes+=1; }

    public void deleteLikes() { this.likes-=1; }

    @Builder
    public WasteDisposalHistory(Long id, User user, Community community, Long paperCartonQuantity, Long asepticCartonQuantity, Boolean paperOrAseptic, LocalDateTime disposalTime, Long likes, boolean collect) {
        this.id = id;
        this.user = user;
        this.community = community;
        this.paperCartonQuantity = paperCartonQuantity;
        this.asepticCartonQuantity = asepticCartonQuantity;
        this.paperOrAseptic = paperOrAseptic;
        this.disposalTime = disposalTime;
        this.likes = likes;
        this.collect = collect;
    }
}
