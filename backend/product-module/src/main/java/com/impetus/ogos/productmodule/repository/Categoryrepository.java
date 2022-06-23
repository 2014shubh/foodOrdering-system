package com.impetus.ogos.productmodule.repository;

import com.impetus.ogos.productmodule.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Categoryrepository extends JpaRepository<Category,Integer> {
    /**
     * @param categoryName
     * @return
     */
    Category findByCategoryName(String categoryName);

}
