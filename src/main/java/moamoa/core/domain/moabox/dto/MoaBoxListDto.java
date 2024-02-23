package moamoa.core.domain.moabox.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import moamoa.core.domain.moabox.MoaBox;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MoaBoxListDto {
    private List<MoaBoxDto> moaBoxList;

}
