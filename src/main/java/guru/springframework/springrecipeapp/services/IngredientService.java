package guru.springframework.springrecipeapp.services;

import guru.springframework.springrecipeapp.commands.IngredientCommand;
import guru.springframework.springrecipeapp.converters.IngredientToIngredientCommand;
import guru.springframework.springrecipeapp.models.Recipe;
import guru.springframework.springrecipeapp.repositories.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class IngredientService {

    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand converter;

    public IngredientService(RecipeRepository recipeRepository, IngredientToIngredientCommand converter) {
        this.recipeRepository = recipeRepository;
        this.converter = converter;
    }

    @Transactional
    public Optional<IngredientCommand> getByIdAndRecipeId(Long id, Long recipeId) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
        if(optionalRecipe.isEmpty()) {
            throw new RuntimeException("Recipe ID " + recipeId + " not found");
        }

        Optional<IngredientCommand> ingredientCommandOptional = optionalRecipe.get().getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(id))
                .findFirst()
                .map(converter::convert);

        if(ingredientCommandOptional.isEmpty()) {
            throw new RuntimeException("Ingredient ID " + id + " not found");
        }

        return ingredientCommandOptional;
    }

}
