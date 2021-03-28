package com.github.drivingtest.server.domain.dto.form.request;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class ExamFormSubmit implements Serializable {
    int examId;
    List<EFSPrimaryTask> primaryTaskList;
    List<EFSSpecialistTask> specialistTaskList;
}
