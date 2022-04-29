package school.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PageableConverter {
  public org.springframework.data.domain.Pageable convert(int page, int size, boolean isAsc, String propertiesName) {

    log.debug("enter convert() :: page={}, size={}, isAsc={}, propertiesName={}", page, size, isAsc, propertiesName);

    var pageable = PageRequest.of(page, size, isAsc ? Direction.ASC : Direction.DESC, propertiesName);

    log.debug("enter convert() :: pageable={}", pageable);

    return pageable;
  }
}
