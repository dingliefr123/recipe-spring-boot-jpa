package recipes.Entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recipe")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "recipe_id")
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @OneToMany(cascade = CascadeType.PERSIST)
  @JoinColumn(
          name = "recipe_id",
          nullable = false,
          foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
  )
  private List<IngredientEntity> ingredients = new ArrayList<>();

  @OneToMany(cascade = CascadeType.PERSIST)
  @JoinColumn(
          name = "recipe_id",
          nullable = false,
          foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
  )
  private List<DirectionEntity> directions = new ArrayList<>();
}
