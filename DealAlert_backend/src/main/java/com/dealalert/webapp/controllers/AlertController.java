
package com.dealalert.webapp.controllers;

import com.dealalert.webapp.common.ApiResponse;
import com.dealalert.webapp.models.Alert;
import com.dealalert.webapp.models.Item;
import com.dealalert.webapp.models.WishList;
import com.dealalert.webapp.repository.ItemRepository;
import com.dealalert.webapp.security.token.JwtUtil;
import com.dealalert.webapp.services.AlertService;
import com.dealalert.webapp.services.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "https://localhost:4200")
@RestController
@RequestMapping("/api")
public class AlertController {


    private final JwtUtil jwtutil;

    private final ItemRepository itemRepository;

    private final AlertService alertService;

    private final WishListService wishListService;

    @Autowired
    public AlertController(JwtUtil jwtutil, ItemRepository itemRepository, AlertService alertService, WishListService wishListService) {
        this.jwtutil = jwtutil;
        this.itemRepository = itemRepository;
        this.alertService = alertService;
        this.wishListService = wishListService;
    }


    @GetMapping("/alerts")
    public ResponseEntity<List<Alert>> getAlertsData(@RequestHeader("Authorization") String jwt) {
        //get username from token?
        String username = jwtutil.getUserNameFromJwtToken(jwtutil.parseJwt(jwt));


        //get user's wishlist
        WishList wishList = wishListService.readWishList(username);

        //whole item
        List<String> ids = new ArrayList<String>();
        if(wishList != null){
            ids.addAll(wishList.getItemIdMap().keySet());
        }

        // instead of findallby to avoid unwanted sorting
        List<Alert> result = new ArrayList<>();
        for (String id : ids) {
            if(alertService.exists(username,id)) {
                result.add(alertService.getAlert(username, id));
            }

        }

        return new ResponseEntity<List<Alert>>(result, HttpStatus.OK);
    }


    @PostMapping(value="/alerts/{itemId}") // http://localhost:8080/api/alert/itemId?valueAlert=2000&enableDisable=true
    public ResponseEntity<ApiResponse> enableAlertPrices(@PathVariable String itemId, @RequestParam Double valueAlert, @RequestHeader("Authorization") String jwt) {
        String username = jwtutil.getUserNameFromJwtToken(jwtutil.parseJwt(jwt));
        String email = jwtutil.getEmailFromJwtToken(jwtutil.parseJwt(jwt));

            Optional<Item> item = itemRepository.findById(itemId);

            if (item.isPresent()) {

                if (valueAlert < item.get().getPrice() && valueAlert > 0) { // check if alert price is valid
                    alertService.enableAlerts(username, email, itemId, valueAlert);
                    return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Enabled monitoring successfully"), HttpStatus.OK);

                } else {
                    return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Enabling monitoring failed : invalid alert price"), HttpStatus.BAD_REQUEST);
                }

            } else {
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid itemId"), HttpStatus.BAD_REQUEST);
            }

    }

    @DeleteMapping(value="/alerts/{itemId}") // http://localhost:8080/api/alert/itemId?valueAlert=2000&enableDisable=true
    public ResponseEntity<ApiResponse> disableAlertPrices(@PathVariable String itemId, @RequestHeader("Authorization") String jwt) {
        String username = jwtutil.getUserNameFromJwtToken(jwtutil.parseJwt(jwt));
        if (alertService.exists(username,itemId)) {
            alertService.disableAlerts(username, itemId);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Deleted alert successfully"), HttpStatus.OK);
        } else {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "There is no such alert!"), HttpStatus.BAD_REQUEST);
        }


    }

    @GetMapping(value="/alerts/{itemId}")
    public ResponseEntity<Double> getAlertPrice(@PathVariable String itemId, @RequestHeader("Authorization") String jwt) {
        String username = jwtutil.getUserNameFromJwtToken(jwtutil.parseJwt(jwt));
        Alert alert = alertService.getAlert(username, itemId);

        return new ResponseEntity<>(alert.getAlertPrice(), HttpStatus.OK);
    }

}

