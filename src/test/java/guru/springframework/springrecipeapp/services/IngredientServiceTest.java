package guru.springframework.springrecipeapp.services;

import guru.springframework.springrecipeapp.commands.IngredientCommand;
import guru.springframework.springrecipeapp.converters.IngredientToIngredientCommand;
import guru.springframework.springrecipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.springrecipeapp.models.Ingredient;
import guru.springframework.springrecipeapp.models.Recipe;
import guru.springframework.springrecipeapp.repositories.RecipeRepository;
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
    private final IngredientToIngredientCommand converter;

    public IngredientServiceTest() {
        this.converter = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new IngredientService(recipeRepository, converter);
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

}
