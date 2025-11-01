package hsf302.he187383.phudd.carrental.repository;

import hsf302.he187383.phudd.carrental.model.UserDocument;
import hsf302.he187383.phudd.carrental.model.enums.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserDocumentRepository extends JpaRepository<UserDocument, UUID> {
    List<UserDocument> findByUser_UserId(UUID userId);
    Optional<UserDocument> findByUser_UserIdAndType(UUID userId, DocumentType type);
}