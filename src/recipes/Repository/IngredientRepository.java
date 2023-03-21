package recipes.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import recipes.Entities.IngredientEntity;

@Repository
public interface IngredientRepository extends
        JpaRepository<IngredientEntity, Long> {
}
