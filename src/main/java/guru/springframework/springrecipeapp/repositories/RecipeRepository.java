package guru.springframework.springrecipeapp.repositories;

import guru.springframework.springrecipeapp.models.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
