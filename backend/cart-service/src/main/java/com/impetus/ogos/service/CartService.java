package com.impetus.ogos.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.impetus.ogos.dto.CartDto;
import com.impetus.ogos.dto.CartItemDto;
import com.impetus.ogos.dto.CartProductDto;
import com.impetus.ogos.dto.CartStatusDto;
import com.impetus.ogos.dto.InventoryItemDto;
import com.impetus.ogos.dto.ItemResponse;
import com.impetus.ogos.dto.OrderItemsDto;
import com.impetus.ogos.dto.PlaceOrderDto;
import com.impetus.ogos.dto.ProductDto;

import com.impetus.ogos.exception.CartQuantityException;

import com.impetus.ogos.exception.IdNotFoundException;
import com.impetus.ogos.exception.InvalidOperationException;

import com.impetus.ogos.model.Cart;
import com.impetus.ogos.model.CartItem;
import com.impetus.ogos.repository.CartItemRepository;
import com.impetus.ogos.repository.CartRepository;

import antlr.collections.impl.Vector;

/**
 * @author mustafa.kasarawala
 *
 */

@Service
public class CartService 
{
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	/**
	 * @param productId
	 * @return productDto
	 * @throws IdNotFoundException
	 * 
	 * This method takes product id and fetches the product dto from product microservice.
	 * 
	 */
	public ProductDto getProduct(int productId) throws IdNotFoundException
	{
		
		ResponseEntity<ProductDto> response = restTemplate.getForEntity("lb://product-service/product/get-product/"+Integer.toString(productId), ProductDto.class);
		if(response.getStatusCode() == HttpStatus.NOT_FOUND) throw new IdNotFoundException("Product Not Found.");
		return response.getBody();

	}
	
	
	public List<CartProductDto> getCart(String userId) throws IdNotFoundException
	{
		
		Cart cart = cartRepository.findById(userId).orElseThrow(()-> {return new IdNotFoundException("User Not Found.");});
		
		List<CartProductDto> cartProductDtos = cart.getCartItems().stream().map((cartItem) -> {
			return new CartProductDto( 
					getProduct(cartItem.getProductId()),
					cartItem.getQuantity()
					);
		}).collect(Collectors.toList());
		
		return cartProductDtos;
	}
	
	
	/**
	 * @param userId
	 * @param itemToAdd
	 * @return cartItemDto
	 * @throws IdNotFoundException
	 * @throws InvalidOperationException
	 * @throws CartQuantityException
	 * 
	 * 
	 * This function adds an item in the user's cart.
	 * If the item already exists it returns invalid operation exception which returns http bad request code to user.
	 * It performs total weight check and total item check i.e. total weight < 25 and total items <15
	 * 
	 * 
	 */
	public ItemResponse addToCart(String userId, Integer productId) throws IdNotFoundException, InvalidOperationException, CartQuantityException
	{
		
		Cart cart = cartRepository.findById(userId).orElseThrow(()-> {return new IdNotFoundException("User Not Found.");});
		
		ProductDto product = getProduct(productId);
		
		if(!(cartItemRepository.findByCartAndProductId(cart, productId).isEmpty())) throw new InvalidOperationException("Item already exists in cart");
		
		CartItem cartItem = new CartItem(productId, 1, product.getProductWeight(), cart);
		
		double weight = product.getProductWeight();
		
		boolean flag = false;
		String message = "";
		
		if( cart.getTotalItems() == 10) 
		{
			flag = true;
			message = "More than 10 items not allowed in cart. ";
		}
		
		if((weight + cart.getTotalWeight()) > 25.0)
		{
			flag = true;
			message += "More than 25Kg weight not allowed in cart.";
		}
		
		if(flag) throw new CartQuantityException(message);
		
		cart.setTotalItems(cart.getTotalItems()+1);
		
		cart.setTotalWeight(cart.getTotalWeight()+weight);
		
		cartRepository.save(cart);

		cartItemRepository.save(cartItem);
		
		return new ItemResponse(cartItem);
		
	} 
	
	
	public ItemResponse addItem(String userId, Integer productId ) throws IdNotFoundException, InvalidOperationException, CartQuantityException
	{
		
		Cart cart = cartRepository.findById(userId).orElseThrow(()-> {return new IdNotFoundException("User Not Found.");});
		
		List<CartItem> list = cartItemRepository.findByCartAndProductId(cart, productId);
		
		if(list.isEmpty()) throw new InvalidOperationException("This item does not exist in cart.");
		
		CartItem cartItem = list.get(0);
		
		double weight = cartItem.getProductWeight();
		
		boolean flag = false;
		String message = "";
		
		if(cart.getTotalItems() == 10) 
		{
			flag = true;
			message = "More than 15 items not allowed in cart. ";
		}
		
		if((weight + cart.getTotalWeight()) > 25.0)
		{
			flag = true;
			message += "More than 25Kg weight not allowed in cart.";
		}
		
		if(flag) throw new CartQuantityException(message);
		
		cart.setTotalItems(cart.getTotalItems()+1);
		
		cart.setTotalWeight(cart.getTotalWeight()+weight);
		
		cartItem.setQuantity(cartItem.getQuantity()+1);
		
		cartRepository.save(cart);

		cartItemRepository.save(cartItem);
		
		return new ItemResponse(cartItem);
		
	}
	public String payment(String amount) throws RazorpayException {
		int amt=Integer.parseInt(amount);

		RazorpayClient client=new RazorpayClient("rzp_test_saj0v6zSI5Zodr", "DyQizL9PJXNHlFEJ08WFCccl");

		JSONObject ob=new JSONObject();
		ob.put("amount", amt*100);
		ob.put("currency", "INR");
		ob.put("receipt", "txn_235425");
		Order order =client.Orders.create(ob);
		return order.toString();
	}
	
	
	
