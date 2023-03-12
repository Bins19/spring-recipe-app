package guru.springframework.springrecipeapp.services;

import guru.springframework.springrecipeapp.commands.IngredientCommand;
import guru.springframework.springrecipeapp.converters.IngredientCommandToIngredient;
import guru.springframework.springrecipeapp.converters.IngredientToIngredientCommand;
import guru.springframework.springrecipeapp.converters.UnitOfMeasureCommandToUnitOfMeasure;
import guru.springframework.springrecipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.springrecipeapp.models.Ingredient;
import guru.springframework.springrecipeapp.models.Recipe;
import guru.springframework.springrecipeapp.repositories.RecipeRepository;
import guru.springframework.springrecipeapp.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class IngredientServiceTest {

    IngredientService service;
    @Mock
    RecipeRepository recipeRepository;
    @Mock
    UnitOfMeasureRepository uomRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;


    public IngredientServiceTest() {
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new IngredientService(recipeRepository, uomRepository, ingredientToIngredientCommand, ingredientCommandToIngredient);
    }

    @Test
    void getByIdAndRecipeId() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(2L);
        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3L);
        recipe.addingIngredient(ingredient1);
        recipe.addingIngredient(ingredient2);
        recipe.addingIngredient(ingredient3);

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

        Optional<IngredientCommand> ingredientCommandOptional = service.getByIdAndRecipeId( 2L, 1L);

        assertEquals(2L, ingredientCommandOptional.get().getId());
        assertEquals(1L, ingredientCommandOptional.get().getRecipeId());
        verify(recipeRepository, times(1)).findById(anyLong());
    }

    @Test
    void saveCommand() {
        IngredientCommand command = new IngredientCommand();
        command.setId(1L);
        command.setRecipeId(1L);

        Optional<Recipe> optionalRecipe = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addingIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(1L);

        when(recipeRepository.findById(anyLong())).thenReturn(optionalRecipe);
        when(recipeRepository.save(any(Recipe.class))).thenReturn(savedRecipe);

        IngredientCommand savedCommand = service.saveCommand(command);

        assertEquals(1L, savedCommand.getId());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

}
