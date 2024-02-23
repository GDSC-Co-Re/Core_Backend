package moamoa.core.domain;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class Location {
    private double latitude;

    private double longitude;

    @Builder
    Location(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
