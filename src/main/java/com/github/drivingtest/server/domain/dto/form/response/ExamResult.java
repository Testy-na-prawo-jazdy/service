package com.github.drivingtest.server.domain.dto.form.response;

import com.github.drivingtest.server.domain.entity.exam.ExamPrimaryTask;
import com.github.drivingtest.server.domain.entity.exam.ExamSpecialistTask;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class ExamResult implements Serializable {
    int examId;
    List<ExamPrimaryTask> primaryTaskList;
    List<ExamSpecialistTask> specialistTaskList;
}
