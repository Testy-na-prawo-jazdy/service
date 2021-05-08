package com.github.drivingtest.server.domain.dto.form.request;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class EFSPrimaryTask {
    int id;
    boolean chosenAnswer;
}
