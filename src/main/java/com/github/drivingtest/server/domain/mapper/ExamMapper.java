package com.github.drivingtest.server.domain.mapper;

import com.github.drivingtest.server.domain.dto.form.response.ExamForm;
import com.github.drivingtest.server.domain.dto.form.response.ExamResult;
import com.github.drivingtest.server.domain.dto.history.ExamHistoryResponse;
import com.github.drivingtest.server.domain.entity.exam.Exam;
import org.springframework.stereotype.Component;

@Component
public class ExamMapper {
    public ExamForm examToExamForm(Exam exam) {
        return ExamForm.builder()
                .examId(exam.getId())
                .primaryTaskList(exam.getExamPrimaryTasks())
                .specialistTaskList(exam.getExamSpecialistTasks())
                .build();
    }

    public ExamResult examToExamResult(Exam exam) {
        return ExamResult.builder()
                .examId(exam.getId())
                .primaryTaskList(exam.getExamPrimaryTasks())
                .specialistTaskList(exam.getExamSpecialistTasks())
                .build();
    }

    public ExamHistoryResponse examToExamHistory(Exam exam) {
        return ExamHistoryResponse.builder()
                .examId(exam.getId())
                .category(exam.getCategory())
                .date(exam.getDate())
                .score(exam.getScore())
                .build();
    }
}
