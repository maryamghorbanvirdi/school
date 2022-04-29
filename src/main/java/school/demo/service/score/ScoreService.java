package school.demo.service.score;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.demo.config.Constants;
import school.demo.domain.score.Score;
import school.demo.repository.score.ScoreRepository;
import tokenparser.security.TokenExtract;

@Service
@Transactional
@Slf4j
public class ScoreService {

  private final ScoreRepository scoreRepository;
  private final TokenExtract tokenExtract;

  public ScoreService(ScoreRepository scoreRepository) {

    this.scoreRepository = scoreRepository;
    this.tokenExtract = new TokenExtract("navaco", "teacher");
  }

  public Score createScore(Score score) throws Exception {

    if (tokenExtract.hasAttribute(Constants.ADD_COURSE)) {

      log.info("enter createScore() :: score={}", score);

      var savedScore = scoreRepository.save(score);

      log.info("exit savedScore() :: savedScore={}", savedScore);

      return savedScore;
    }
    throw new Exception("You Are not Allowed for This Action");
  }

  public Optional<Score> editScore(Score score) throws Exception {

    if (tokenExtract.hasAttribute(Constants.EDIT_COURSE)) {

        log.info("enter editScore() :: score={}", score);

        var optionalScore =  scoreRepository.findById(score.getId());

        if (optionalScore.isPresent()) {
          var updated = optionalScore.get();
          score.setScore(updated.getScore());

          log.info("exit editScore() :: score={}", score);

          return Optional.of(scoreRepository.save(score));
        }
        throw new Exception("Score Does Not Exist");
    }
    throw new Exception("You Are not Allowed for This Action");
  }

  public Page<Score> getAllScores(Pageable pageable, String teacherId) throws Exception {

    if (tokenExtract.hasAttribute(Constants.GET_ALL_COURSE)) {

      log.info("enter getAllScores() :: pageable={}, teacherId={}", pageable, teacherId);

      var scores = scoreRepository.findAllById(pageable, teacherId);

      log.info("enter getAllCourses() :: courses={}", scores);

      return scores;
    }
    throw new Exception("You Are not Allowed for This Action");
  }

  public Optional<Score> getACourse(String studentId) {

    log.info("enter getACourse() :: studentId={}", studentId);

    var score = scoreRepository.findByStudentId(studentId);

    log.info("enter getACourse() :: score={}", score);

    return score;
  }
}
