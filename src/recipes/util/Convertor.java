package recipes.util;


import recipes.DTO.RecipeDTO;
import recipes.Entities.DirectionEntity;
import recipes.Entities.IngredientEntity;
import recipes.Entities.RecipeEntity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Convertor {
  private static <T> T createInst(Constructor<T> Cons, String parameter) {
    try {
      return Cons.newInstance(parameter);
    } catch (Exception e) {
      return null;
    }
  }

  public static <T> List<T> toObjectList(List<String> list, Class<T> clazz) throws Exception {
    Constructor<T> Const = clazz.getConstructor(String.class);
    return list.stream()
            .map(s -> createInst(Const, s))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
  }

  public static RecipeDTO recipeEntity2DTO(RecipeEntity recipeEntity) {
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
    return new RecipeDTO(
            recipeEntity.getName(),
            recipeEntity.getDescription(),
            recipeEntity.getCategory(),
            recipeEntity.getDate(),
            ingredients,
            directions
    );
  }
}
