package com.github.drivingtest.server.repository;

import com.github.drivingtest.server.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
