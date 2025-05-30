package backend_blogapp.service;

import backend_blogapp.dto.request.CategoryRequest;
import backend_blogapp.dto.response.CategoryResponse;
import backend_blogapp.exception.ResourceNotFoundException;
import backend_blogapp.model.Category;
import backend_blogapp.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // Create category
    public CategoryResponse createCategory(CategoryRequest request) throws BadRequestException {
        if (categoryRepository.existsByName(request.getName())) {
            throw new BadRequestException("Category already exists");
        }

        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setSlug(createSlug(request.getName()));

        Category savedCategory = categoryRepository.save(category);
        return mapToCategoryResponse(savedCategory);
    }

    // Get all categories
    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(this::mapToCategoryResponse)
                .collect(Collectors.toList());
    }

    // Get category by ID
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        return mapToCategoryResponse(category);
    }


    private String createSlug(String name) {
        return name.toLowerCase().replaceAll("[^a-z0-9]+", "-");
    }
    private CategoryResponse mapToCategoryResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getSlug()
        );
    }


}
