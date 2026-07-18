package dysystem.com.greedfinance.domain.repository;

import dysystem.com.greedfinance.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(String id);
    List<User> findAll();
    User save(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
}
