package com.github.drivingtest.server.domain.repository;

import com.github.drivingtest.server.domain.entity.Category;
import com.github.drivingtest.server.domain.entity.CategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, CategoryEnum> {
}
