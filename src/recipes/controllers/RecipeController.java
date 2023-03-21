package recipes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import recipes.DTO.PostRecipeDTO;
import recipes.DTO.RecipeDTO;
import recipes.Exceptions.RecipeNotFoundException;
import recipes.Exceptions.ValidationException;
import recipes.Service.RecipeService;
import recipes.Validator.RecipeValidator;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;


@RestController
@RequestMapping("/api/recipe")
@Validated
public class RecipeController {
  @Autowired
  RecipeService recipeService;

  @GetMapping("/{id}")
  public RecipeDTO getRecipe(@PathVariable Long id) {
    if (Objects.isNull(id))
      throw new RecipeNotFoundException("");
    Optional<RecipeDTO> optional =
            recipeService.queryRecipe(id);
    if (optional.isEmpty())
      throw new RecipeNotFoundException("");
    return optional.get();
  }


  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteRecipe(@PathVariable Long id) {
    recipeService.deleteRecipe(id);
  }

  @PostMapping("/new")
  public PostRecipeDTO postRecipe(
          @Valid @RequestBody RecipeValidator recipeValidatorRes
  ) throws Exception {
    LocalDateTime date = LocalDateTime.now();
    RecipeDTO recipeDTO = new RecipeDTO(
            recipeValidatorRes.getName(),
            recipeValidatorRes.getDescription(),
            recipeValidatorRes.getCategory(),
            date,
            recipeValidatorRes.getIngredients(),
            recipeValidatorRes.getDirections()
    );
    var id = recipeService.saveRecipe(recipeDTO);
    return new PostRecipeDTO(id);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void putRecipe(
          @PathVariable Long id,
          @Valid @RequestBody RecipeValidator recipeValidatorRes
  ) throws Exception {
    recipeService.putRecipe(id, recipeValidatorRes);
  }

  @GetMapping("/search")
  public List<RecipeDTO> searchRecipes(
          @RequestParam Map<String, String> paramMap
  ) {
    RecipeController.NameOrCategory condition =
            checkSearchParams(paramMap);
    String param = condition ==
            RecipeController.NameOrCategory.NAME ?
            paramMap.getOrDefault("name","") :
            paramMap.getOrDefault("category","");
    return recipeService
            .searchRecipes(condition, param);
  }

  static private RecipeController.NameOrCategory checkSearchParams(Map<String, String> paramMap) {
    if (paramMap.size() != 1)
      throw new ValidationException("");
    RecipeController.NameOrCategory condition = null;
    String val = null;
    if (paramMap.containsKey("category")) {
      val = paramMap.get("category");
      condition = NameOrCategory.CATEGORY;
    } else if (paramMap.containsKey("name")) {
      val = paramMap.get("name");
      condition = NameOrCategory.NAME;
    }
    if (Objects.isNull(val))
      throw new ValidationException("");
    if (val.trim().length() == 0)
      throw new ValidationException("");
    return condition;
  }



  public enum NameOrCategory {
    NAME,
    CATEGORY
  }
}
