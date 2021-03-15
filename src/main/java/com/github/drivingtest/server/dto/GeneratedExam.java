package com.github.drivingtest.server.dto;

import com.github.drivingtest.server.entity.ExamPrimaryTask;
import com.github.drivingtest.server.entity.ExamSpecialistTask;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GeneratedExam {
    int examId;
    List<ExamPrimaryTask> primaryTaskList;
    List<ExamSpecialistTask> specialistTaskList;
}
