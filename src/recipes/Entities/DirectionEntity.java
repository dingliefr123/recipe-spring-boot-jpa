package recipes.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "direction")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DirectionEntity {
  @Id
  @GeneratedValue
  @Column(name = "direction_id")
  private Long id;

  @Column(name = "direction")
  private String direction;

  public DirectionEntity (String direction) {
    this.direction = direction;
  }
}
