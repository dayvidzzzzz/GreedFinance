package dysystem.com.greedfinance.domain.repository;

import dysystem.com.greedfinance.domain.model.Role;

import java.util.Optional;

public interface RoleRepository {
    Optional<Role> findByName(String name);
    Optional<Role> findById(Long id);
    Role save(Role role);
}
