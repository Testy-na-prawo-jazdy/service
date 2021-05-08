package com.github.drivingtest.server.domain.dto.history.response;

import com.github.drivingtest.server.domain.entity.CategoryEnum;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
public class ExamHistory implements Serializable {
    int examId;
    int score;
    CategoryEnum category;
    Date date;
}
