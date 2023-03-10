package guru.springframework.springrecipeapp.models;

import jakarta.persistence.*;
import lombok.*;

@Data
@EqualsAndHashCode(exclude = "recipe")
@ToString(exclude = "recipe")
@Entity
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Recipe recipe;
    @Lob
    private String recipeNotes;

}
