package recipes.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import recipes.DTO.RecipeDTO;
import recipes.Entities.DirectionEntity;
import recipes.Entities.IngredientEntity;
import recipes.Entities.RecipeEntity;
import recipes.Exceptions.RecipeNotFoundException;
import recipes.Exceptions.RequestForbiddenException;
import recipes.Repository.DirectionRepository;
import recipes.Repository.IngredientRepository;
import recipes.Repository.RecipeRepository;
import recipes.Validator.RecipeValidator;
import recipes.controllers.RecipeController;
import recipes.util.Convertor;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeService {

  RecipeRepository recipeRepository;
  IngredientRepository ingredientRepository;
  DirectionRepository directionRepository;

  @Autowired
  RecipeService(RecipeRepository recipeRepository,
                IngredientRepository ingredientRepository,
                DirectionRepository directionRepository) {
    this.recipeRepository = recipeRepository;
    this.ingredientRepository = ingredientRepository;
    this.directionRepository = directionRepository;
  }

  @Transactional
  public Long saveRecipe(RecipeDTO recipeDTO, Long authorId) throws Exception {
    RecipeEntity recipeEntity =
            RecipeEntity.fromRecipeDTO(recipeDTO, authorId);
    return recipeRepository.save(recipeEntity).getId();
  }

  public Optional<RecipeDTO> queryRecipe(Long id) {
    Optional<RecipeEntity> optional =
            recipeRepository.findById(id);
    if (optional.isEmpty()) {
      return Optional.empty();
    }

    return Optional.of(Convertor
            .recipeEntity2DTO(optional.get()));
  }

  public List<RecipeDTO> searchRecipes(
          RecipeController.NameOrCategory condition,
          String param) {
    List<RecipeEntity> l;
    Sort sort = Sort.by("date").descending();
    if (condition == RecipeController.NameOrCategory.NAME) {
      l = recipeRepository.findByNameContainingIgnoreCase(param, sort);
    } else {
      l = recipeRepository.findByCategoryIgnoreCase(param, sort);
    }
    if (Objects.isNull(l) || l.size() == 0)
      return List.of();
    return l
            .stream()
            .map(Convertor::recipeEntity2DTO)
            .collect(Collectors.toList());
  }

  public void deleteRecipe(Long id, Long userId) {
    if (Objects.isNull(id))
      throw new RecipeNotFoundException("");
    Optional<RecipeEntity> optional = recipeRepository.findById(id);
    if (optional.isEmpty())
      throw new RecipeNotFoundException("");
    checkCurrentUserIsAuthor(optional.get(), userId);
    recipeRepository.deleteById(id);
  }

  @Transactional
  public void putRecipe(Long id, RecipeValidator validated, Long userId) throws Exception {
    if (Objects.isNull(id))
      throw new RecipeNotFoundException("");
    Optional<RecipeEntity> optional =
            recipeRepository.findById(id);
    if (optional.isEmpty())
      throw new RecipeNotFoundException("");

    RecipeEntity recipeEntity = optional.get();

    checkCurrentUserIsAuthor(recipeEntity, userId);

    // Remove the mapped objects
    clearMapped(recipeEntity);


    List<IngredientEntity> ingredientEntities =
            Convertor.toObjectList(validated.getIngredients(), IngredientEntity.class);
    List<DirectionEntity> directionEntities =
            Convertor.toObjectList(validated.getDirections(), DirectionEntity.class);
    recipeEntity.setName(validated.getName());
    recipeEntity.setDescription(validated.getDescription());
    recipeEntity.setCategory(validated.getCategory());
    recipeEntity.setDate(LocalDateTime.now());
    recipeEntity.setDirections(directionEntities);
    recipeEntity.setIngredients(ingredientEntities);

    recipeRepository.save(recipeEntity);
  }


  private void checkCurrentUserIsAuthor(RecipeEntity recipe, Long userId) {
    try {
      if (!userId.equals(recipe.getAuthorId()))
        throw new RequestForbiddenException("");
    } catch (Exception e) {
      throw new RequestForbiddenException("");
    }
  }

  private void clearMapped(RecipeEntity recipeEntity) {
    List<Long> ingredientIds = recipeEntity.getIngredients()
            .stream()
            .map(IngredientEntity::getId)
            .collect(Collectors.toList());
    List<Long> directionIds = recipeEntity.getDirections()
            .stream()
            .map(DirectionEntity::getId)
            .collect(Collectors.toList());
    if (ingredientIds.size() > 0)
      ingredientRepository.deleteAllById(ingredientIds);
    if (directionIds.size() > 0)
      directionRepository.deleteAllById(directionIds);
    ;
  }

}
