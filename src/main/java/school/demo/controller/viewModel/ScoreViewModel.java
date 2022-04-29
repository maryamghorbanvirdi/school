package school.demo.controller.viewModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreViewModel {

  private String Id;
  private String studentId;
  private String teacherId;
  private String courseId;
  private String score;
}
