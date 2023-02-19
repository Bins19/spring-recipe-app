package guru.springframework.springrecipeapp.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    Category category;

    @BeforeEach
    void setUp() {
        category = new Category();
    }

    @Test
    void getId() {
        category.setId(4L);
        assertEquals(category.getId(), 4L);
    }

    @Test
    void getDescription() {
    }

    @Test
    void getRecipes() {
    }
}