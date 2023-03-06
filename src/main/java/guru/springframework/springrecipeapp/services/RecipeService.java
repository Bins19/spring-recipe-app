package guru.springframework.springrecipeapp.services;

import guru.springframework.springrecipeapp.models.Recipe;
import guru.springframework.springrecipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RecipeService {

    private final RecipeRepository repository;

    public RecipeService(RecipeRepository repository) {
        this.repository = repository;
    }

    public List<Recipe> getAll() {
        log.debug("I'm in the service");
        return (List<Recipe>) repository.findAll();
    }

    public Optional<Recipe> getById(Long id) {
        return Optional.of(repository.findById(id)).orElse(null);
    }

}
