package dysystem.com.greedfinance.service.useCase.category;

import dysystem.com.greedfinance.domain.model.Category;
import dysystem.com.greedfinance.domain.repository.CategoryRepository;
import dysystem.com.greedfinance.dto.request.CategoryRequestDTO;
import dysystem.com.greedfinance.dto.response.CategoryResponseDTO;
import dysystem.com.greedfinance.handler.exception.BusinessException;
import dysystem.com.greedfinance.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateCategoryUseCase {

    private final CategoryRepository categoryRepository;

    public CategoryResponseDTO execute(CategoryRequestDTO dto){
        if (categoryRepository.findByName(dto.name()).isPresent())
             throw new BusinessException("There´s already a category with this name");

        String idTenant = SecurityUtils.getCurrentTenantId();

        Category category = Category.builder()
                .name(dto.name())
                .tenantId(idTenant)
                .build();
        Category saved = categoryRepository.save(category);
        return toResponse(saved);
    }

    private CategoryResponseDTO toResponse(Category category){
        return new CategoryResponseDTO(category.getId(), category.getName());
    }
}
