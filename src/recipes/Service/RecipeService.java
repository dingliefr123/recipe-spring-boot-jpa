package recipes.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.DTO.RecipeDTO;
import recipes.Entities.DirectionEntity;
import recipes.Entities.IngredientEntity;
import recipes.Entities.RecipeEntity;
import recipes.Exceptions.RecipeNotFoundException;
import recipes.Repository.RecipeRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeService {

  RecipeRepository recipeRepository;

  @Autowired
  RecipeService(RecipeRepository recipeRepository) {
    this.recipeRepository = recipeRepository;
  }

  @Transactional
  public Long saveRecipe(RecipeDTO recipeDTO) {
    final RecipeEntity recipeEntity = new RecipeEntity();
    recipeEntity.setDescription(recipeDTO.getDescription());
    recipeEntity.setName(recipeDTO.getName());
    List<IngredientEntity> ingredientEntities = recipeDTO
            .getIngredients()
            .stream()
            .map(IngredientEntity::new)
            .collect(Collectors.toList());
    recipeEntity.setIngredients(ingredientEntities);
    List<DirectionEntity> directionEntities = recipeDTO
            .getDirections()
            .stream()
            .map(DirectionEntity::new)
            .collect(Collectors.toList());
    recipeEntity.setDirections(directionEntities);

    RecipeEntity created =
            recipeRepository.save(recipeEntity);
    return created.getId();
  }

  public Optional<RecipeDTO> queryRecipe(Long id) {
    Optional<RecipeEntity> optional =
            recipeRepository.findById(id);
    if (optional.isEmpty()) {
      return Optional.empty();
    }
    RecipeEntity recipeEntity = optional.get();
    var ingredients = recipeEntity
            .getIngredients()
            .stream()
            .map(IngredientEntity::getIngredient)
            .collect(Collectors.toList());
    var directions = recipeEntity
            .getDirections()
            .stream()
            .map(DirectionEntity::getDirection)
            .collect(Collectors.toList());
    var dto = new RecipeDTO(
            recipeEntity.getName(),
            recipeEntity.getDescription(),
            ingredients,
            directions
    );
    return Optional.of(dto);
  }

  public void deleteRecipe(Long id) {
    if (Objects.isNull(id))
      throw new RecipeNotFoundException("");
    boolean exists = recipeRepository.existsById(id);
    if (!exists)
      throw new RecipeNotFoundException("");
    recipeRepository.deleteById(id);
  }

}
