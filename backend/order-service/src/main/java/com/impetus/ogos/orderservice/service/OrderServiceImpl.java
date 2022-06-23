package com.impetus.ogos.orderservice.service;

import com.impetus.ogos.orderservice.controller.OrderController;
import com.impetus.ogos.orderservice.dto.FeedbackDto;
import com.impetus.ogos.orderservice.dto.OrderDto;
import com.impetus.ogos.orderservice.dto.PlaceOrderDto;
import com.impetus.ogos.orderservice.exception.OrderNotFoundException;
import com.impetus.ogos.orderservice.model.Order;
import com.impetus.ogos.orderservice.model.enumeration.OrderStage;
import com.impetus.ogos.orderservice.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
	static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	@Autowired
	private OrderRepository repo;

	@Autowired
	private OrderItemsService orderItemsService;

	@Override
	public void createOrder(PlaceOrderDto orderDto) {
		logger.info("inside createOrder method of OrderService");
		Order orderFromDto = getOrderFromDto(orderDto);
		orderFromDto.setTimeOfOrder(LocalDateTime.now());

		orderFromDto.setStage(OrderStage.Pending);
		repo.save(orderFromDto);
		System.out.println(orderDto);

	}

	/**
	 * This method is basically converting our Order entity into PlaceOrderDto and
	 * setting the values that it'll receive from the cart.
	 * 
	 * @param orderDto
	 * @return
	 */
	@Override
	public Order getOrderFromDto(PlaceOrderDto orderDto) {
		logger.info("inside getOrderFromDto method of OrderService");
		Order order = new Order();
		order.setUserId(orderDto.getUserId());
		order.setAddressId(orderDto.getAddressId());
		order.setPaymentID(orderDto.getPaymentId());
		order.setTotalPrice(orderDto.getTotalPrice());
		order.setOrderDetails(orderDto.getOrderItemsDtoList().stream()
				.map(orderItemsService::getOrderItemFromOrderItemDto).collect(Collectors.toList()));
		return order;
	}

	/**
	 * This method is to get details of all the orders saved in our database
	 * 
	 * @return
	 */
	@Override
	public List<Order> getAllOrders() {
		logger.info("inside getAllOrders method of OrderService");

		List<Order> allOrders = repo.findAll();
		return allOrders;

	}

	/**
	 * This method is to used to fetch orders by order id , if we enter the order id
	 * which exists , it'll
	 * 
	 * @return that particular order details otherwise
	 * @throws OrderNotFoundException along with a messgae
	 */

	@Override
	public Order getOrder(int orderId) throws OrderNotFoundException {
		logger.info("inside getOrder method of OrderService");
		Optional<Order> order = repo.findById(orderId);
		if (order.isPresent()) {
			return order.get();

		}
		throw new OrderNotFoundException("order not found");
	}

	/**
	 * This method is to track orders by list of status
	 * 
	 * @param status
	 * @return
	 */
	@Override
	public List<Order> findOrderByStatus(OrderStage status) {
		return this.repo.findAllOrderByStage(status).stream().collect(Collectors.toList());
	}

	/**
	 * This method is to change the status of particular order
	 * 
	 * @param Id
	 * @return orderstatus
	 */

	@Override
	public void changeOrderStatus(int Id) {
		logger.info("inside changeOrderStatus method of OrderService");
		Order orderStatus = this.repo.getById(Id);

		orderStatus.setStageDate(LocalDateTime.now());
		changeStatus(orderStatus);
		this.repo.save(orderStatus);
	}

	public List<OrderDto> fetchOrderByUserId(String userId) {
		logger.info("inside fetchOrderByUserId method of OrderService");
		List<Order> orderListByUser =repo.findAllOrderByUserId(userId);
		List<OrderDto> orderDtosByUserId = new ArrayList<>();
		for(Order order : orderListByUser) {
			OrderDto orderDto = getDtoFromOrder(order);
			orderDtosByUserId.add(orderDto);
		}
		return orderDtosByUserId;
	}


	private void changeStatus(Order order) {
		logger.info("inside changeStatus method of OrderService");
		order.setStage(OrderStage.values()[Arrays.asList(OrderStage.values()).indexOf(order.getStage()) + 1]);

	}
	
	public void addFeedback(FeedbackDto feedbackDto) {
		logger.info("inside addFeedback method of OrderService");
		
		 Order order =repo.findById(feedbackDto.getOrderId()).orElse(null);
		 order.setFeedback(feedbackDto.getFeedback());
		 repo.save(order);
	}

	public  OrderDto getDtoFromOrder(Order order) {
		logger.info(" getDtoFromProduct method of ProductService");
		OrderDto orderDto =new OrderDto(order);
		return orderDto;
	}

	@Override
	public List<OrderDto> listOrders() {
		logger.info("listOrders of orderService");
		List<Order> listOrders = repo.findAll();
		List<OrderDto> orderDtos = new ArrayList<>();
		for(Order order : listOrders) {
			OrderDto orderDto = getDtoFromOrder(order);
			orderDtos.add(orderDto);
		}
		return orderDtos;
	}


}
