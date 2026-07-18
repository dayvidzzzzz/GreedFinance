package dysystem.com.greedfinance.dto.response;

import java.time.LocalDateTime;

public record RegisterResponseDTO (
        Long id,
        String name,
        String username,
        String email,
        boolean isActive,
        LocalDateTime createdAt,
        TokenResponseDTO tokenResponseDTO
){
}
