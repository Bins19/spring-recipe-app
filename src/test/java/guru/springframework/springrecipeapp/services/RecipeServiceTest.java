package guru.springframework.springrecipeapp.services;

import guru.springframework.springrecipeapp.models.Recipe;
import guru.springframework.springrecipeapp.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RecipeServiceTest {

    RecipeService service;
    @Mock
    RecipeRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new RecipeService(repository);
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
}