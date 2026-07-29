package com.ecommerce.project.repositories;

import com.ecommerce.project.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findByCategoryName(@NotBlank(message = "Category name required") @Size(min = 3,message = "Category Name must contain atleast 5 character") String categoryName);
    //crudRepo gives pre generated crud operations like findAll find Delete etc
    //Jpa repository is also bigger version of crudrepo in this repo we gave to parameter like category and long it means
    // the <entity, type of primary key>
}