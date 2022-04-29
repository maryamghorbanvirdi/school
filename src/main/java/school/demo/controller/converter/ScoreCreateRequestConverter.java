package school.demo.controller.converter;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import school.demo.controller.viewModel.ScoreCreateRequest;
import school.demo.domain.score.Score;

@Component
@Slf4j
@AllArgsConstructor
public class ScoreCreateRequestConverter {

  public Score convert(ScoreCreateRequest createRequest) {

    log.debug("enter convert() :: scoreCreateRequest={}", createRequest);

    var score = new Score(createRequest.getStudentId(),
        createRequest.getTeacherId(),
        createRequest.getCourseId(),
        createRequest.getScore());

    log.debug("exit convert :: score={}", score);

    return score;
  }
}
