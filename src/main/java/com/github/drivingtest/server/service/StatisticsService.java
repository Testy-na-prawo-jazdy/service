package com.github.drivingtest.server.service;

import com.github.drivingtest.server.domain.dto.statistics.Statistics;
import com.github.drivingtest.server.domain.entity.exam.Exam;
import com.github.drivingtest.server.domain.repository.exam.ExamRepository;
import com.github.drivingtest.server.security.domain.entity.User;
import com.github.drivingtest.server.security.service.AuthService;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Service
public class StatisticsService {

    private final AuthService authService;
    private final ExamRepository examRepository;

    public StatisticsService(AuthService authService, ExamRepository examRepository) {
        this.authService = authService;
        this.examRepository = examRepository;
    }

    public List<Statistics> getExamStatistics() {
        User user = authService.getLoggedUser();
        Date date = Date.from(ZonedDateTime.now().minusMonths(1).toInstant());

        List<Exam> examList = examRepository.findAllByUserAndDateAfter(user, date);
        Map<Date, List<Exam>> examListByDay = examList.stream().collect(groupingBy(d -> Date.from(d.getDate().toInstant().truncatedTo(ChronoUnit.DAYS))));

        List<Statistics> statisticsList = new ArrayList<>();
        for (Map.Entry<Date, List<Exam>> entry : examListByDay.entrySet()) {
            List<Exam> list = entry.getValue();

            int allTests = list.size();
            int averageScore = list.stream().map(Exam::getScore).reduce(0, Integer::sum) / allTests;
            int failedTests = (int) list.stream().filter(exam -> exam.getScore() < 68).count();

            Statistics statistics = Statistics.builder()
                    .date(entry.getKey())
                    .averageScore(averageScore)
                    .allTests(allTests)
                    .failedTests(failedTests)
                    .build();

            statisticsList.add(statistics);
        }

        return statisticsList;
    }
}
