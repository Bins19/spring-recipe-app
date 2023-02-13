package guru.springframework.springrecipeapp.controllers;

import guru.springframework.springrecipeapp.models.Category;
import guru.springframework.springrecipeapp.models.UnitOfMeasure;
import guru.springframework.springrecipeapp.repositories.CategoryRepository;
import guru.springframework.springrecipeapp.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class IndexController {

    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage() {
        Optional<Category> optionalCategory = categoryRepository.findByDescription("American");
        Optional<UnitOfMeasure> optionalUom = unitOfMeasureRepository.findByDescription("Teaspoon");

        System.out.println("Category ID is: " + optionalCategory.get().getId());
        System.out.println("UOM ID is: " + optionalUom.get().getId());
        return "index";
    }

}
