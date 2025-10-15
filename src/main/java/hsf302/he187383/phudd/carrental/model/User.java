package hsf302.he187383.phudd.carrental.model;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.*;
import java.util.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    UUID userId;

    @Column(unique = true, nullable = false)
    String email;

    String passwordHash;

    String fullName;

    String phone;

    @ManyToOne
    @JoinColumn(name = "role_id")
    Role role;

    String status; // active, suspended, banned

    LocalDate dob;

    String nationalId;

    String driverLicenseNo;

    Boolean isVerified;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    LocalDateTime deletedAt;

}
