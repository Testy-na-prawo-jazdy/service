package com.github.drivingtest.server.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Data
@Entity(name = "Category")
public class Category {
    @Id
    @Enumerated(EnumType.STRING)
    CategoryEnum category;
}
