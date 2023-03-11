package guru.springframework.springrecipeapp.services;

import guru.springframework.springrecipeapp.converters.RecipeCommandToRecipe;
import guru.springframework.springrecipeapp.converters.RecipeToRecipeCommand;
import guru.springframework.springrecipeapp.models.Recipe;
import guru.springframework.springrecipeapp.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RecipeServiceTest {

    RecipeService service;
    @Mock
    RecipeRepository repository;
    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;
    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new RecipeService(repository, recipeToRecipeCommand, recipeCommandToRecipe);
    }

    @Test
    void getAll() {
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe());
        when(repository.findAll()).thenReturn(recipes);

        List<Recipe> recipeSet = service.getAll();

        assertEquals(recipeSet.size(), 1);
        verify(repository, times(1)).findAll();
    }

    @Test
    void getById() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(recipe));

        Optional<Recipe> recipeOptional = service.getById(1L);

        assertEquals(1L, recipeOptional.get().getId());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void deleteById() {
        Long id = 1L;
        service.deleteById(id);

        verify(repository, times(1)).deleteById(id);
    }

}