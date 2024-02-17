package moamoa.core.domain.auth.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDto {
    @Schema(description = "나라", example = "한국")
    private String country;

    @Schema(description = "도", example = "경기도")
    private String depth1;

    @Schema(description = "시", example = "성남시")
    private String depth2;

    @Schema(description = "동", example = "판교동")
    private String depth3;    

    @Schema(description = "우편번호", example = "12345")
    private String zipcode;
}