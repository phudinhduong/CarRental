package hsf302.he187383.phudd.carrental.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.UUID;

@Entity
@Table(name = "roles")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role {
    @Id @GeneratedValue
    @Column(name = "role_id")
    UUID roleId;

    @Column(nullable = false, unique = true)
    String name; // Admin, Owner, Renter

    String description;
}
