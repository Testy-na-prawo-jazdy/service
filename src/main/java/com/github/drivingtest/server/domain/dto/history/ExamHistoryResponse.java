package com.github.drivingtest.server.domain.dto.history;

import com.github.drivingtest.server.domain.entity.CategoryEnum;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
public class ExamHistoryResponse implements Serializable {
    int examId;
    int score;
    CategoryEnum category;
    Date date;
}
