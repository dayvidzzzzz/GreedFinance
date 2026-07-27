package dysystem.com.greedfinance.dto.response;

import java.util.List;

public record TokenResponseDTO(
        String token,
        String Type,
        boolean firstAccess,
        List<String> roleNames
) {
}
