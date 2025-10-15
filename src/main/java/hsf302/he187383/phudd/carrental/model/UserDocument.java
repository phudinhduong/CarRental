package hsf302.he187383.phudd.carrental.model;


import hsf302.he187383.phudd.carrental.model.enums.DocumentStatus;
import hsf302.he187383.phudd.carrental.model.enums.DocumentType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_documents")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDocument {
    @Id @GeneratedValue
    @Column(name = "document_id")
    UUID documentId;

    @ManyToOne @JoinColumn(name = "user_id")
    User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    DocumentType type;

    @Column(name = "file_url", length = 1024)
    String fileUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    DocumentStatus status;

    @Column(name = "uploaded_at")
    LocalDateTime uploadedAt;
}
