package recipes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recipes.DTO.DeleteSuccessResult;
import recipes.DTO.PostRecipeDTO;
import recipes.DTO.RecipeDTO;
import recipes.Exceptions.RecipeNotFoundException;
import recipes.Exceptions.ValidationException;
import recipes.Service.RecipeService;
import recipes.Validator.RecipeValidator;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/recipe")
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
  public ResponseEntity<String> deleteRecipe(@PathVariable Long id) {
    recipeService.deleteRecipe(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PostMapping("/new")
  public PostRecipeDTO postRecipe(
          @Valid @RequestBody RecipeValidator recipeValidatorRes
  ) {
    RecipeDTO recipeDTO = new RecipeDTO(
            recipeValidatorRes.getName(),
            recipeValidatorRes.getDescription(),
            recipeValidatorRes.getIngredients(),
            recipeValidatorRes.getDirections()
    );
    var id = recipeService.saveRecipe(recipeDTO);
    return new PostRecipeDTO(id);
  }
}
