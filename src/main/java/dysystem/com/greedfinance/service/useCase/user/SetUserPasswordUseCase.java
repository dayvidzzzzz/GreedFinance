package dysystem.com.greedfinance.service.useCase.user;

import dysystem.com.greedfinance.domain.model.User;
import dysystem.com.greedfinance.domain.repository.UserRepository;
import dysystem.com.greedfinance.dto.request.PasswordRequestDTO;
import dysystem.com.greedfinance.handler.exception.BusinessException;
import dysystem.com.greedfinance.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class SetUserPasswordUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void execute(PasswordRequestDTO dto){
        User user = SecurityUtils.getCurrentUser();

        if (!dto.password().equals(dto.passwordConfirmation()))
            throw new BusinessException("The password does not match");

        if (passwordEncoder.matches(dto.password(), user.getPassword()))
            throw new BusinessException("Ths password have to be different");

        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setFirstAccess(false);
        userRepository.save(user);
    }
}
