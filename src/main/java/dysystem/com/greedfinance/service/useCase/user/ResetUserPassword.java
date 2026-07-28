package dysystem.com.greedfinance.service.useCase.user;

import dysystem.com.greedfinance.domain.model.User;
import dysystem.com.greedfinance.domain.repository.UserRepository;
import dysystem.com.greedfinance.handler.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ResetUserPassword {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void execute(String userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        user.setPassword(passwordEncoder.encode("123456"));
        user.setFirstAccess(true);
        userRepository.save(user);
    }
}
