package hsf302.he187383.phudd.carrental.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "reviews")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Review {
    @Id @GeneratedValue
    @Column(name = "review_id")
    UUID reviewId;

    @ManyToOne @JoinColumn(name = "booking_id")
    Booking booking;

    @ManyToOne @JoinColumn(name = "reviewer_id")
    User reviewer;

    @ManyToOne @JoinColumn(name = "target_user_id")
    User targetUser; // owner hoặc renter

    @Column(nullable = false)
    Integer rating; // 1..5

    @Column(columnDefinition = "NVARCHAR(MAX)")
    String comment;

    @Column(name = "created_at")
    LocalDateTime createdAt;
}
