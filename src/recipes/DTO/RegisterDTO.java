package recipes.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
  @NotNull
  @Email(regexp = "\\w+@\\w+\\.\\w+")
  private String email;

  @Size(min = 8)
  @NotNull
  @NotBlank
  private String password;
}
