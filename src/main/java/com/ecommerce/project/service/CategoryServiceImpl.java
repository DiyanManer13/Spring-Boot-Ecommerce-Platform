package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements com.ecommerce.project.service.CategoryService {
    private long nextId=1L;
    private List<Category> categories =new ArrayList<>();

    @Override
    public List<Category> getAllCategories(){
        return categories;
    }

    @Override
    public void createCategory(Category category){
        category.setCategoryId(nextId++);
        categories.add(category);

    }

    @Override
    public String deleteCategory(Long categoryId){
        Category category = categories.stream() //Functional programing
                .filter(c -> c.getCategoryId().equals(categoryId))
                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource not found"));

        categories.remove(category);
        return "Category with CategoryId : "+categoryId+" deleted successfully";
    }
}