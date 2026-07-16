package com.ecommerce.project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity(name = "CATEGORIES")
public class Category{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // there are different generation types like auto,identity,sequence,table
    private Long categoryId;
    private String categoryName;


}