package dysystem.com.greedfinance.dto.response;

import java.time.LocalDateTime;

public record UserResponseDTO(
        String id,
        String name,
        String email,
        String username,
        LocalDateTime createAt
){
}
