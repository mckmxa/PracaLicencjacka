package com.dealalert.resource_adapter.controllers;

import com.dealalert.resource_adapter.models.Bestseller;
import com.dealalert.resource_adapter.models.Item;
import com.dealalert.resource_adapter.models.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;

@RequestMapping(value = "/resources")
@RestController
public class AdapterController {

    @Value("${rainforest.api.key}")
    private String API_KEY;

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value = "/fetch")
    public ResponseEntity<List<Item>> getProducts() {
        Bestseller[] bestsellers;
        String resourceUrl = "https://api.rainforestapi.com/request?api_key=";
        String params = "&type=bestsellers&amazon_domain=amazon.com&category_id=bestsellers_amazon_devices";
        Response response = restTemplate
                .getForObject(resourceUrl + API_KEY + params, Response.class);

        bestsellers = response.getBestsellers();

        List<Item> itemsList = new ArrayList<>();

        for(int i = 0; i < bestsellers.length; i++){
            Item item = new Item();
            item.setId(bestsellers[i].getAsin());
            item.setImageURL(bestsellers[i].getImage());
            if(bestsellers[i].getPrice() != null) {
                item.setPrice(bestsellers[i].getPrice().getValue());
            }
            item.setName(bestsellers[i].getTitle());
            itemsList.add(item);
        }

        return new ResponseEntity<List<Item>>(itemsList, HttpStatus.OK);
    }
}

