package com.dealalert.webapp.controllers;

import com.dealalert.webapp.common.ApiResponse;
import com.dealalert.webapp.models.Item;
import com.dealalert.webapp.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "https://localhost:4200")
@RestController
@RequestMapping("/api")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/items")
    public ResponseEntity<Map<String, Object>> getAllItems(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size)  {
        try {
            List<Item> items;
            Pageable paging = PageRequest.of(page, size);

            Page<Item> pageItems;
            if (name == null)
                pageItems = itemService.getAll(paging);
            else
                pageItems = itemService.getItemsByNameIgnoreCase(paging, name);

            items = pageItems.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("items", items);
            response.put("currentPage", pageItems.getNumber());
            response.put("totalItems", pageItems.getTotalElements());
            response.put("totalPages", pageItems.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable("id") String id) {
        Optional<Item> itemData = itemService.getItemData(id);
        return itemData.map(item -> new ResponseEntity<>(item, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //Create new product
    @PostMapping("/items")
    public ResponseEntity<ApiResponse> createItem(@RequestBody Item item) {
        try {
            itemService.createItem(item);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Item added successfully"), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Error while adding item"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/items/{id}")
    public ResponseEntity<HttpStatus> deleteItem(@PathVariable("id") String id) {
        try {
            itemService.deleteItem(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/items")
    public ResponseEntity<HttpStatus> deleteAllItems() {
        try {
            itemService.deleteAllItems();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}