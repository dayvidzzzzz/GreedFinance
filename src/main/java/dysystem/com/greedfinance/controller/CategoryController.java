package dysystem.com.greedfinance.controller;

import dysystem.com.greedfinance.dto.request.CategoryRequestDTO;
import dysystem.com.greedfinance.dto.response.CategoryResponseDTO;
import dysystem.com.greedfinance.service.useCase.category.CategorySelectionLists;
import dysystem.com.greedfinance.service.useCase.category.CreateCategoryUseCase;
import dysystem.com.greedfinance.service.useCase.category.DeleteCategoryByIdUseCase;
import dysystem.com.greedfinance.service.useCase.category.FindCategoryByIdUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/categories")
@AllArgsConstructor
public class CategoryController {

    private final CategorySelectionLists categoryLists;
    private final CreateCategoryUseCase createCategoryUseCase;
    private final DeleteCategoryByIdUseCase deleteCategoryUseCase;
    private final FindCategoryByIdUseCase findCategoryUseCase;

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> create(@RequestBody CategoryRequestDTO name) {
        CategoryResponseDTO response = createCategoryUseCase.execute(name);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteCategoryUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(findCategoryUseCase.execute(id));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAll() {
        return ResponseEntity.ok(categoryLists.findAll());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<CategoryResponseDTO>> getAllByName(@PathVariable String name) {
        return ResponseEntity.ok(categoryLists.findAllByName(name));
    }
}
