package school.demo.repository.score;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import school.demo.domain.score.Score;

@Repository
public interface ScoreRepository extends MongoRepository<Score, String> {
  Page<Score> findAllById(Pageable pageable, String teacherId);

  Optional<Score> findByStudentId(String studentId);
}