package dysystem.com.greedfinance.service.useCase.category;

import dysystem.com.greedfinance.domain.model.Category;
import dysystem.com.greedfinance.domain.repository.CategoryRepository;
import dysystem.com.greedfinance.dto.response.CategoryResponseDTO;
import dysystem.com.greedfinance.handler.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FindCategoryByIdUseCase {
    private final CategoryRepository categoryRepository;

    public CategoryResponseDTO execute(Long id){
        return categoryRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new NotFoundException("Category not fount"));
    }

    private CategoryResponseDTO toResponse(Category category){
        return new CategoryResponseDTO(category.getId(), category.getName());
    }
}
