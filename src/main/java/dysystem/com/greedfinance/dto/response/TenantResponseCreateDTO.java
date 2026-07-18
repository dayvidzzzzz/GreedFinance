package dysystem.com.greedfinance.dto.response;

public record TenantResponseCreateDTO(
        String id,
        String name,
        UserTenantResponseDTO userTenant
) {
    public record UserTenantResponseDTO(
            String id,
            String name,
            String username,
            String email
    ){}
}
