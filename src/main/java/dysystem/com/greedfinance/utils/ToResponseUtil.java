package dysystem.com.greedfinance.utils;

import dysystem.com.greedfinance.domain.model.Tenant;
import dysystem.com.greedfinance.domain.model.User;
import dysystem.com.greedfinance.dto.response.TenantResponseDTO;
import dysystem.com.greedfinance.dto.response.UserResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class ToResponseUtil {

    public TenantResponseDTO toTenantResponse(Tenant tenant){
        return new TenantResponseDTO(
                tenant.getId(),
                tenant.getName()
        );
    }

    public UserResponseDTO toUserResponse(User user){
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getUsername(),
                user.getCreateAt()
        );
    }
}
