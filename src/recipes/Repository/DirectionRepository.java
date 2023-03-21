package recipes.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import recipes.Entities.DirectionEntity;

@Repository
public interface DirectionRepository extends
        JpaRepository<DirectionEntity, Long> {
}
