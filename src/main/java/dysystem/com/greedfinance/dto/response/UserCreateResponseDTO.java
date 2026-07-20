package dysystem.com.greedfinance.dto.response;

import java.time.LocalDateTime;

public record UserCreateResponseDTO(
        String id,
        String name,
        String email,
        String username,
        LocalDateTime createAt,
        RoleResponse role,
        TenantResponse tenant
) {
    public record RoleResponse(
            String name
    ){
    }

    public record TenantResponse(
            String name
    ){
    }
}
