package dysystem.com.greedfinance.service.useCase.user;

import dysystem.com.greedfinance.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class DeleteUserById {

    private final UserRepository userRepository;

    @Transactional
    public void execute(String id){
        userRepository.delete(id);
    }
}
