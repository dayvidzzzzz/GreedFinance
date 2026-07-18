package dysystem.com.greedfinance.service.useCase.tenant;

import dysystem.com.greedfinance.domain.model.Role;
import dysystem.com.greedfinance.domain.model.Tenant;
import dysystem.com.greedfinance.domain.model.User;
import dysystem.com.greedfinance.domain.repository.RoleRepository;
import dysystem.com.greedfinance.domain.repository.TenantRepository;
import dysystem.com.greedfinance.domain.repository.UserRepository;
import dysystem.com.greedfinance.dto.request.TenantCreateRequestDTO;
import dysystem.com.greedfinance.dto.response.TenantResponseCreateDTO;
import dysystem.com.greedfinance.handler.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CreateTenantUseCase {

    private final TenantRepository tenantRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public TenantResponseCreateDTO execute(TenantCreateRequestDTO dto){
        if (tenantRepository.findByName(dto.name()).isPresent())
            throw new BusinessException("There´s already a tenant with this name");

        if (userRepository.findByEmail(dto.userMaster().email()).isPresent())
            throw new BusinessException("This email is already in use");

        if (userRepository.findByUsername(dto.userMaster().username()).isPresent())
            throw new BusinessException("This username is already in use");

        if (!dto.userMaster().password().equals(dto.userMaster().passwordConfirmation()))
            throw new BusinessException("The password does not match");

        Tenant tenant = Tenant.builder()
                .id(UUID.randomUUID().toString())
                .name(dto.name())
                .build();
        Tenant newTenant = tenantRepository.save(tenant);

        Role roleMaster = roleRepository.findByName("ROLE_MASTER")
                .orElseGet(() -> roleRepository.save(
                        Role.builder()
                                .name("ROLE_MASTER")
                                .build()
                ));

        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .name(dto.name())
                .username(dto.userMaster().username())
                .email(dto.userMaster().email())
                .password(passwordEncoder.encode(dto.userMaster().password()))
                .roles(Set.of(roleMaster))
                .tenant(newTenant)
                .build();
        User newUser = userRepository.save(user);

        return toResponse(newTenant, newUser);
    }

    private TenantResponseCreateDTO toResponse(Tenant tenant, User user){
        return new TenantResponseCreateDTO(
                tenant.getId(),
                tenant.getName(),
                new TenantResponseCreateDTO.UserTenantResponseDTO(
                    user.getId(),
                    user.getName(),
                    user.getUsername(),
                    user.getEmail()
                )
        );
    }
}
