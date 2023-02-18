package guru.springframework.springrecipeapp.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = "recipes")
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @ManyToMany(mappedBy = "categories")
    @ToString.Exclude
    private Set<Recipe> recipes;

}
