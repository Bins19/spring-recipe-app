package guru.springframework.springrecipeapp.repositories;

import guru.springframework.springrecipeapp.models.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UnitOfMeasureRepositoryIT {

    @Autowired
    UnitOfMeasureRepository repository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findByDescription() {
        Optional<UnitOfMeasure> optional = repository.findByDescription("Teaspoon");
        assertEquals(optional.get().getDescription(), "Teaspoon");
    }

    @Test
    void findByDescriptionCup() {
        Optional<UnitOfMeasure> optional = repository.findByDescription("Cup");
        assertEquals(optional.get().getDescription(), "Cup");
    }
}