package guru.springframework.springrecipeapp.services;

import guru.springframework.springrecipeapp.commands.IngredientCommand;
import guru.springframework.springrecipeapp.converters.IngredientCommandToIngredient;
import guru.springframework.springrecipeapp.converters.IngredientToIngredientCommand;
import guru.springframework.springrecipeapp.models.Ingredient;
import guru.springframework.springrecipeapp.models.Recipe;
import guru.springframework.springrecipeapp.repositories.RecipeRepository;
import guru.springframework.springrecipeapp.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IngredientService {

    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository uomRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    public IngredientService(RecipeRepository recipeRepository, UnitOfMeasureRepository uomRepository, IngredientToIngredientCommand converter, IngredientCommandToIngredient ingredientCommandToIngredient) {
        this.recipeRepository = recipeRepository;
        this.uomRepository = uomRepository;
        this.ingredientToIngredientCommand = converter;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
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
                .map(ingredientToIngredientCommand::convert);

        if(ingredientCommandOptional.isEmpty()) {
            throw new RuntimeException("Ingredient ID " + id + " not found");
        }

        return ingredientCommandOptional;
    }

    @Transactional
    public IngredientCommand saveCommand(IngredientCommand ingredientCommand) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(ingredientCommand.getRecipeId());
        if(optionalRecipe.isEmpty()) {
            throw new RuntimeException("Recipe ID " + ingredientCommand.getRecipeId() + " not found");
        }
        Recipe recipe = optionalRecipe.get();
        Optional<Ingredient> optionalIngredient = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                .findFirst();
        if(optionalIngredient.isEmpty()) {
            recipe.addIngredient(ingredientCommandToIngredient.convert(ingredientCommand));
        } else {
            Ingredient ingredient = optionalIngredient.get();
            ingredient.setDescription(ingredientCommand.getDescription());
            ingredient.setAmount(ingredientCommand.getAmount());
            ingredient.setUnitOfMeasure(uomRepository
                    .findById(ingredientCommand.getUnitOfMeasure().getId())
                    .orElseThrow(() -> new RuntimeException("UOM not found")));
        }
        Recipe savedRecipe = recipeRepository.save(recipe);

        Optional<Ingredient> savedIngredient = savedRecipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                .findFirst();
        if(savedIngredient.isEmpty()) {
            savedIngredient = savedRecipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getDescription().equals(ingredientCommand.getDescription()))
                    .filter(ingredient -> ingredient.getAmount().equals(ingredientCommand.getAmount()))
                    .filter(ingredient -> ingredient.getUnitOfMeasure().getId().equals(ingredientCommand.getUnitOfMeasure().getId()))
                    .findFirst();
        }
        return ingredientToIngredientCommand.convert(savedIngredient.get());
    }

    public void delete(Long id, Long recipeId) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
        if(optionalRecipe.isEmpty()) {
            throw new RuntimeException("Recipe ID " + recipeId + " not found");
        }
        Recipe recipe = optionalRecipe.get();
        Optional<Ingredient> optionalIngredient = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(id))
                .findFirst();
        if(optionalIngredient.isEmpty()) {
            throw new RuntimeException("Ingredient ID " + id + " not found");
        }
        recipe.setIngredients(recipe.getIngredients().stream()
                .filter(ingredient -> !ingredient.getId().equals(id))
                .collect(Collectors.toSet()));
        optionalIngredient.get().setRecipe(null);
        recipeRepository.save(recipe);
    }

}
