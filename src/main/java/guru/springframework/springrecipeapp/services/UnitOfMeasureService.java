package guru.springframework.springrecipeapp.services;

import guru.springframework.springrecipeapp.commands.UnitOfMeasureCommand;
import guru.springframework.springrecipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.springrecipeapp.models.UnitOfMeasure;
import guru.springframework.springrecipeapp.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitOfMeasureService {

    private final UnitOfMeasureRepository repository;
    private final UnitOfMeasureToUnitOfMeasureCommand converter;

    public UnitOfMeasureService(UnitOfMeasureRepository repository, UnitOfMeasureToUnitOfMeasureCommand converter) {
        this.repository = repository;
        this.converter = converter;
    }

    public List<UnitOfMeasureCommand> getAll() {
        return ((List<UnitOfMeasure>) repository.findAll()).stream()
                .map(converter::convert)
                .toList();
    }

}
