package moamoa.core.domain.wasteDisposalHistory.api;

import com.google.rpc.context.AttributeContext;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import moamoa.core.domain.wasteDisposalHistory.WasteDisposalHistory;
import moamoa.core.domain.wasteDisposalHistory.WasteDisposalHistoryService;
import moamoa.core.domain.wasteDisposalHistory.dto.WasteDisposalHistoryRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/waste-disposal")
@RequiredArgsConstructor
@Tag(name = "인증 테스트 API", description = "JWT 토큰 인증 테스트용 API.")
public class WasteDisposalHistoryController {

    private final WasteDisposalHistoryService wasteDisposalHistoryService;

    @PostMapping
    public ResponseEntity<String> createWasteDisposal(Authentication authentication, @RequestBody WasteDisposalHistoryRequestDto requestDto) {
        // 인증된 사용자의 이메일 또는 ID를 가져옴
        String userEmail = authentication.getName(); // 또는 다른 식별 정보

        wasteDisposalHistoryService.createWasteDisposal(userEmail, requestDto);
        return ResponseEntity.ok("Waste disposal history saved successfully");
    }

    @PostMapping("/{id}/likes")
    public ResponseEntity<Void> incrementLikes(@PathVariable Long id) {
        wasteDisposalHistoryService.incrementLikes(id);
        return ResponseEntity.ok().build();
    }

}
