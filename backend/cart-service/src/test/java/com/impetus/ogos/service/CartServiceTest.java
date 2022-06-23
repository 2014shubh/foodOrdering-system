package com.impetus.ogos.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.impetus.ogos.dto.InventoryItemDto;
import com.impetus.ogos.dto.ProductDto;
import com.impetus.ogos.exception.CartQuantityException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.impetus.ogos.model.Cart;
import com.impetus.ogos.model.CartItem;
import com.impetus.ogos.repository.CartItemRepository;
import com.impetus.ogos.repository.CartRepository;
import com.razorpay.RazorpayException;

@SpringBootTest
class CartServiceTest {

	@Autowired
	private CartService cartService;

	@MockBean
	private CartRepository cartRepository;
	
	@MockBean
	private CartItemRepository itemRepository;
	
	@MockBean
	private RestTemplate restTemplate;


	@Test
	 void deleteCart() {
		String userId = "B$00002";
		Cart cart = new Cart("B$00002", "INDORE", 0, 0);
		CartItem cartItem = new CartItem(1, 1, 10);
		cartItem.setCart(cart);
		cart.setCartItems(Stream.of(cartItem).collect(Collectors.toList()));
		when(cartRepository.findById(userId)).thenReturn(Optional.of(cart));
		cartService.deleteCart(userId);
		verify(cartRepository, times(1)).delete(cart);

	}

	@Test
	 void changeInventory() {
		String userId = "B$00002";
		String inventoryId = "Ujjain";
		Cart cart = new Cart("B$00002", "INDORE", 0, 0);
		cart.setInventoryId(inventoryId);
		when(cartRepository.findById(userId)).thenReturn(Optional.of(cart));
		cartService.changeInventory(userId, inventoryId);
		verify(cartRepository, times(1)).save(cart);
	}


	@Test
	 void deleteAllItems() {
		String userId = "B$00002";
		Cart cart = new Cart("B$00002", "INDORE", 0, 0);
		when(cartRepository.findById(userId)).thenReturn(Optional.of(cart));
		cartService.deleteAllItems(userId);
		verify(cartRepository, times(1)).delete(cart);
	}

	@Test
	 void deleteItem() {
		String userId = "B$00002";
		int productId=1;
		Cart cart = new Cart("B$00002", "INDORE", 0, 0);
		when(cartRepository.findById(userId)).thenReturn(Optional.of(cart));
		
		List<CartItem> cartItems=Stream.of(new CartItem(1,1,1.5)).collect(Collectors.toList());
		when(itemRepository.findByCartAndProductId(cart, productId)).thenReturn(cartItems);
		CartItem cartItem=cartItems.get(0);
		cartService.deleteItem(userId, productId);
		verify(itemRepository,times(1)).delete(cartItem);
	}
	
	@Test
	 void removeItem() {
		String userId = "B$00002";
		int productId=1;
		Cart cart = new Cart("B$00002", "INDORE", 0, 0);
		when(cartRepository.findById(userId)).thenReturn(Optional.of(cart));
		
		List<CartItem> cartItems=Stream.of(new CartItem(1,2,1.5)).collect(Collectors.toList());
		when(itemRepository.findByCartAndProductId(cart, productId)).thenReturn(cartItems);
		CartItem cartItem=cartItems.get(0);
		cartService.removeItem(userId, productId);
		verify(cartRepository,times(1)).save(cart);
		verify(itemRepository,times(1)).save(cartItem);
	}

	@Test
	 void removeOneItem() {
		String userId = "B$00002";
		int productId=1;
		Cart cart = new Cart("B$00002", "INDORE", 0, 0);
		when(cartRepository.findById(userId)).thenReturn(Optional.of(cart));
		
		List<CartItem> cartItems=Stream.of(new CartItem(1,1,1.5)).collect(Collectors.toList());
		when(itemRepository.findByCartAndProductId(cart, productId)).thenReturn(cartItems);
		CartItem cartItem=cartItems.get(0);
		cartService.removeItem(userId, productId);
		verify(itemRepository,times(1)).delete(cartItem);
	}
	
	@Test
	 void payment() throws RazorpayException {
		String amount="800";
		assertNotEquals(null,cartService.payment(amount));
	}
	