	public ItemResponse removeItem(String userId, Integer productId) throws IdNotFoundException, InvalidOperationException, CartQuantityException	
	{
		
		Cart cart = cartRepository.findById(userId).orElseThrow(()-> {return new IdNotFoundException("User Not Found.");});
		
		List<CartItem> list = cartItemRepository.findByCartAndProductId(cart, productId);
		
		if(list.isEmpty()) throw new InvalidOperationException("This item does not exist in cart.");
		
		CartItem cartItem = list.get(0);
		
		if(cartItem.getQuantity() == 1) 
		{
			deleteItem(userId, productId);
			return new ItemResponse(productId, 0);
		}
		
		cartItem.setQuantity(cartItem.getQuantity()-1);
		
		cart.setTotalItems(cart.getTotalItems()-1);
		
		cart.setTotalWeight(cart.getTotalWeight() - cartItem.getProductWeight());
		
		cartRepository.save(cart);

		cartItemRepository.save(cartItem);
		
		return new ItemResponse(cartItem);
		
	}
	
	
	
	
	/**
	 * @param userId
	 * @param itemToRemove
	 * @throws IdNotFoundException
	 * @throws InvalidOperationException
	 * 
	 * This function deletes an item present in users cart.
	 * 
	 */
	public void deleteItem(String userId, Integer productId) throws IdNotFoundException, InvalidOperationException
	{
		
		Cart cart = cartRepository.findById(userId).orElseThrow(()-> {return new IdNotFoundException("User Not Found.");});
		
		List<CartItem> list = cartItemRepository.findByCartAndProductId(cart, productId);
		
		if(list.isEmpty()) throw new InvalidOperationException("Item already does not exist in cart.");
		
		CartItem cartItem = list.get(0);
		
		cart.setTotalItems(cart.getTotalItems() - cartItem.getQuantity());
		
		cart.setTotalWeight(cart.getTotalWeight() - (cartItem.getQuantity() * cartItem.getProductWeight()));
		
		cartItemRepository.delete(cartItem);
		
		cartRepository.save(cart);
	}
	
	
	public void deleteAllItems(String userId) throws IdNotFoundException
	{
		Cart cart = cartRepository.findById(userId).orElseThrow(()-> {return new IdNotFoundException("User Not Found.");});
		
		cartRepository.delete(cart);
		
		cart = new Cart(userId, "INDORE", 0, 0, null);
		
		cartRepository.save(cart);
	}
	
