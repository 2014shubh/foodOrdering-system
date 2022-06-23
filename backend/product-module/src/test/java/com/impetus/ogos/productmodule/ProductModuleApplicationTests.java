package com.impetus.ogos.productmodule;

import com.impetus.ogos.productmodule.entity.Product;
import com.impetus.ogos.productmodule.repository.ProductRepository;
import com.impetus.ogos.productmodule.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.validateMockitoUsage;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductModuleApplicationTests {

// @Autowired
//	private ProductService productService;
//
// @MockBean
//	private ProductRepository productRepository;
	@Test
	void contextLoads() {
	}
//public void getDtoFromProductTest(){
//	when(productRepository.findAll()).thenReturn(Stream.of(new Product("bread","www.io",3,"bread description",2,"kg",1),new Product("panner","www.panner",4,"bread description",2,"kg",1)).collect(Collectors.toList()));

//}
}