	@Test
	 void addItem() {
		String userId = "B$00002";
		int productId=1;
		Cart cart = new Cart("B$00002", "INDORE", 0, 0);
		when(cartRepository.findById(userId)).thenReturn(Optional.of(cart));
		
		List<CartItem> cartItems=Stream.of(new CartItem(1,1,1.5)).collect(Collectors.toList());
		when(itemRepository.findByCartAndProductId(cart, productId)).thenReturn(cartItems);
		cartService.addItem(userId, productId);
		verify(cartRepository,times(1)).save(cart);
	}
	
	@Test
	 void addExtraItem() {
		String userId = "B$00002";
		int productId=1;
		Cart cart = new Cart("B$00002", "INDORE", 0, 10);
		when(cartRepository.findById(userId)).thenReturn(Optional.of(cart));
		
		List<CartItem> cartItems=Stream.of(new CartItem(1,10,1.5)).collect(Collectors.toList());
		when(itemRepository.findByCartAndProductId(cart, productId)).thenReturn(cartItems);
		assertThrows(CartQuantityException.class, ()->cartService.addItem(userId, productId));
	}
	
	@Test
	 void addExtraQuantity() {
		String userId = "B$00002";
		int productId=1;
		Cart cart = new Cart("B$00002", "INDORE", 25, 9);
		when(cartRepository.findById(userId)).thenReturn(Optional.of(cart));
		
		List<CartItem> cartItems=Stream.of(new CartItem(1,10,1.5)).collect(Collectors.toList());
		when(itemRepository.findByCartAndProductId(cart, productId)).thenReturn(cartItems);
		assertThrows(CartQuantityException.class, ()->cartService.addItem(userId, productId));
	}
	
	@Test
	 void getProduct() {
		int productId=1;
		ProductDto productDto=new ProductDto(1,"Milk","../../../../../assets/images/milk1.jpg",28,"litre",1.5,"milk desc",1);
		when(restTemplate.getForEntity("lb://product-service/product/get-product/"+Integer.toString(productId), ProductDto.class))
		.thenReturn(new ResponseEntity<ProductDto>(productDto,HttpStatus.OK));
		assertEquals(productDto,cartService.getProduct(productId));
	}
	
	@Test
	 void addToCart() {
		String userId = "B$00002";
		int productId=1;
		Cart cart = new Cart("B$00002", "INDORE", 0, 0);
		when(cartRepository.findById(userId)).thenReturn(Optional.of(cart));
		ProductDto productDto=new ProductDto(1,"Milk","../../../../../assets/images/milk1.jpg",28,"litre",1.5,"milk desc",1);
		when(restTemplate.getForEntity("lb://product-service/product/get-product/"+Integer.toString(productId), ProductDto.class))
		.thenReturn(new ResponseEntity<ProductDto>(productDto,HttpStatus.OK));
		
		List<CartItem> cartItems=new ArrayList<CartItem>();
		when(itemRepository.findByCartAndProductId(cart, productId)).thenReturn(cartItems);
		cartService.addToCart(userId, productId);
		verify(cartRepository,times(1)).save(cart);
	}
	
	@Test
	 void addExtraToCart() {
		String userId = "B$00002";
		int productId=1;
		Cart cart = new Cart("B$00002", "INDORE", 0, 10);
		when(cartRepository.findById(userId)).thenReturn(Optional.of(cart));
		ProductDto productDto=new ProductDto(1,"Milk","../../../../../assets/images/milk1.jpg",28,"litre",1.5,"milk desc",1);
		when(restTemplate.getForEntity("lb://product-service/product/get-product/"+Integer.toString(productId), ProductDto.class))
		.thenReturn(new ResponseEntity<ProductDto>(productDto,HttpStatus.OK));
		
		List<CartItem> cartItems=new ArrayList<CartItem>();
		when(itemRepository.findByCartAndProductId(cart, productId)).thenReturn(cartItems);
		assertThrows(CartQuantityException.class, ()->cartService.addToCart(userId, productId));
	}
	
	@Test
	 void addExtraWeightToCart() {
		String userId = "B$00002";
		int productId=1;
		Cart cart = new Cart("B$00002", "INDORE", 25, 9);
		when(cartRepository.findById(userId)).thenReturn(Optional.of(cart));
		ProductDto productDto=new ProductDto(1,"Milk","../../../../../assets/images/milk1.jpg",28,"litre",1.5,"milk desc",1);
		when(restTemplate.getForEntity("lb://product-service/product/get-product/"+Integer.toString(productId), ProductDto.class))
		.thenReturn(new ResponseEntity<ProductDto>(productDto,HttpStatus.OK));
		
		List<CartItem> cartItems=new ArrayList<CartItem>();
		when(itemRepository.findByCartAndProductId(cart, productId)).thenReturn(cartItems);
		assertThrows(CartQuantityException.class, ()->cartService.addToCart(userId, productId));
	}
	
