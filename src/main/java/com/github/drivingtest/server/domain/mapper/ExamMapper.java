package com.github.drivingtest.server.domain.mapper;

import com.github.drivingtest.server.domain.dto.ExamResponse;
import com.github.drivingtest.server.domain.entity.Exam;
import org.springframework.stereotype.Component;

@Component
public class ExamMapper {
    public ExamResponse examToResponse(Exam exam) {
        return ExamResponse.builder()
                .examId(exam.getId())
                .primaryTaskList(exam.getExamPrimaryTasks())
                .specialistTaskList(exam.getExamSpecialistTasks())
                .build();
    }
}
