package dysystem.com.greedfinance.service.useCase.auth;

import dysystem.com.greedfinance.config.security.TokenProvider;
import dysystem.com.greedfinance.domain.model.User;
import dysystem.com.greedfinance.domain.repository.UserRepository;
import dysystem.com.greedfinance.dto.request.LoginRequestDTO;
import dysystem.com.greedfinance.dto.response.TokenResponseDTO;
import dysystem.com.greedfinance.handler.exception.BadRequestException;
import dysystem.com.greedfinance.handler.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
public class LoginUseCase {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public TokenResponseDTO execute(LoginRequestDTO loginDto, String id_tenant) {
        User user = userRepository.findByUsernameOrEmail(loginDto.login())
                .orElseThrow(() -> new NotFoundException("User not foung" + loginDto.login()));

        if (!user.isActive())
            throw new BadRequestException("A conta do usuário está inativa");

        if (!passwordEncoder.matches(loginDto.password(), user.getPassword()))
            throw new BadRequestException("Credenciais inválidas");

        return toTokenResponse(loginDto.login(), id_tenant);
    }

    private TokenResponseDTO toTokenResponse(String login, String tenant_id) {
        try {
            String token = tokenProvider.buildToken(login, tenant_id);
            return new TokenResponseDTO(token, "Bearer");
        } catch (Exception e) {
            throw new BadRequestException("Erro ao gerar o token de acesso");
        }
    }
}