	@Test
	 void getCart() {
		String userId = "B$00002";
		int productId=1;
		Cart cart = new Cart("B$00002", "INDORE", 25, 9);
		CartItem cartItem = new CartItem(1, 1, 10);
		cart.setCartItems(Stream.of(cartItem).collect(Collectors.toList()));
		when(cartRepository.findById(userId)).thenReturn(Optional.of(cart));
		ProductDto productDto=new ProductDto(1,"Milk","../../../../../assets/images/milk1.jpg",28,"litre",1.5,"milk desc",1);
		when(restTemplate.getForEntity("lb://product-service/product/get-product/"+Integer.toString(productId), ProductDto.class))
		.thenReturn(new ResponseEntity<ProductDto>(productDto,HttpStatus.OK));
		
		assertEquals(1,cartService.getCart(userId).size());
	}
	
	@Test
	 void checkStatus() {
		String userId = "B$00002";
		int productId=1;
		Cart cart = new Cart("B$00002", "INDORE", 25, 9);
		CartItem cartItem = new CartItem(1, 1, 10);
		cart.setCartItems(Stream.of(cartItem).collect(Collectors.toList()));
		when(cartRepository.findById(userId)).thenReturn(Optional.of(cart));
		InventoryItemDto inventoryDto=new InventoryItemDto(1,0);
		when(restTemplate.getForEntity("lb://inventory-service/inventory/get-stock/"+cart.getInventoryId()+"/"+Integer.toString(cartItem.getProductId()), InventoryItemDto.class))
		.thenReturn(new ResponseEntity<InventoryItemDto>(inventoryDto, HttpStatus.OK));
		
		ProductDto productDto=new ProductDto(1,"Milk","../../../../../assets/images/milk1.jpg",28,"litre",1.5,"milk desc",1);
		when(restTemplate.getForEntity("lb://product-service/product/get-product/"+Integer.toString(productId), ProductDto.class))
		.thenReturn(new ResponseEntity<ProductDto>(productDto,HttpStatus.OK));
		
		assertEquals("FAIL",cartService.checkStatus(userId).getCartStatus());
	}
	
	@Test
	 void checkExtraQuantityStatus() {
		String userId = "B$00002";
		int productId=1;
		Cart cart = new Cart("B$00002", "INDORE", 25, 9);
		CartItem cartItem = new CartItem(1, 3, 10);
		cart.setCartItems(Stream.of(cartItem).collect(Collectors.toList()));
		when(cartRepository.findById(userId)).thenReturn(Optional.of(cart));
		InventoryItemDto inventoryDto=new InventoryItemDto(1,2);
		when(restTemplate.getForEntity("lb://inventory-service/inventory/get-stock/"+cart.getInventoryId()+"/"+Integer.toString(cartItem.getProductId()), InventoryItemDto.class))
		.thenReturn(new ResponseEntity<InventoryItemDto>(inventoryDto, HttpStatus.OK));
		
		ProductDto productDto=new ProductDto(1,"Milk","../../../../../assets/images/milk1.jpg",28,"litre",1.5,"milk desc",1);
		when(restTemplate.getForEntity("lb://product-service/product/get-product/"+Integer.toString(productId), ProductDto.class))
		.thenReturn(new ResponseEntity<ProductDto>(productDto,HttpStatus.OK));
		
		assertEquals("FAIL",cartService.checkStatus(userId).getCartStatus());
		
	}
	
	@Test
		void checkOut() throws JsonProcessingException, URISyntaxException {
		String userId = "B$00002";
		int productId=1;
		Cart cart = new Cart("B$00002", "INDORE", 20, 9);
		CartItem cartItem = new CartItem(1, 1, 10);
		cart.setCartItems(Stream.of(cartItem).collect(Collectors.toList()));
		when(cartRepository.findById(userId)).thenReturn(Optional.of(cart));
		
		ProductDto productDto=new ProductDto(1,"Milk","../../../../../assets/images/milk1.jpg",28,"litre",1.5,"milk desc",1);
		when(restTemplate.getForEntity("lb://product-service/product/get-product/"+Integer.toString(productId), ProductDto.class))
		.thenReturn(new ResponseEntity<ProductDto>(productDto,HttpStatus.OK));
		
	
		cartService.checkOut(userId,1,"1");
		verify(cartRepository,times(1)).save(cart);
	}
	
}
