package com.impetus.ogos.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.impetus.ogos.dto.InventoryDto;
import com.impetus.ogos.dto.InventoryItemDto;
import com.impetus.ogos.dto.InventoryProductDto;
import com.impetus.ogos.dto.ProductDto;
import com.impetus.ogos.exception.IdConflictException;
import com.impetus.ogos.exception.IdNotFoundException;
import com.impetus.ogos.model.Inventory;
import com.impetus.ogos.model.InventoryItem;
import com.impetus.ogos.repository.InventoryItemRepository;
import com.impetus.ogos.repository.InventoryRepository;

/**
 * @author mustafa.kasarawala
 *
 */
@Service
public class InventoryService 
{
	
	@Autowired
	InventoryRepository inventoryRepository;
	
	@Autowired
	InventoryItemRepository inventoryItemRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	/**
	 * @param inventoryId
	 * @param productId
	 * @return InventoryItem
	 * @throws IdNotFoundException
	 *
	 * This function takes the inventory id and product id. 
	 * checks if they exist in database or not
	 * then it fetches and returns the corresponding inventory item.
	 *
	 */
	private InventoryItem getInventoryItem (String inventoryId, int productId) throws IdNotFoundException
	{
		
		Inventory inventory = inventoryRepository.findById(inventoryId).orElseThrow(()-> {return new IdNotFoundException("Inventory id "+inventoryId+" does not exist.");});
		
		List<InventoryItem> list = inventoryItemRepository.findByInventoryAndProductId(inventory, productId);
		
		if(list.isEmpty()) throw new IdNotFoundException("Product id "+Integer.toString(productId)+" not found.");
		
		return list.get(0);
	}
	
	
	
	/**
	 * @param inventoryId
	 * @param inventoryItemDto
	 * @return inventoryItemDto
	 * @throws IdNotFoundException
	 * 
	 * This function takes the inventory id and an item dto
	 * the item dto contains information about which product id's quantity user wants to know
	 * it searches into repository for the required inventory item with inventory id and product id
	 * then it return the inventory item.
	 * 
	 * 
	 */
	public InventoryItemDto getStock(String inventoryId, int productId) throws IdNotFoundException
	{
		InventoryItem inventoryItem = getInventoryItem(inventoryId, productId);
		
		return new InventoryItemDto (productId, inventoryItem.getQuantity());
	}
	
	
	
	/**
	 * @param inventoryId
	 * @param inventoryItemDto
	 * @throws IdNotFoundException
	 * 
	 * item dto contains product id and the quantity to increase.
	 * it adds the quantity into available stock of a given product.
	 * 
	 */
	public void addStock(String inventoryId, InventoryItemDto inventoryItemDto) throws IdNotFoundException
	{
		InventoryItem inventoryItem = getInventoryItem(inventoryId,inventoryItemDto.getProductId());
		inventoryItem.addStock(inventoryItemDto.getQuantity());
		inventoryItemRepository.save(inventoryItem);
		
	}
	
	
	
	
	/**
	 * @param inventoryId
	 * @param inventoryItemDto
	 * @throws IdNotFoundException
	 * 
	 * item dto contains product id and the quantity to decrease.
	 * it decreases the quantity from available stock of a given product.
	 * 
	 */
	public void emptyStock(String inventoryId, InventoryItemDto inventoryItemDto) throws IdNotFoundException
	{
		InventoryItem inventoryItem = getInventoryItem(inventoryId,inventoryItemDto.getProductId());
		inventoryItem.emptyStock(inventoryItemDto.getQuantity());
		inventoryItemRepository.save(inventoryItem);
	}
	
	
	
	
	/**
	 * @param inventoryId
	 * @return inventoryDto
	 * @throws IdNotFoundException
	 * 
	 * returns complete list of products and their quantities for a given inventory id
	 * 
	 */
	/*public InventoryDto getInventory(String inventoryId) throws IdNotFoundException
	{
		Inventory inventory = inventoryRepository.findById(inventoryId).orElseThrow(()-> {return new IdNotFoundException("Inventory id "+inventoryId+" does not exist.");});
		
		return new InventoryDto( inventory.getInventoryId(), inventory.getInventoryItems().stream().map(InventoryItemDto::new).collect(Collectors.toList()));
	}*/
	
	private InventoryProductDto getInventoryProductDto(InventoryItem item) throws IdNotFoundException
	{
		ResponseEntity<ProductDto> response = restTemplate.getForEntity("lb://product-service/product/get-product/"+Integer.toString(item.getProductId()), ProductDto.class);
		if(response.getStatusCode() == HttpStatus.NOT_FOUND) throw new IdNotFoundException("Product Not Found.");
		ProductDto product = response.getBody();
		return new InventoryProductDto(product.getId(), product.getName(), product.getPrice(), product.getDescription(), product.getProductWeight(),
			product.getMeasurementUnit(), product.getCategoryId(), item.getQuantity());
	}
	
