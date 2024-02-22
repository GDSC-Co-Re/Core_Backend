package moamoa.core.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class Address {
    private String country;

    private String depth1; // 도

    private String depth2; // 시

    private String depth3; // 동

    private String zipcode;

    @Builder
    Address(String country, String depth1, String depth2, String depth3, String zipcode){
        this.country = country;
        this.depth1 = depth1;
        this.depth2 = depth2;
        this.depth3 = depth3;
        this.zipcode = zipcode;
    }

}
