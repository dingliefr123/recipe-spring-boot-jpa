package recipes.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.DTO.RegisterDTO;
import recipes.Entities.UserEntity;
import recipes.Exceptions.ValidationException;
import recipes.Repository.UserRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService {

  UserRepository userRepository;

  @Autowired
  UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Transactional
  public void register(RegisterDTO registerDTO) {
    var optional = findUser(registerDTO.getEmail());
    if (optional.isPresent())
      throw new ValidationException("");
    UserEntity userEntity = UserEntity
            .builder()
            .email(registerDTO.getEmail())
            .password(registerDTO.getPassword())
            .build();
    userRepository.save(userEntity);
  }

  Optional<UserEntity> findUser(String email) {
    return userRepository.findTopDistinctByEmail(email);
  }

}
