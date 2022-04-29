package school.demo.controller.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import school.demo.controller.viewModel.ScoreViewModel;
import school.demo.domain.score.Score;

@Component
@Slf4j
public class ScoreViewModelConverter {

  public ScoreViewModel convert(Score score) {

    log.debug("enter convert() :: score={}", score);

    var scoreViewModel = new ScoreViewModel(score.getId(),
        score.getStudentId(),
        score.getTeacherId(),
        score.getCourseId(),
        score.getScore());

    log.debug("exit convert() :: scoreViewModel={}", scoreViewModel);

    return scoreViewModel;
  }
}
