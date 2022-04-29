package school.demo.controller.converter;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import school.demo.controller.viewModel.ScoreUpdateRequest;
import school.demo.domain.score.Score;

@Component
@Slf4j
@AllArgsConstructor
public class ScoreUpdateRequestConverter {

  public Score convert(String scoreId, ScoreUpdateRequest updateRequest) {

    log.debug("enter convert() :: scoreId={}, scoreUpdateRequest={}", scoreId, updateRequest);

    var score = new Score(scoreId, updateRequest.getScore());

    log.debug("exit convert :: score={}", score);

    return score;
  }
}
