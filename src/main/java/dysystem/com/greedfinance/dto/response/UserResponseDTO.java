package dysystem.com.greedfinance.dto.response;

import java.time.LocalDate;

public record UserResponseDTO(
        String id,
        String name,
        String email,
        String username,
        LocalDate createAt
){
}
