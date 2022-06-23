package com.impetus.ogos.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.impetus.ogos.dto.InventoryDto;
import com.impetus.ogos.dto.InventoryItemDto;
import com.impetus.ogos.dto.InventoryProductDto;
//import com.impetus.ogos.productmodule.Dto.ProductListDto;
import com.impetus.ogos.service.InventoryService;

@RestController
@RequestMapping("/inventory")
public class InventoryController 
{
	
	@Autowired
	private InventoryService inventoryService;
	
	@GetMapping("/get-stock/{inventoryId}/{productId}")
	public InventoryItemDto getStock(@PathVariable String inventoryId, @PathVariable String productId)
	{
		return inventoryService.getStock(inventoryId, Integer.parseInt(productId));
	}
	
	@PutMapping("/empty-stock/{inventoryId}")
	public void emptyStock(@PathVariable String inventoryId, @RequestBody InventoryItemDto inventoryItemDto)
	{
		inventoryService.emptyStock(inventoryId, inventoryItemDto);
	}
	
	@PutMapping("/add-stock/{inventoryId}")
	public void addStock(@PathVariable String inventoryId, @RequestBody InventoryItemDto inventoryItemDto)
	{
		inventoryService.addStock(inventoryId, inventoryItemDto);
	}
	
	/*@GetMapping("/get-inventory/{inventoryId}")
	public InventoryDto getInventory(@PathVariable String inventoryId)
	{
		return inventoryService.getInventory(inventoryId);
	}*/
	
	@GetMapping("/get-inventory")
	public List<InventoryProductDto> getInventory()
	{
		return inventoryService.getInventory();
	}
	
	@PostMapping("/add-product/{productId}")
	public void addProduct(@PathVariable Integer productId)
	{	
		inventoryService.addProduct(productId);
	}
	
	@DeleteMapping("/delete-product/{productId}")
	public void deleteProduct(@PathVariable Integer productId)
	{
		inventoryService.deleteProduct(productId);
	}
	
/*	
	@PutMapping("/add-manager/{inventoryId}/{managerId}")
	public void addManager(@PathVariable String inventoryId, @PathVariable String managerId )
	{
		inventoryService.addManager(inventoryId, managerId);
	}
	
	@PutMapping("/add-delivery-partner/{inventoryId}/{deliveryPartnerId}")
	public void addDeliveryPartner(@PathVariable String inventoryId, @PathVariable String deliveryPartnerId)
	{
		inventoryService.addDeliveryPartner(inventoryId, deliveryPartnerId);
	}
	
	@GetMapping("/get-manager-inventory/{managerId}")
	public String getManagerInventory(@PathVariable String managerId)
	{
		return inventoryService.getManagerInventory(managerId);
	}
	
	@GetMapping("/get-delivery-parnter-inventory/{deliveryPartnerId}")
	public String getDeliveryParnterInventory(@PathVariable String deliveryPartnerId)
	{
		return inventoryService.getDeliveryPartnerInventory(deliveryPartnerId);
	}
	
*/
	@PostMapping("/add-inventory/{inventoryId}")
	public void addInventory(@PathVariable String inventoryId)
	{
		inventoryService.addInventory(inventoryId);
	}
	
}
