package guru.springframework.springrecipeapp.services;

import guru.springframework.springrecipeapp.commands.RecipeCommand;
import guru.springframework.springrecipeapp.converters.RecipeCommandToRecipe;
import guru.springframework.springrecipeapp.converters.RecipeToRecipeCommand;
import guru.springframework.springrecipeapp.models.Recipe;
import guru.springframework.springrecipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RecipeService {

    private final RecipeRepository repository;
    private final RecipeToRecipeCommand recipeToRecipeCommand;
    private final RecipeCommandToRecipe recipeCommandToRecipe;

    public RecipeService(RecipeRepository repository, RecipeToRecipeCommand recipeToRecipeCommand, RecipeCommandToRecipe recipeCommandToRecipe) {
        this.repository = repository;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
    }

    public List<Recipe> getAll() {
        log.debug("I'm in the service");
        return (List<Recipe>) repository.findAll();
    }

    public Optional<Recipe> getById(Long id) {
        Optional<Recipe> optional = repository.findById(id);

        if(optional.isEmpty()) {
            throw new RuntimeException("Recipe not found");
        }

        return optional;
    }

    @Transactional
    public Optional<RecipeCommand> getCommandById(Long id) {
        return Optional.ofNullable(recipeToRecipeCommand.convert(repository.findById(id).orElse(null)));
    }

    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(recipeCommand);
        Recipe savedRecipe = repository.save(detachedRecipe);
        log.debug("Saved RecipeId: " + savedRecipe.getId());
        return recipeToRecipeCommand.convert(savedRecipe);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

}
