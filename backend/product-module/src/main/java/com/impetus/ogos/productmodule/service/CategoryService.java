package com.impetus.ogos.productmodule.service;

import com.impetus.ogos.productmodule.entity.Category;
import com.impetus.ogos.productmodule.repository.Categoryrepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class CategoryService {
    private final Categoryrepository categoryrepository;

    public CategoryService(Categoryrepository categoryrepository) {
      //  log.info("CategoryService  method of CategoryService");
        this.categoryrepository = categoryrepository;
    }

    public List<Category> listCategories() {
    //    log.info(" listCategories method of CategoryService");
        return categoryrepository.findAll();
    }

    public void createCategory(Category category) {
     //   log.info("createCategory method of CategoryService");
        categoryrepository.save(category);
    }

    public Category readCategory(String categoryName) {
     //   log.info("readCategory method of CategoryService");
        return categoryrepository.findByCategoryName(categoryName);
    }

    public Optional<Category> readCategory(Integer categoryId) {
        return categoryrepository.findById(categoryId);
    }

    public void updateCategory(Integer categoryID, Category newCategory) {
     //   log.info("updateCategory method of CategoryService");
        Category category = categoryrepository.findById(categoryID).get();
        category.setCategoryName(newCategory.getCategoryName());
        category.setDescription(newCategory.getDescription());
        category.setProducts(newCategory.getProducts());


        categoryrepository.save(category);
    }
}
