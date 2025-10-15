package hsf302.he187383.phudd.carrental.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationResponse {

    UUID notificationId;
    String type;
    String title;
    String body;
    Boolean isRead;
    String meta;
    LocalDateTime createdAt;
}
