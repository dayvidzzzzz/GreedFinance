package dysystem.com.greedfinance.infra.repository.impl;

import dysystem.com.greedfinance.domain.model.User;
import dysystem.com.greedfinance.domain.repository.UserRepository;
import dysystem.com.greedfinance.infra.entity.UserEntity;
import dysystem.com.greedfinance.infra.mapper.UserMapper;
import dysystem.com.greedfinance.infra.repository.jpa.UserRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserMapper mapper;
    private final UserRepositoryJpa repositoryJpa;

    @Override
    public Optional<User> findById(String id) {
        return repositoryJpa.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<User> findAll() {
        return repositoryJpa.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public User save(User user) {
       UserEntity entity = repositoryJpa.save(mapper.toEntity(user));
       return mapper.toDomain(entity);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repositoryJpa.findByEmail(email)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return repositoryJpa.findByUsername(username)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByUsernameOrEmail(String login) {
        return repositoryJpa.findByUsernameOrEmail(login)
                .map(mapper::toDomain);
    }
}
