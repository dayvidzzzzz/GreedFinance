package dysystem.com.greedfinance.service.useCase.user;

import dysystem.com.greedfinance.domain.repository.UserRepository;
import dysystem.com.greedfinance.dto.response.UserResponseDTO;
import dysystem.com.greedfinance.handler.exception.NotFoundException;
import dysystem.com.greedfinance.utils.ToResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FindUserByUsernameUseCase {


    private final UserRepository userRepository;
    private final ToResponseUtil toResponse;

    public UserResponseDTO execute(String username){
        return userRepository.findByUsername(username)
                .map(toResponse::toUserResponse)
                .orElseThrow(() -> new NotFoundException("User username not found"));
    }
}