	public CartStatusDto checkStatus(String userId) throws IdNotFoundException
	{
		Cart cart = cartRepository.findById(userId).orElseThrow(()-> {return new IdNotFoundException("User Not Found.");});
		
		CartStatusDto cartStatus = new CartStatusDto("SUCCESS", 0, new ArrayList<String>());
		
		List<CartItem> items = cart.getCartItems();
		for(int i=0; i<items.size(); i++) {
			
			CartItem item = items.get(i);
			
		//cartStatus.setMessages(cart.getCartItems().stream().map((item) ->{
			//for(CartItem item:cart.getCartItems()) {
			
			//String message = new String("");
			
			ResponseEntity<InventoryItemDto> response = restTemplate.getForEntity("lb://inventory-service/inventory/get-stock/"+cart.getInventoryId()+"/"+Integer.toString(item.getProductId()), InventoryItemDto.class);
			if(response.getStatusCode() == HttpStatus.NOT_FOUND) throw new IdNotFoundException("Product Not Found.");
			InventoryItemDto inventoryItem = response.getBody();
			
			if(inventoryItem.getQuantity() == 0)
			{
				ProductDto product = getProduct(item.getProductId());
				
				cart.setTotalItems(cart.getTotalItems() - item.getQuantity());
				cart.setTotalWeight(cart.getTotalWeight() - (item.getQuantity() * item.getProductWeight()));
				
				cartRepository.save(cart);
				cartItemRepository.delete(item);
				
				cartStatus.setCartStatus("FAIL");
				cartStatus.setIssues(cartStatus.getIssues()+1);
				cartStatus.addMessage(" We are sorry "+product.getName()+" is currently out of stock !");
				
			}
			else if(inventoryItem.getQuantity() < item.getQuantity() )
			{
				ProductDto product = getProduct(item.getProductId());
				int diff = item.getQuantity() - inventoryItem.getQuantity();
				
				cart.setTotalItems(cart.getTotalItems()-diff);
				cart.setTotalWeight(cart.getTotalWeight()-(diff*item.getProductWeight()));
				item.setQuantity(inventoryItem.getQuantity());
				cartItemRepository.save(item);
				cartRepository.save(cart);
				
				cartStatus.setCartStatus("FAIL");
				cartStatus.setIssues(cartStatus.getIssues()+1);
				cartStatus.addMessage(" We are sorry only "+inventoryItem.getQuantity()+" packet of " +product.getName()+" left in stock !");
			}
		}//).filter((item)-> (!item.isEmpty())).collect(Collectors.toList()));
		
		return cartStatus;
	}
	
	
	public void checkOut(String userId, int addressId, String paymentId) throws IdNotFoundException
	{
		Cart cart = cartRepository.findById(userId).orElseThrow(()-> {return new IdNotFoundException("User Not Found.");});
		
		cart.getCartItems().stream().map( (item)-> {
			return new InventoryItemDto(item.getProductId(), item.getQuantity());
		}).forEach( (item) ->{
			RequestEntity<InventoryItemDto> inventoryRequest;
			try {
				inventoryRequest = RequestEntity.put(new URI("http://inventory-service/inventory/empty-stock/"+cart.getInventoryId())).body(item);
				restTemplate.exchange(inventoryRequest, void.class);
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}});
		
		List<OrderItemsDto> orderItems = cart.getCartItems().stream().map((item) ->{
			ProductDto product = getProduct(item.getProductId());
			return new OrderItemsDto(item.getQuantity(), product.getPrice(), item.getProductId(), product.getName());
		}).collect(Collectors.toList());
		
		double totalPrice = orderItems.stream().map((item)-> {
			return item.getPurchasePrice() * item.getQuantity();
		}).reduce(0.0, Double::sum);
		
		PlaceOrderDto order = new PlaceOrderDto(userId, totalPrice, addressId, paymentId, orderItems );
		
		RequestEntity<PlaceOrderDto> request;
		try {
			request = RequestEntity.post(new URI("lb://order-service/order/placeOrder")).body(order);
			restTemplate.exchange(request, void.class);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cart.setTotalItems(0);
		cart.setTotalWeight(0);
		cartRepository.save(cart);
		cart.getCartItems().stream().forEach(cartItemRepository::delete);
		
	}
		
		/*CartDto cartDto = getCart(userId);
		
		PlaceOrderDto placeOrderDto = new PlaceOrderDto();
		
		placeOrderDto.setUserId(userId);
		
		placeOrderDto.setInventoryId(cart.getInventoryId());
		
		placeOrderDto.setOrderItemsDtoList(
				cartDto.getItems().stream().map((cartItem) ->
				{
					return new OrderItemsDto( cartItem.getProductId(), 
							cartItem.getCartQuantity(), 
							(getProduct(cartItem.getProductId()).getPrice() * cartItem.getCartQuantity()));
				}).toList()
				);
		
		placeOrderDto.setTotalPrice(
				placeOrderDto.getOrderItemsDtoList().stream().map(item -> item.getPurchasePrice()).reduce(0.0, Double::sum));
		
		cartDto.getItems().stream().map( (item)-> {
			return new InventoryItemDto(item.getProductId(), item.getCartQuantity());
		}).forEach( (item) ->{
			RequestEntity<InventoryItemDto> inventoryRequest;
			try {
				inventoryRequest = RequestEntity.put(new URI("http://inventory-service/inventory/empty-stock/"+cart.getInventoryId())).body(item);
				restTemplate.exchange(inventoryRequest, void.class);
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});
		
		RequestEntity<PlaceOrderDto> request = RequestEntity.post(new URI("http://order-service/place-order")).body(placeOrderDto);
		restTemplate.exchange(request, void.class);
		
		cart.getCartItems().stream().forEach(cartItemRepository::delete);
		
	}*/
	
	
	
	
	/**
	 * @param userId
	 * @return CartDto
	 * @throws IdNotFoundException
	 * 
	 * This function returns the complete cart of the user.
	 * First it refreshes the cart to match each items quantity with inventory stock.
	 * then it returns the cartDto containing list of items to user.
	 * 
	 */
/*	public CartDto getCart(String userId) throws IdNotFoundException
	{
		
		Cart cart = cartRepository.findById(userId).orElseThrow(()-> {return new IdNotFoundException("User Not Found.");});
		
		List<CartItemDto> cartItemDtos = cart.getCartItems().stream().map((cartItem) -> {
			return getCartItemDto(cart, cartItem);
		}).collect(Collectors.toList());
		
		cart.getCartItems().stream().forEach(cartItemRepository::save);
		
		cartRepository.save(cart);
		
		return new CartDto(cartItemDtos);
	}*/
	
	

	
	/**
	 * @param userId
	 * @throws IdNotFoundException
	 * @throws URISyntaxException
	 * 
	 * This function deletes stock in inventory corresponding to quantity of items in cart.
	 * then it creates a new order and sends orderDto to order microservice.
	 * then it deletes items in user's cart.
	 * 
	 */
/*	public void checkOutCart(String userId) throws IdNotFoundException, URISyntaxException
	{
		Cart cart = cartRepository.findById(userId).orElseThrow(()-> {return new IdNotFoundException("User Not Found.");});
		
		CartDto cartDto = getCart(userId);
		
		PlaceOrderDto placeOrderDto = new PlaceOrderDto();
		
		placeOrderDto.setUserId(userId);
		
		placeOrderDto.setInventoryId(cart.getInventoryId());
		
		placeOrderDto.setOrderItemsDtoList(
				cartDto.getItems().stream().map((cartItem) ->
				{
					return new OrderItemsDto( cartItem.getProductId(), 
							cartItem.getCartQuantity(), 
							(getProduct(cartItem.getProductId()).getPrice() * cartItem.getCartQuantity()));
				}).toList()
				);
		
		placeOrderDto.setTotalPrice(
				placeOrderDto.getOrderItemsDtoList().stream().map(item -> item.getPurchasePrice()).reduce(0.0, Double::sum));
		
		cartDto.getItems().stream().map( (item)-> {
			return new InventoryItemDto(item.getProductId(), item.getCartQuantity());
		}).forEach( (item) ->{
			RequestEntity<InventoryItemDto> inventoryRequest;
			try {
				inventoryRequest = RequestEntity.put(new URI("http://inventory-service/inventory/empty-stock/"+cart.getInventoryId())).body(item);
				restTemplate.exchange(inventoryRequest, void.class);
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});
		
		RequestEntity<PlaceOrderDto> request = RequestEntity.post(new URI("http://order-service/place-order")).body(placeOrderDto);
		restTemplate.exchange(request, void.class);
		
		cart.getCartItems().stream().forEach(cartItemRepository::delete);
		
	}
	
	*/
	
	
	/**
	 * @param userId
	 * 
	 * when a new customer is registered in the system, his cart is automatically created.
	 * cart id is same as user id.
	 * 
	 */
	public void addCart(String userId)
	{
		if(cartRepository.existsById(userId)) return;
		
		
		Cart newCart = new Cart(userId, "INDORE", 0, 0, null);
		cartRepository.save(newCart);
		
		
		
		
	}
	
	
	
	/**
	 * @param userId
	 * @throws IdNotFoundException
	 * 
	 * when a customer is deleted from system his cart is also deleted.
	 * 
	 */
	public void deleteCart(String userId) throws IdNotFoundException
	{
		Cart cart = cartRepository.findById(userId).orElseThrow(()-> {return new IdNotFoundException("User Not Found.");});
		cartRepository.delete(cart);
	}
	
	
	
	/**
	 * @param userId
	 * @param inventoryId
	 * @throws IdNotFoundException
	 * 
	 * this function is used to change the inventory of a customer.
	 * 
	 */
	public void changeInventory(String userId, String inventoryId) throws IdNotFoundException
	{
		Cart cart = cartRepository.findById(userId).orElseThrow(()-> {return new IdNotFoundException("User Not Found.");});
		
		cart.setInventoryId(inventoryId);
		
		cartRepository.save(cart);
	}

}