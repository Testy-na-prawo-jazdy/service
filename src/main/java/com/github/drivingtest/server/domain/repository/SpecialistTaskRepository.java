package com.github.drivingtest.server.domain.repository;

import com.github.drivingtest.server.domain.entity.CategoryEnum;
import com.github.drivingtest.server.domain.entity.SpecialistTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialistTaskRepository extends JpaRepository<SpecialistTask, Integer> {
    @Query(value = "select pt from SpecialistTask pt inner join pt.categories c where c.category = :#{#category} order by function('RAND')")
    List<SpecialistTask> findSpecialistTasksByCategoriesCategory(CategoryEnum category);
}
