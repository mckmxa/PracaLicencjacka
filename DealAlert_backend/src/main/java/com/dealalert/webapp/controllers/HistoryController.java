package com.dealalert.webapp.controllers;


import com.dealalert.webapp.common.ApiResponse;
import com.dealalert.webapp.models.History;
import com.dealalert.webapp.repository.HistoryRepository;
import com.dealalert.webapp.repository.ItemRepository;
import com.dealalert.webapp.services.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;


@CrossOrigin(origins = "https://localhost:4200")
@RestController
@RequestMapping("/api")
public class HistoryController {


    private HistoryRepository historyRepository;
    private ItemRepository itemRepository;
    private HistoryService historyService;

    @Autowired
    public HistoryController(HistoryRepository historyRepository, ItemRepository itemRepository, HistoryService historyService) {
        this.historyRepository = historyRepository;
        this.itemRepository = itemRepository;
        this.historyService = historyService;
    }


    @GetMapping(value="/history/{itemId}")
    public ResponseEntity<List<History>> getPriceHistory(@PathVariable String itemId) {
        History history = new History();
        if(historyRepository.existsByItemId(itemId)){
            history = historyService.readItemHistory(itemId);
            List<History> result = new ArrayList<>();
            result.add(history);
            return new ResponseEntity<List<History>>(result, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }





    }

    // USED IN TESTS ONLY //

/*
    @PostMapping("/history/{itemId}")
    public ResponseEntity<ApiResponse> addHistory(@PathVariable String itemId) {

            History history;
            LocalDate now = LocalDate.now();


            if (!historyRepository.existsByItemId(itemId)) {
                history = new History(itemId);

            } else {
                history = historyRepository.findByItemId(itemId);
            }

            if (itemRepository.findById(itemId).isPresent()) {
                double price = itemRepository.findById(itemId).get().getPrice();


                history.addToMap(now, price);

                historyService.addHistory(history);
                return new ResponseEntity<ApiResponse>(new ApiResponse(true, "History added successfully"), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid itemId"), HttpStatus.BAD_REQUEST);
            }



    }
*/
}
