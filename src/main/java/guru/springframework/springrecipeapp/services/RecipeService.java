package guru.springframework.springrecipeapp.services;

import guru.springframework.springrecipeapp.models.Recipe;
import guru.springframework.springrecipeapp.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<Recipe> getAll() {
        return (List<Recipe>) recipeRepository.findAll();
    }

}
