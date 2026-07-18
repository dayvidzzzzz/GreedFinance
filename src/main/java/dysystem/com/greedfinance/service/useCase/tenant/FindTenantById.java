package dysystem.com.greedfinance.service.useCase.tenant;

import dysystem.com.greedfinance.domain.repository.TenantRepository;
import dysystem.com.greedfinance.dto.response.TenantResponseDTO;
import dysystem.com.greedfinance.handler.exception.NotFoundException;
import dysystem.com.greedfinance.utils.ToResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class FindTenantById {

    private final TenantRepository repository;
    private final ToResponseUtil toResponse;

    public TenantResponseDTO execute(String id){
        return repository.findById(id)
                .map(toResponse::toTenantResponse)
                .orElseThrow(() -> new NotFoundException("Tenant not found"));
    }

}
