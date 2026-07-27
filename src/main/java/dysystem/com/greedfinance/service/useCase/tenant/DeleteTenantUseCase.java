package dysystem.com.greedfinance.service.useCase.tenant;

import dysystem.com.greedfinance.domain.repository.TenantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteTenantUseCase {

    private final TenantRepository tenantRepository;

    public void execute(String id){
        if (tenantRepository.findById(id).isPresent())
            tenantRepository.delete(id);
    }
}
