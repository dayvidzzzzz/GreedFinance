package dysystem.com.greedfinance.service.useCase.category;

import dysystem.com.greedfinance.domain.model.Category;
import dysystem.com.greedfinance.domain.repository.CategoryRepository;
import dysystem.com.greedfinance.dto.response.CategoryResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategorySelectionLists {
    private final CategoryRepository categoryRepository;

    public List<CategoryResponseDTO> findAll(){
        return categoryRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<CategoryResponseDTO> findAllByName(String name){
        return categoryRepository.findByName(name)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private CategoryResponseDTO toResponse(Category category){
        return new CategoryResponseDTO(category.getId(), category.getName());
    }
}
