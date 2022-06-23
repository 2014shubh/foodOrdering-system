package com.impetus.ogos.orderservice.controller;

import com.impetus.ogos.orderservice.common.ApiResponse;
import com.impetus.ogos.orderservice.dto.FeedbackDto;

//import com.impetus.ogos.orderservice.service.orderService;

import com.impetus.ogos.orderservice.dto.OrderDto;
import com.impetus.ogos.orderservice.dto.OrderItemsDto;
import com.impetus.ogos.orderservice.dto.PlaceOrderDto;
import com.impetus.ogos.orderservice.exception.OrderNotFoundException;
import com.impetus.ogos.orderservice.model.Order;
import com.impetus.ogos.orderservice.model.enumeration.OrderStage;
import com.impetus.ogos.orderservice.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/order")

public class OrderController {
	static Logger logger = LoggerFactory.getLogger(OrderController.class);
	@Autowired
	public OrderService orderService;

	@PostMapping("/placeOrder")
	public ResponseEntity<ApiResponse> placeOrder(@Valid @RequestBody PlaceOrderDto orderDto) {
		logger.info("inside placeOrder method of OrderContoller");

		orderService.createOrder(orderDto);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Order has been created succesfully"), HttpStatus.CREATED);
	}

//	@GetMapping("/all")
//	@CrossOrigin("http://localhost:4200")
//	public ResponseEntity<List<Order>> getallOrdersDetail(Order order) {
//		logger.info("inside getallOrdersDetail method of OrderContoller");
//
//		List<Order> orderList = orderService.getAllOrders();
//
//		return new ResponseEntity<List<Order>>(orderList, HttpStatus.OK);
//	}

	@GetMapping("/all")

	public ResponseEntity<List<OrderDto>> getallOrdersDetail(OrderDto orderDto) {
		logger.info("  getallOrdersDetail method of OrderContoller");
		List<OrderDto> orderList = orderService.listOrders();
		return new ResponseEntity<List<OrderDto>>(orderList, HttpStatus.OK);
	}

	@GetMapping("/{id}")

	public ResponseEntity<Object> fetchOrdersByOrderId(@PathVariable("id") Integer id) {
		logger.info("inside fetchOrdersByOrderId method of Order Contoller");

		try {
			Order orderByOrderId = this.orderService.getOrder(id);
			return new ResponseEntity<>(orderByOrderId, HttpStatus.OK);
		} catch (OrderNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}

	}
	@GetMapping("/fetch/{status}")
	public List<Order> fetchByCategory(@PathVariable("status") String status) {

		return loadOrderByStatus(status);
	}

	private List<Order> loadOrderByStatus(String status) {
		OrderStage orderStatus = OrderStage.Pending;
//        orderStage.Pending;;

		switch (status) {
		case "status_All":
			return this.orderService.getAllOrders();
		case "status_Packed":
			orderStatus = OrderStage.Packed;
			break;
		case "status_Shipped":
			orderStatus = OrderStage.Shipped;
			break;
		case "status_Delivered":
			orderStatus = OrderStage.Delivered;
			break;
		}
		return orderService.findOrderByStatus(orderStatus);
	}

	@GetMapping("/getjason")
	public PlaceOrderDto getjason() {
		List<OrderItemsDto> list = Arrays.asList(new OrderItemsDto(2, 1, 90,"milk"),
				new  OrderItemsDto(2, 5, 50,"honey")


		);
		PlaceOrderDto dto = new PlaceOrderDto();
		dto.setOrderItemsDtoList(list);
		dto.setUserId("11");
		dto.setTotalPrice(100);
		return dto;
	}
	
	@GetMapping("/user/{userId}")

	public  ResponseEntity<List<OrderDto>> getAllOrdersOfUserbyUserId(@PathVariable("userId") String userId)
	{
		logger.info("inside getAllOrdersOfUserByUserId method of OrderContoller");
	    List<OrderDto> orderList =orderService.fetchOrderByUserId(userId);
		Collections.reverse(orderList);
		return new ResponseEntity<List<OrderDto>>( orderList,HttpStatus.OK);
	}


	@PutMapping("/change/status/{id}")


	public void orderChangeStatus(@PathVariable("id") Integer id) {

		logger.info("inside orderChangeStatus method of OrderContoller");

		 this.orderService.changeOrderStatus(id);

	}
	
	@PutMapping("/feedback")
	public ResponseEntity<ApiResponse> addFeedback(@RequestBody FeedbackDto feedbackDto) {
		logger.info("inside addFeedback method of OrderContoller");

		 orderService.addFeedback(feedbackDto);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Feedback added"),HttpStatus.OK);
	}
}
