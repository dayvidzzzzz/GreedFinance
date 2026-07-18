package dysystem.com.greedfinance.dto.response;

import java.time.LocalDate;

public record UserCreateResponseDTO(
        String id,
        String name,
        String email,
        String username,
        LocalDate createAt,
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
