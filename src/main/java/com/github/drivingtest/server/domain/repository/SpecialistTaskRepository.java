package com.github.drivingtest.server.domain.repository;

import com.github.drivingtest.server.domain.entity.CategoryEnum;
import com.github.drivingtest.server.domain.entity.SpecialistTask;
import com.github.drivingtest.server.security.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialistTaskRepository extends JpaRepository<SpecialistTask, Integer> {
    @Query(value = "select st from SpecialistTask st inner join st.categories c where c.category = :#{#category} order by function('RAND')")
    List<SpecialistTask> findSpecialistTasksByCategoriesCategory(CategoryEnum category);

    @Query(value = "select st from SpecialistTask st inner join st.categories c where c.category =:#{#category} and st.id not in (select lpt.specialistTask.id from LearnSpecialistTaskDetails lpt where lpt.user = :#{#user} and lpt.isCorrect = true)")
    List<SpecialistTask> findUniqueSpecialistTasksByCategoriesCategory(CategoryEnum category, User user);
}
