package hsf302.he187383.phudd.carrental.repository;// RoleRepository.java


import hsf302.he187383.phudd.carrental.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(String name);
}
