package hsf302.he187383.phudd.carrental.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id @GeneratedValue
    @Column(name = "user_id")
    UUID userId;

    @Column(nullable = false, unique = true, length = 255)
    String email;

    @Column(name = "password_hash", length = 255)
    String passwordHash;

    @Column(name = "full_name", length = 255)
    String fullName;

    @Column(length = 50)
    String phone;

    @ManyToOne @JoinColumn(name = "role_id")
    Role role;

    @Column(length = 32)
    String status; // active, suspended, banned

    LocalDate dob;

    @Column(name = "national_id", length = 255)
    String nationalId; // tokenized/hashed nếu sensitive

    @Column(name = "driver_license_no", length = 255)
    String driverLicenseNo;

    @Column(name = "is_verified")
    Boolean isVerified;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    LocalDateTime deletedAt;
}
