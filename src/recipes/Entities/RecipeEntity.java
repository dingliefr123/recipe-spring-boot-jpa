package recipes.Entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
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

  @Column(name = "category")
  private String category;

  @Column(name = "date")
  private LocalDateTime date;

  @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
  @JoinColumn(
          name = "recipe_id",
          nullable = false,
          foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
  )
  private List<IngredientEntity> ingredients = new ArrayList<>();

  @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
  @JoinColumn(
          name = "recipe_id",
          nullable = false,
          foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
  )
  private List<DirectionEntity> directions = new ArrayList<>();
}
