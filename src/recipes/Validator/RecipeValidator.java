package recipes.Validator;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class RecipeValidator {
  @NotBlank
  @NotNull
  private String name;

  @NotBlank
  @NotNull
  private String description;

  @NotNull
  @NotBlank
  private String category;

  @Size(min = 1)
  @NotNull
  private List<String> ingredients;

  @Size(min = 1)
  @NotNull
  private List<String> directions;
}
