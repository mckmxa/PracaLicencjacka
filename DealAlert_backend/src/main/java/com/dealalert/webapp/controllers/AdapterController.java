package com.dealalert.webapp.controllers;


import com.dealalert.webapp.models.History;
import com.dealalert.webapp.models.Item;
import com.dealalert.webapp.repository.HistoryRepository;
import com.dealalert.webapp.services.HistoryService;
import com.dealalert.webapp.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin(origins = "https://localhost:4200")
@RequestMapping(value = "/api")
@RestController
public class AdapterController {


    private final RestTemplate restTemplate;


    private final ItemService itemService;


    private final HistoryService historyService;

    @Autowired
    public AdapterController(RestTemplate restTemplate, ItemService itemService, HistoryService historyService) {
        this.restTemplate = restTemplate;
        this.itemService = itemService;
        this.historyService = historyService;
    }

    @RequestMapping(value = "/fetch")
    public ResponseEntity<Item[]> getProducts() {

        String RESOURCE_PROVIDER_URL = "http://localhost:8082/resources/fetch";
        Item[] list = restTemplate
                .getForObject(RESOURCE_PROVIDER_URL , Item[].class);



        for (Item element : list) {
            itemService.createItem(element);
            if(!historyService.alreadyExists(element.getId())) {
                History history = new History();
                history.setItemId(element.getId());
                history.addToMap(LocalDate.now(), element.getPrice());
                historyService.addHistory(history);
            } else {
                History history = historyService.readItemHistory(element.getId());
                history.addToMap(LocalDate.now(), element.getPrice());
                historyService.addHistory(history);
            }
        }

        return new ResponseEntity<>(list,HttpStatus.OK);
    }

}