package moamoa.core.domain.wasteDisposalHistory;

import moamoa.core.domain.community.CommunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WasteDisposalHistoryService {
    private final WasteDisposalHistoryRepository wasteDisposalHistoryRepository;
    @Autowired
    public WasteDisposalHistoryService(WasteDisposalHistoryRepository wasteDisposalHistoryRepository) {
        this.wasteDisposalHistoryRepository = wasteDisposalHistoryRepository;
    }
}
