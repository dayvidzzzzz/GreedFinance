package dysystem.com.greedfinance.service.useCase.user;

import dysystem.com.greedfinance.domain.repository.UserRepository;
import dysystem.com.greedfinance.dto.response.UserResponseDTO;
import dysystem.com.greedfinance.handler.exception.NotFoundException;
import dysystem.com.greedfinance.utils.ToResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FindAllUsersUseCase {

    private final UserRepository userRepository;
    private final ToResponseUtil toResponse;

    public List<UserResponseDTO> execute(){
        return userRepository.findAll()
                .stream()
                .map(toResponse::toUserResponse)
                .toList();
    }
}
