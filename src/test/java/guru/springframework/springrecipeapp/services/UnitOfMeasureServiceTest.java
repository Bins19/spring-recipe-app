package guru.springframework.springrecipeapp.services;

import guru.springframework.springrecipeapp.commands.UnitOfMeasureCommand;
import guru.springframework.springrecipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.springrecipeapp.models.UnitOfMeasure;
import guru.springframework.springrecipeapp.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UnitOfMeasureServiceTest {

    UnitOfMeasureService service;
    @Mock
    UnitOfMeasureRepository repository;
    private final UnitOfMeasureToUnitOfMeasureCommand converter;

    UnitOfMeasureServiceTest() {
        this.converter = new UnitOfMeasureToUnitOfMeasureCommand();
    }


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new UnitOfMeasureService(repository, converter);
    }

    @Test
    void getAll() {
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setId(1L);
        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setId(2L);
        when(repository.findAll()).thenReturn(Arrays.asList(uom1, uom2));

        List<UnitOfMeasureCommand> uomList = service.getAll();

        assertEquals(2, uomList.size());
        assertEquals(1L, uomList.get(0).getId());
        assertEquals(2L, uomList.get(1).getId());
    }

}
