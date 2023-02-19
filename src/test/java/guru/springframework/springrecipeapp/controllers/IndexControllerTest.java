package guru.springframework.springrecipeapp.controllers;

import guru.springframework.springrecipeapp.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class IndexControllerTest {

    IndexController controller;
    @Mock
    RecipeService recipeService;
    @Mock
    Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new IndexController(recipeService);
    }

    @Test
    void getIndexPage() {
        String index = controller.getIndexPage(model);

        assertEquals(index, "index");
        verify(model, times(1)).addAttribute(eq("recipes"), anyList());
        verify(recipeService, times(1)).getAll();
    }

}