package guru.springframework.springrecipeapp.repositories;

import guru.springframework.springrecipeapp.models.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
