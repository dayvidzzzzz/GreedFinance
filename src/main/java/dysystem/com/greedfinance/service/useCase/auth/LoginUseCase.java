package dysystem.com.greedfinance.service.useCase.auth;

import dysystem.com.greedfinance.config.security.TokenProvider;
import dysystem.com.greedfinance.domain.model.Role;
import dysystem.com.greedfinance.domain.model.User;
import dysystem.com.greedfinance.domain.repository.TenantRepository;
import dysystem.com.greedfinance.domain.repository.UserRepository;
import dysystem.com.greedfinance.dto.request.LoginRequestDTO;
import dysystem.com.greedfinance.dto.response.TokenResponseDTO;
import dysystem.com.greedfinance.handler.exception.BadRequestException;
import dysystem.com.greedfinance.handler.exception.NotFoundException;
import dysystem.com.greedfinance.utils.TenantContext;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LoginUseCase {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final TenantRepository tenantRepository;

    @Transactional
    public TokenResponseDTO execute(LoginRequestDTO loginDto, String id_tenant) {
        if (id_tenant != null && !id_tenant.isEmpty())
            TenantContext.setCurrentTenantId(id_tenant);

        User user = findUserWithFallback(loginDto.login(), id_tenant);

        if (user.getTenantId() != null && !user.getTenantId().isEmpty())
            if (!tenantRepository.findById(user.getTenantId()).isPresent())
                throw new NotFoundException("Tenant don´t exists");

        if (!user.isActive())
            throw new BadRequestException("A conta do usuário está inativa");

        if (!passwordEncoder.matches(loginDto.password(), user.getPassword()))
            throw new BadRequestException("Credenciais inválidas");

        List<String> rolesName = user.getRoles().stream().map(Role::getName).toList();
        String tenantForToken = user.getTenantId() != null ? user.getTenantId() : id_tenant;
        TokenResponseDTO response = toTokenResponse(loginDto.login(), tenantForToken, user.isFirstAccess(), rolesName);
        return response;
    }


    private User findUserWithFallback(String login, String tenantId) {
        Optional<User> userOptional = Optional.empty();

        if (tenantId != null && !tenantId.isEmpty()) {
            userOptional = userRepository.findByUsernameOrEmailAndTenantId(login, tenantId);
            if (userOptional.isPresent())
                return userOptional.get();
        }

        userOptional = userRepository.findByUsernameOrEmail(login);
        if (userOptional.isPresent())
            return userOptional.get();

        throw new NotFoundException("User not found: " + login);
    }

    private TokenResponseDTO toTokenResponse(String login, String tenant_id, boolean fistAccess, List<String> roles) {
        try {
            String token = tokenProvider.buildToken(login, tenant_id);
            return new TokenResponseDTO(token, "Bearer", fistAccess, roles);
        } catch (Exception e) {
            throw new BadRequestException("Erro ao gerar o token de acesso");
        }
    }
}