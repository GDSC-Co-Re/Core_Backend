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
public class RegisterRequestDto {
    @Schema(description = "사용자의 이메일 주소", example = "1234@email.com")
    private String email;

    @Schema(description = "사용자의 이름", example = "Mary Jane")
    private String name;

    @Schema(description = "사용자의 비밀번호", example = "abc123!#")
    private String password;

    @Schema(description = "사용자의 전화번호", example = "010-0000-0000")
    private String phone;

    private AddressDto addressDto;

    @Schema(description = "사용자의 별명", example = "MJ")
    private String nickname;
}
