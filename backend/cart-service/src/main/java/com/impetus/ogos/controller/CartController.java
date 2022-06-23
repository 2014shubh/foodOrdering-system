package com.impetus.ogos.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.impetus.ogos.dto.CartDto;
import com.impetus.ogos.dto.CartItemDto;
import com.impetus.ogos.dto.CartProductDto;
import com.impetus.ogos.dto.CartStatusDto;
import com.impetus.ogos.dto.CheckOutDto;
import com.impetus.ogos.dto.ItemRequest;
import com.impetus.ogos.dto.ItemResponse;
import com.impetus.ogos.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController 
{
	
	@Autowired
	private CartService cartService;
	
	
	@GetMapping("/get-cart/{userId}")
	public List<CartProductDto> getCart(@PathVariable(value = "userId") String userId)
	{
		return cartService.getCart(userId);
	}
	
	
	@PostMapping("/check-out/{userId}")
	public void checkOutCart(@PathVariable String userId, @Valid @RequestBody CheckOutDto checkOutDto)
	{
		cartService.checkOut( userId, checkOutDto.getAddressId(), checkOutDto.getPaymentId());
	}
	
	@GetMapping("/check-status/{userId}")
	public CartStatusDto checkCartStatus(@PathVariable String userId)
	{
		return cartService.checkStatus(userId);
	}
	
	@Modifying 
	@PutMapping("/delete-all/{userId}")
	public void deleteAllItems(@PathVariable String userId)
	{
		cartService.deleteAllItems(userId);
	}
	
	@PostMapping("/add-to-cart/{userId}/{productId}")
	public ItemResponse addToCart(@PathVariable String userId, @PathVariable String productId)
	{
		return cartService.addToCart(userId, Integer.parseInt(productId));
	}
	
	
	@PostMapping("/add-item/{userId}/{productId}")
	public ItemResponse addItem(@PathVariable String userId, @PathVariable String productId)
	{
		return cartService.addItem(userId, Integer.parseInt(productId));
	}
	
	@PostMapping("/remove-item/{userId}/{productId}")
	public ItemResponse removeItem(@PathVariable String userId, @PathVariable String productId)
	{
		return cartService.removeItem(userId, Integer.parseInt(productId));
	}
	
	@PostMapping("/delete-item/{userId}/{productId}")
	public void deleteItem(@PathVariable String userId, @PathVariable String productId)
	{
		cartService.deleteItem(userId, Integer.parseInt(productId));
	}
	
	
	@PostMapping("/add-cart/{userId}")
	public void addCart(@PathVariable(value = "userId") String userId)
	{
		cartService.addCart(userId);
	}
	
	@DeleteMapping("/delete-cart/{userId}")
	public void deleteCart(@PathVariable(value = "userId") String userId)
	{
		cartService.deleteCart(userId);
	}
	
	@PutMapping("/change-inventory/{userId}/{inventoryId}")
	public void changeInventory(@PathVariable String userId, @PathVariable String inventoryId)
	{
		cartService.changeInventory(userId, inventoryId);
	}
	
	@PostMapping("/payment/{amount}")
	public String payment(@PathVariable(value="amount") String amount) throws Exception {
		return cartService.payment(amount);
	}

	
	
	
	
	
	
/*	@PostMapping("/add-item/{userId}")
	public CartItemDto addItem(@PathVariable(value = "userId") String userId, @Valid @RequestBody CartItemDto itemDto)
	{
		return cartService.addItem(userId, itemDto);
	}*/
	
/*	@PostMapping("/add-item/{userId}/{productId}")
	//@CrossOrigin("http://localhost:4200")
	public List<CartProductDto> addItem(@PathVariable(value = "userId") String userId, @PathVariable(value = "productId") String productId)
	{
		CartItemDto itemDto = new CartItemDto(Integer.parseInt(productId),1,1);
		cartService.addItem(userId, itemDto);
		return cartService.getCart(userId); 
	} 
	
	
	@PutMapping("/update-item/{userId}")
	public CartItemDto updateItem(@PathVariable(value = "userId") String userId, @Valid @RequestBody CartItemDto itemDto)
	{
		return cartService.updateItem(userId, itemDto);
	}
	
	@PutMapping("/remove-item/{userId}")
	public void removeItem(@PathVariable(value = "userId") String userId, @Valid @RequestBody CartItemDto itemDto)
	{
		cartService.removeItem(userId, itemDto);
	}
	
/*	@GetMapping("/get-cart/{userId}")
	public CartDto getCart(@PathVariable(value = "userId") String userId)
	{
		return cartService.getCart(userId);
	}
	
	

	@GetMapping("/get-cart/{userId}")
	public List<CartProductDto> getCart(@PathVariable(value = "userId") String userId)
	{
		return cartService.getCart(userId);
	}
	
	
	@PostMapping("/add-cart/{userId}")
	public void addCart(@PathVariable(value = "userId") String userId)
	{
		cartService.addCart(userId);
	}
	
	@DeleteMapping("/delete-cart/{userId}")
	public void deleteCart(@PathVariable(value = "userId") String userId)
	{
		cartService.deleteCart(userId);
	}
	
	@PutMapping("/change-inventory/{userId}/{inventoryId}")
	public void changeInventory(@PathVariable String userId, @PathVariable String inventoryId)
	{
		cartService.changeInventory(userId, inventoryId);
	}
	
	@GetMapping("get-json/{product}/{req}/{cart}")
	public CartItemDto getJson(@PathVariable(value = "product") String product, @PathVariable(value="req") String req, @PathVariable(value="cart") String cart)
	{
		int productId = Integer.parseInt(product);
		int requestedQuantity = Integer.parseInt(req);
		int cartQuantity = Integer.parseInt(cart);
		
		return new CartItemDto(productId, requestedQuantity, cartQuantity);
	}*/
	
}
