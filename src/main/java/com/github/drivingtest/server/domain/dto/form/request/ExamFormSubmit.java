package com.github.drivingtest.server.domain.dto.form.request;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@ToString
public class ExamFormSubmit implements Serializable {
    int examId;
    List<EFSPrimaryTask> primaryTaskList;
    List<EFSSpecialistTask> specialistTaskList;
}
