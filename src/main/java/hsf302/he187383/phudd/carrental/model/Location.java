package hsf302.he187383.phudd.carrental.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.UUID;

@Entity
@Table(name = "locations")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Location {
    @Id @GeneratedValue
    @Column(name = "location_id")
    UUID locationId;

    String country;
    String province;
    String city;
    String district;
    String ward;

    @Column(name = "address_line")
    String addressLine;

//    @Column(precision = 9, scale = 6)
    Double lat;

//    @Column(precision = 9, scale = 6)
    Double lng;
}
