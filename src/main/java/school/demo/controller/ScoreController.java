package school.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import school.demo.controller.converter.ScoreCreateRequestConverter;
import school.demo.controller.converter.ScoreUpdateRequestConverter;
import school.demo.controller.converter.ScoreViewModelConverter;
import school.demo.controller.viewModel.ScoreCreateRequest;
import school.demo.controller.viewModel.ScoreUpdateRequest;
import school.demo.controller.viewModel.ScoreViewModel;
import school.demo.domain.score.Score;
import school.demo.service.score.ScoreService;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/v1/")
@Tags(value = {@Tag(name = "Scores Apis", description = "Ability to add, update and search Scores ")})
@ApiResponses(value = {@ApiResponse(responseCode = "400", description = "Bad request", content = @Content)})
public class ScoreController {

  private final ScoreService scoreService;
  private final ScoreCreateRequestConverter scoreCreateRequestConverter;
  private final ScoreUpdateRequestConverter scoreUpdateRequestConverter;
  private final ScoreViewModelConverter scoreViewModelConverter;
  private final PageableConverter pageableConverter;

  @PostMapping("/scores")
  @Operation(summary = "Create Score", description = "Ability to add a Score")
  @ApiResponses(value = {@ApiResponse(responseCode = "400", description = "Bad request", content = @Content)})
  public ResponseEntity<ScoreViewModel> createScore(@Valid @RequestBody ScoreCreateRequest scoreCreateRequest) throws Exception {

    log.info("enter createScore() :: categoryCreateRequest={}", scoreCreateRequest);

    var score = scoreService.createScore(scoreCreateRequestConverter.convert(scoreCreateRequest));

    var scoreViewModel = scoreViewModelConverter.convert(score);

    log.info("exit createScore() :: scoreViewModel={}", scoreViewModel);

    return ResponseEntity.status(HttpStatus.CREATED).body(scoreViewModel);
  }

  @PutMapping("/scores/{scoreId}/update")
  @Operation(summary = "Update Score", description = "Ability to update a Score")
  @ApiResponses(value = {@ApiResponse(responseCode = "400", description = "Bad request", content = @Content), @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)})
  public ResponseEntity<ScoreViewModel> updateScore(@PathVariable String scoreId, @Valid @RequestBody ScoreUpdateRequest scoreUpdateRequest) throws Exception {

    log.info("enter updateScore() :: scoreId={}, scoreUpdateRequest={}", scoreId, scoreUpdateRequest);

    var scoreViewModel = scoreService.editScore(scoreUpdateRequestConverter.convert(scoreId, scoreUpdateRequest))
        .filter(Score::isPresent)
        .map(scoreViewModelConverter::convert)
        .map(score -> ResponseEntity.status(HttpStatus.OK).body(score))
        .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    log.info("exit updateScore() :: scoreViewModel={}", scoreViewModel);
    return scoreViewModel;
  }


  @GetMapping("/scores")
  @Operation(summary = "Find All Scores", description = "Ability to search all Scores")
  @ApiResponses(value = {@ApiResponse(responseCode = "400", description = "Bad request", content = @Content)})
  public List<ScoreViewModel> getAllScores(
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "20") int size,
      @RequestParam(name = "isAsc", defaultValue = "true") boolean isAsc,
      @RequestParam(name = "propertiesName", defaultValue = "id") String propertiesName, @RequestParam String teacherId) throws Exception {

    log.info("enter getAllScores() :: page={}, size={}, isAsc={}, propertiesName={}, teacherId={}", page, size, isAsc, propertiesName, teacherId);

    var scoresViewModels = scoreService.getAllScores(pageableConverter.convert(page, size, isAsc, propertiesName), teacherId)
        .map(scoreViewModelConverter::convert)
        .getContent();

    log.info("enter getAllScores :: scoresViewModels={}", scoresViewModels);

    return scoresViewModels;
  }

  @GetMapping("/scores/{scoreId}")
  @Operation(summary ="Find a Score based on studentId", description = "Ability to search a Score")
  @ApiResponses(value = {@ApiResponse(responseCode = "400", description = "Bad request", content = @Content), @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)})
  public ResponseEntity<ScoreViewModel> getAScore(@PathVariable String studentId) {

    log.info("enter getAScore :: studentId={}", studentId);

    var scoreViewModel = scoreService.getACourse(studentId)
        .filter(Score::isPresent)
        .map(scoreViewModelConverter::convert)
        .map(score -> ResponseEntity.status(HttpStatus.OK).body(score))
        .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    log.info("enter getAScore() :: scoreViewModel={}", scoreViewModel);
    return scoreViewModel;
  }
}
