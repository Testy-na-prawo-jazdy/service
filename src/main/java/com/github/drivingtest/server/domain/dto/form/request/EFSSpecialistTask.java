package com.github.drivingtest.server.domain.dto.form.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EFSSpecialistTask {
    int id;
    String chosenAnswer;
}