	public List<InventoryProductDto> getInventory() throws IdNotFoundException
	{
		return inventoryItemRepository.findAll().stream().map((item) ->{
			return getInventoryProductDto(item);
		}).collect(Collectors.toList());
	}
	
	
	
	
	/**
	 * @param productId
	 * 
	 * adds the new product id into all the inventories with quantity zero.
	 * if product id already exists then it does nothing.
	 * This function is used when a new product has been added in the ogos product database.
	 * 
	 */
	public void addProduct(int productId)
	{
		if(inventoryItemRepository.existsInventoryItemByProductId(productId)) return;
		
		inventoryRepository.findAll().stream().map((inventory) -> {
			return new InventoryItem( productId, 0, inventory);
		}).forEach(inventoryItemRepository::save);
		
	}
	
	
	
	
	/**
	 * @param productId
	 * 
	 * It deletes a given product id from all the inventories.
	 * This function is used when a product is removed from ogos product database.
	 * 
	 */
	public void deleteProduct(int productId)
	{
		inventoryItemRepository.findByProductId(productId).stream().forEach(inventoryItemRepository::delete);
	}
	
	
/*	
	
	/**
	 * @param inventoryId
	 * @param managerId
	 * @throws IdNotFoundException
	 * @throws IdConflictException
	 * 
	 * This function assigns manager to an inventory.
	 * If the manager is already assigned to any inventory it throws exception.
	 * One manager can be assigned to only one inventory at a time.
	 * 
	 
	public void addManager(String inventoryId, String managerId) throws IdNotFoundException, IdConflictException
	{
		Optional<Inventory> managerInventory = inventoryRepository.findByManagerId(managerId);
		
		if(managerInventory.isPresent()) throw new IdConflictException("manager id "+managerId+" is already assigned inventory id "+managerInventory.get().getInventoryId());
		
		Inventory inventory = inventoryRepository.findById(inventoryId).orElseThrow(()-> {return new IdNotFoundException("Inventory id "+inventoryId+" does not exist.");});
		
		inventory.setManagerId(managerId);
	}

	
	
	
	/**
	 * @param inventoryId
	 * @param deliveryPartnerId
	 * @throws IdNotFoundException
	 * @throws IdConflictException
	 * 
	 * 
	 * This function assigns delivery partner to an inventory.
	 * If the partner is already assigned to any inventory it throws exception.
	 * One delivery partner can be assigned to only one inventory at a time.
	 * 
	 * 
	 
	public void addDeliveryPartner(String inventoryId, String deliveryPartnerId) throws IdNotFoundException, IdConflictException
	{
		Optional<Inventory> deliveryPartnerInventory = inventoryRepository.findByDeliveryPartnerId(deliveryPartnerId);
		
		if(deliveryPartnerInventory.isPresent()) throw new IdConflictException("Delivery Partner id "+deliveryPartnerId+" is already assigned inventory id "+deliveryPartnerInventory.get().getInventoryId());
		
		Inventory inventory = inventoryRepository.findById(inventoryId).orElseThrow(()-> {return new IdNotFoundException("Inventory id "+inventoryId+" does not exist.");});
		
		inventory.setDeliveryPartnerId(deliveryPartnerId);
	}
	
	
	
	
	/**
	 * @param managerId
	 * @return
	 * @throws IdNotFoundException
	 * 
	 * This function returns the inventory id where the manager is assigned.
	 * 
	 
	public String getManagerInventory(String managerId) throws IdNotFoundException
	{
		Optional<Inventory> managerInventory = inventoryRepository.findByManagerId(managerId);
		
		return managerInventory.orElseThrow(()-> {
			return new IdNotFoundException("manager id "+managerId+" not assigned to any inventory.");}).getInventoryId();
	}

	
	
	
	/**
	 * @param deliveryPartnerId
	 * @return
	 * @throws IdNotFoundException
	 * 
	 * This function returns the inventory id where the delivery partner is assigned.
	 * 
	 
	public String getDeliveryPartnerInventory(String deliveryPartnerId) throws IdNotFoundException
	{
		Optional<Inventory> deliveryPartnerInventory = inventoryRepository.findByDeliveryPartnerId(deliveryPartnerId);
		
		return deliveryPartnerInventory.orElseThrow(()-> {
			return new IdNotFoundException("delivery partner id "+deliveryPartnerId+" not assigned to any inventory.");}).getInventoryId();
	}
	
*/	
	
	
	/**
	 * @param inventoryId
	 * @throws IdConflictException
	 * 
	 * 
	 * This function adds a new inventory into the system.
	 * It initializes the inventory with the quantity of all the products=0.
	 * Inventory id is decided by user and it has to be unique name of inventory
	 * for e.g. Ujjain, Indore, etc.
	 * 
	 */
	public void addInventory(String inventoryId) throws IdConflictException
	{
		if(inventoryRepository.existsById(inventoryId)) throw new IdConflictException("Inventory id "+inventoryId+" already exists");
		
		List<Inventory> list = inventoryRepository.findAll();
		
		Inventory inventory = new Inventory(inventoryId, new LinkedList<>());
		
		if(!(list.isEmpty()))
		{
			Inventory existingInventory = list.get(0);
			
			existingInventory.getInventoryItems().stream().forEach((inventoryItem) -> {
				inventory.getInventoryItems().add(new InventoryItem( inventoryItem.getProductId(), 0, inventory));
			});	
		}
		
		inventoryRepository.save(inventory);
	}

}
