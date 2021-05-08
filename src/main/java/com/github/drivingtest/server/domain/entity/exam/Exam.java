package com.github.drivingtest.server.domain.entity.exam;

import com.github.drivingtest.server.domain.entity.CategoryEnum;
import com.github.drivingtest.server.security.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Exam")
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @OneToMany
    @JoinColumn(name = "EXAM_ID")
    List<ExamPrimaryTask> examPrimaryTasks;

    @OneToMany
    @JoinColumn(name = "EXAM_ID")
    List<ExamSpecialistTask> examSpecialistTasks;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    User user;

    int score;

    @Enumerated(EnumType.STRING)
    CategoryEnum category;

    Date date;
}
