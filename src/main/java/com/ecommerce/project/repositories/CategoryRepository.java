package com.ecommerce.project.repositories;

import com.ecommerce.project.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> { //crudRepo gives pre generated crud operations like findAll find Delete etc
    //Jpa repository is also bigger version of crudrepo in this repo we gave to parameter like category and long it means
    // the <entity, type of primary key>


}