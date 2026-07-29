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
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class LoginUseCase {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final TenantRepository tenantRepository;

    @Transactional
    public TokenResponseDTO execute(LoginRequestDTO loginDto, String id_tenant) {
        User user = userRepository.findByUsernameOrEmailAndTenantId(loginDto.login(), id_tenant)
                .orElseThrow(() -> new NotFoundException("User not found " + loginDto.login()));

        if (!tenantRepository.findById(id_tenant).isPresent())
            throw new NotFoundException("This tenant don´t exists");

        if (!user.isActive())
            throw new BadRequestException("A conta do usuário está inativa");

        if (!passwordEncoder.matches(loginDto.password(), user.getPassword()))
            throw new BadRequestException("Credenciais inválidas");

        List<String> rolesName = user.getRoles().stream().map(Role::getName).toList();

        return toTokenResponse(loginDto.login(), id_tenant, user.isFirstAccess(), rolesName);
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
