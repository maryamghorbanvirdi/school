package school.demo.controller.viewModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreCreateRequest {

  private String studentId;
  private String teacherId;
  private String courseId;
  private String score;
}
