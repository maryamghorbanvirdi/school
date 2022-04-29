package school.demo.domain.score;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Score {

  @Id
  private String Id;
  private String studentId;
  private String teacherId;
  private String courseId;
  private String score;

  public Score(String studentId, String teacherId, String courseId, String score) {
    this.studentId = studentId;
    this.teacherId = teacherId;
    this.courseId = courseId;
    this.score = score;
  }

  public Score(String scoreId, String score) {
    this.Id = courseId;
    this.score = score;
  }

  public boolean isPresent() {
    return true;
  }
}
