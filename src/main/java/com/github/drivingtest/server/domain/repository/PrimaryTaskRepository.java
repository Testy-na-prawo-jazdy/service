package com.github.drivingtest.server.domain.repository;

import com.github.drivingtest.server.domain.entity.CategoryEnum;
import com.github.drivingtest.server.domain.entity.PrimaryTask;
import com.github.drivingtest.server.security.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrimaryTaskRepository extends JpaRepository<PrimaryTask, Integer> {
    @Query(value = "select pt from PrimaryTask pt inner join pt.categories c where c.category = :#{#category} order by function('RAND')")
    List<PrimaryTask> findPrimaryTasksByCategoriesCategory(@Param("category") CategoryEnum category);

    @Query(value = "select pt from PrimaryTask pt inner join pt.categories c where c.category = :#{#category} order by function('RAND')")
    List<PrimaryTask> findUniquePrimaryTasksByCategoriesCategory(@Param("category") CategoryEnum category, @Param("user") User user);
}
