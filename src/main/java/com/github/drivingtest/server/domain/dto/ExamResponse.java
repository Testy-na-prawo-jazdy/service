package com.github.drivingtest.server.domain.dto;

import com.github.drivingtest.server.domain.entity.ExamPrimaryTask;
import com.github.drivingtest.server.domain.entity.ExamSpecialistTask;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExamResponse {
    int examId;
    List<ExamPrimaryTask> primaryTaskList;
    List<ExamSpecialistTask> specialistTaskList;
}
