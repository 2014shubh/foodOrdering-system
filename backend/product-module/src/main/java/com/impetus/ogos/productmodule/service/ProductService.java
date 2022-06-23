package com.impetus.ogos.productmodule.service;

import com.impetus.ogos.productmodule.Dto.ProductDto;
import com.impetus.ogos.productmodule.Dto.ProductListDto;
import com.impetus.ogos.productmodule.entity.Category;
import com.impetus.ogos.productmodule.entity.Product;
import com.impetus.ogos.productmodule.exception.ProductNotExistException;
import com.impetus.ogos.productmodule.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
	private RestTemplate restTemplate;


    /**
     * @return list of ProductDto
     *
     * This function fetches the list of all products from repository
     * then prepares a list of productDtos from that and returns the list.
     *
     */

    public ProductListDto listProducts(Optional<Integer> page,Optional<String> sortBy) {
     //   log.info("listProducts method of ProductService");
        int currentPage=0;
        if(page.get()!=null) {
            currentPage=page.get();
        }
        Page<Product> products = productRepository.findAll(PageRequest.of(page.orElse(0),5,
                Sort.Direction.ASC,sortBy.orElse("id")));


        List<ProductDto> productDtos = new ArrayList<>();
        for(Product product : products) {
            ProductDto productDto = getDtoFromProduct(product);
            productDtos.add(productDto);
        }
        return new ProductListDto(productDtos, products.getTotalPages(),currentPage);
    }



    /**
     * @param product
     * @return ProductDto
     *
     * This function returns corresponding ProductDto from the Product object passed as argument.
     *
     */

    public static ProductDto getDtoFromProduct(Product product) {
   //     log.info(" getDtoFromProduct method of ProductService");
        ProductDto productDto = new ProductDto(product);
        return productDto;
    }

    /**
     * @param productDto
     * @param category
     * @return Product
     *
     * This function returns corresponding Product object from ProductDto and category objects passed as arguments.
     * The returned product object will contain the Category passed in argument.
     *
     */
    public static Product getProductFromDto(ProductDto productDto, Category category) {
    //    log.info(" getProductFromDto method of ProductService");
        Product product = new Product(productDto,category);
        return product;
    }

    /**
     * @param productDto
     * @param category
     *
     * This function adds a new product into the database.
     *
     */
    public void addProduct(ProductDto productDto, Category category) {
   //     log.info(" addProduct method of ProductService");
    	
        Product product = getProductFromDto(productDto, category);
       
        productRepository.save(product);
        
        Product savedProduct = productRepository.findByName(product.getName()).get(0);
        
        try {
			restTemplate.postForEntity(new URI("lb://inventory-service/inventory/add-product/"+Integer.toString(savedProduct.getId())), null, null);
		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
    }

    /**
     * @param productID
     * @param productDto
     * @param category
     *
     * This function updates the product in database with a given productID passed as argument.
     * It takes ProductDto and category as argument, constructs a new product from that
     * then updates it into database with given productId.
     *
     */
    public void updateProduct(Integer productID, ProductDto productDto, Category category) {
    //    log.info("updateProduct  method of ProductService");
        Product product = getProductFromDto(productDto, category);
        product.setId(productID);
        productRepository.save(product);
    }


    /**
     * @param productId
     * @return
     * @throws ProductNotExistException
     *
     * This function returns a Product having given productId passed in argument.
     * If the product with that productId doesn't exist in database it returns ProductNotExistException.
     *
     */
    public Product getProductById(Integer productId) throws ProductNotExistException {
    //    log.info("inside getProductById method of ProductService");
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (!optionalProduct.isPresent())
            throw new ProductNotExistException("Product id is invalid " + productId);
        return optionalProduct.get();
    }
}
