package com.github.drivingtest.server.domain.dto.statistics;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Statistics {
    Date date;
    int averageScore;
    int failedTests;
    int allTests;
}
