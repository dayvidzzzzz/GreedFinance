package dysystem.com.greedfinance.service.useCase.user;

import dysystem.com.greedfinance.domain.model.User;
import dysystem.com.greedfinance.domain.repository.UserRepository;
import dysystem.com.greedfinance.dto.request.UserRequestDTO;
import dysystem.com.greedfinance.dto.response.UserUpdateResponseDTO;
import dysystem.com.greedfinance.handler.exception.BusinessException;
import dysystem.com.greedfinance.handler.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateUserUseCase {

    private final UserRepository userRepository;

    public UserUpdateResponseDTO execute(String idUser, UserRequestDTO dto){
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (userRepository.findByEmail(dto.email()).isPresent())
            throw new BusinessException("This email is already in use");

        if (userRepository.findByUsername(dto.username()).isPresent())
            throw new BusinessException("This username is already in use");

        user.setEmail(dto.email());
        user.setName(dto.name());
        user.setUsername(dto.username());
        User updatedUser = userRepository.save(user);

        return toResponse(updatedUser);
    }

    private UserUpdateResponseDTO toResponse(User user) {
        return new UserUpdateResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getUsername(),
                user.getCreateAt(),
                user.isFirstAccess()
        );
    }
}
