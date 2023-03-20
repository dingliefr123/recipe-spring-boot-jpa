package recipes.controllers;

import org.springframework.web.bind.annotation.*;
import recipes.DTO.PostRecipeDTO;
import recipes.DTO.RecipeDTO;
import recipes.Exceptions.RecipeNotFoundException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RecipeController {
  static List<RecipeDTO> recipes = new ArrayList<>();

  @GetMapping("/api/recipe/{id}")
  public RecipeDTO getRecipe(@PathVariable int id) {
    int _id = id - 1;
    if (_id < 0 || _id >= recipes.size() )
      throw new RecipeNotFoundException("");
    return recipes.get(_id);
  }

  @PostMapping("/api/recipe/new")
  public PostRecipeDTO postRecipe(@RequestBody RecipeDTO _recipe) {
    recipes.add(_recipe);
    return new PostRecipeDTO(recipes.size());
  }
}
