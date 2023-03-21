package recipes.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import recipes.Entities.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends
        JpaRepository<UserEntity, Long> {

  Optional<UserEntity> findTopDistinctByEmail(String email);

}