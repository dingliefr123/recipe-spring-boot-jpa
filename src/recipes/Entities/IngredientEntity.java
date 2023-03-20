package recipes.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ingredient")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientEntity {
  @Id
  @GeneratedValue
  @Column(name = "ingredient_id")
  private Long id;

  @Column(name = "ingredient")
  private String ingredient;

  public IngredientEntity (String ingredient) {
    this.ingredient = ingredient;
  }
}
