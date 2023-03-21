package recipes.Entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import recipes.DTO.RecipeDTO;
import recipes.util.Convertor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recipe")
@Builder
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

  @Column(name = "user_id")
  private Long authorId;


  public static RecipeEntity fromRecipeDTO(RecipeDTO recipeDTO, Long authorId) throws Exception {
    List<IngredientEntity> ingredientEntities =
            Convertor.toObjectList(recipeDTO.getIngredients(), IngredientEntity.class);
    List<DirectionEntity> directionEntities =
            Convertor.toObjectList(recipeDTO.getDirections(), DirectionEntity.class);
    return RecipeEntity.builder()
            .description(recipeDTO.getDescription())
            .name(recipeDTO.getName())
            .category(recipeDTO.getCategory())
            .date(recipeDTO.getDate())
            .ingredients(ingredientEntities)
            .directions(directionEntities)
            .authorId(authorId)
            .build();
  }

}
