package com.impetus.ogos.productmodule.controller;

import com.impetus.ogos.productmodule.Dto.ProductDto;
import com.impetus.ogos.productmodule.Dto.ProductListDto;
import com.impetus.ogos.productmodule.common.ApiResponse;
import com.impetus.ogos.productmodule.entity.Category;
import com.impetus.ogos.productmodule.entity.Product;
import com.impetus.ogos.productmodule.repository.ProductRepository;
import com.impetus.ogos.productmodule.service.CategoryService;
import com.impetus.ogos.productmodule.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")

@Slf4j
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductRepository productRepository;


 @GetMapping("/all")

 public ResponseEntity<ProductListDto> getProducts(@RequestParam Optional<Integer> page,@RequestParam Optional<String> sortBy) {
    // log.info("getProducts method of ProductContoller");
     ProductListDto body = productService.listProducts(page,sortBy);
     return new ResponseEntity<ProductListDto>(body, HttpStatus.OK);
 }


    @PostMapping("/all")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody ProductDto productDto) {
      //  log.info(" addProduct method of ProductContoller");
        Optional<Category> optionalCategory = categoryService.readCategory(productDto.getCategoryId());
        if (!optionalCategory.isPresent()) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "category is invalid"), HttpStatus.CONFLICT);
        }
        Category category = optionalCategory.get();
        productService.addProduct(productDto, category);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Product has been added"), HttpStatus.CREATED);
    }

    @GetMapping("/get-product/{productId}")
    public ProductDto getProduct(@PathVariable("productId") String productId)
    {
        Integer id = Integer.parseInt(productId);
        return ProductService.getDtoFromProduct(productService.getProductById(id));
    }
   @PostMapping("/update/{productID}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productID") Integer productID, @RequestBody @Validated ProductDto productDto) {
    //   log.info(" UpdateProduct method of ProductContoller");
       Optional<Category> optionalCategory = categoryService.readCategory(productDto.getCategoryId());
        if (!optionalCategory.isPresent()) {
           return new ResponseEntity<ApiResponse>(new ApiResponse(false, "category is invalid"), HttpStatus.CONFLICT);
       }
      Category category = optionalCategory.get();
        productService.updateProduct(productID, productDto, category);
      return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Product has been updated"), HttpStatus.OK);
   }


   @GetMapping("/search/{query}")
    public ResponseEntity<?> search(@PathVariable("query")String query) {
       List<Product> products = this.productRepository.findByName(query);
       return ResponseEntity.ok(products);

   }
}
