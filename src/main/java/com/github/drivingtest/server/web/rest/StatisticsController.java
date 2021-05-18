package com.github.drivingtest.server.web.rest;

import com.github.drivingtest.server.domain.dto.statistics.Statistics;
import com.github.drivingtest.server.service.StatisticsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StatisticsController {

    public static final String PATH_GET_EXAM_STATISTICS = "/exam/statistics";

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping(PATH_GET_EXAM_STATISTICS)
    public ResponseEntity<List<Statistics>> getExamStatistics() {
        return new ResponseEntity<>(statisticsService.getExamStatistics(), HttpStatus.OK);
    }
}
