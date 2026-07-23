package dysystem.com.greedfinance.service.useCase.category;

import dysystem.com.greedfinance.domain.repository.CategoryRepository;
import dysystem.com.greedfinance.handler.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteCategoryByIdUseCase {
    private final CategoryRepository categoryRepository;

    public void execute(Long id){
        if (!categoryRepository.findById(id).isPresent())
            throw new BusinessException("This category don´t exists");
        categoryRepository.delete(id);
    }
}
