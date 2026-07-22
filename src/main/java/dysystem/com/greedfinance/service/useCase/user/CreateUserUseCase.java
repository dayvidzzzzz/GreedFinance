package dysystem.com.greedfinance.service.useCase.user;

import dysystem.com.greedfinance.domain.model.Role;
import dysystem.com.greedfinance.domain.model.Tenant;
import dysystem.com.greedfinance.domain.model.User;
import dysystem.com.greedfinance.domain.repository.RoleRepository;
import dysystem.com.greedfinance.domain.repository.TenantRepository;
import dysystem.com.greedfinance.domain.repository.UserRepository;
import dysystem.com.greedfinance.dto.request.UserCreateRequestDTO;
import dysystem.com.greedfinance.dto.response.UserCreateResponseDTO;
import dysystem.com.greedfinance.handler.exception.BusinessException;
import dysystem.com.greedfinance.handler.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CreateUserUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TenantRepository tenantRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserCreateResponseDTO execute(User userMaster, UserCreateRequestDTO dto) {
        if (userRepository.findByEmail(dto.email()).isPresent())
            throw new BusinessException("This email is already in use");

        if (userRepository.findByUsername(dto.username()).isPresent())
            throw new BusinessException("This username is already in use");

        String idTenant = userMaster.getTenantId();
        Tenant tenant = tenantRepository.findById(idTenant)
                .orElseThrow(() -> new NotFoundException("Tenant not found: " + idTenant));

        Role roleUser = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> roleRepository.save(
                        Role.builder()
                                .name("ROLE_USER")
                                .build()
                ));

        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .name(dto.name())
                .username(dto.username())
                .email(dto.email())
                .password(passwordEncoder.encode("123456"))
                .active(true)
                .tenantId(idTenant)
                .roleIds(Set.of(roleUser.getId()))
                .build();
        User savedUser = userRepository.save(user);
        return toResponse(savedUser, roleUser, tenant);
    }

    private UserCreateResponseDTO toResponse(User user, Role role, Tenant tenant) {
        return new UserCreateResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getUsername(),
                user.getCreateAt(),
                new UserCreateResponseDTO.RoleResponse(role.getName()),
                new UserCreateResponseDTO.TenantResponse(tenant.getName())
        );
    }
}