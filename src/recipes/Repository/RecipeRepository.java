package recipes.Repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import recipes.Entities.RecipeEntity;

import java.util.List;

@Repository
public interface RecipeRepository extends
        JpaRepository<RecipeEntity, Long> {
  List<RecipeEntity> findByCategoryIgnoreCase(String category, Sort sort);

  List<RecipeEntity> findByNameContainingIgnoreCase(String name, Sort sort);
}
