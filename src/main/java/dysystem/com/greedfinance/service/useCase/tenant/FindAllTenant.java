package dysystem.com.greedfinance.service.useCase.tenant;

import dysystem.com.greedfinance.domain.repository.TenantRepository;
import dysystem.com.greedfinance.dto.response.TenantResponseDTO;
import dysystem.com.greedfinance.utils.ToResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FindAllTenant {
    private final TenantRepository tenantRepository;
    private final ToResponseUtil toResponse;

    public List<TenantResponseDTO> execute(){
        return tenantRepository.findAll()
                .stream()
                .map(toResponse::toTenantResponse)
                .toList();
    }
}
