package dysystem.com.greedfinance.service.useCase.tenant;

import dysystem.com.greedfinance.domain.repository.TenantRepository;
import dysystem.com.greedfinance.dto.response.TenantResponseDTO;
import dysystem.com.greedfinance.handler.exception.NotFoundException;
import dysystem.com.greedfinance.utils.ToResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FindTenantByNameUseCase {

    private final TenantRepository repository;
    private final ToResponseUtil toResponse;

    public TenantResponseDTO execute(String name){
        return repository.findByName(name)
                .map(toResponse::toTenantResponse)
                .orElseThrow(() -> new NotFoundException("Tenant not found"));
    }

}

