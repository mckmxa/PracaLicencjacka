package com.dealalert.webapp.controllers;

import com.dealalert.webapp.common.ApiResponse;
import com.dealalert.webapp.models.Item;
import com.dealalert.webapp.models.WishList;
import com.dealalert.webapp.repository.AlertRepository;
import com.dealalert.webapp.repository.WishListRepository;
import com.dealalert.webapp.security.token.JwtUtil;
import com.dealalert.webapp.services.AlertService;
import com.dealalert.webapp.services.ItemService;
import com.dealalert.webapp.services.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "https://localhost:4200")
@RestController
@RequestMapping("/api")
public class WishListController {


    private final JwtUtil jwtutil;

    private final ItemService itemService;

    private final WishListService wishListService;

    private final WishListRepository wishListRepository;

    private final AlertRepository alertRepository;


    @Autowired
    public WishListController(JwtUtil jwtutil, ItemService itemService, WishListService wishListService, WishListRepository wishListRepository, AlertRepository alertRepository) {
        this.jwtutil = jwtutil;
        this.itemService = itemService;
        this.wishListService = wishListService;
        this.wishListRepository = wishListRepository;
        this.alertRepository = alertRepository;

    }


    @GetMapping("/wishlist")
    public ResponseEntity<List<Optional<Item>>>  getWishList(@RequestHeader("Authorization") String jwt) {
        //get username from token?
        String username = jwtutil.getUserNameFromJwtToken(jwtutil.parseJwt(jwt));


        //get user's wishlist
        WishList wishList = wishListService.readWishList(username);

        //whole item
        List<String> ids = new ArrayList<String>();
        if(wishList != null){
            ids.addAll(wishList.getItemIdMap().keySet()); // ids.addAll(wishList.getItemIdList().keySet());
        }

        // instead of findallby to avoid unwanted sorting
        List<Optional<Item>> result = new ArrayList<>();
        for (String id : ids) {
            result.add(itemService.getOne(id));
        }

        //Iterable<Item> itemsInfo = itemService.getAllByIds(ids);
        //List<Item> result = new ArrayList<>();
        //itemsInfo.forEach(result::add);



        return new ResponseEntity<List<Optional<Item>>>(result, HttpStatus.OK);
    }




    @GetMapping("/wishlist/status")
    public ResponseEntity<List<Boolean>>  getItemsStatus(@RequestHeader("Authorization") String jwt) {
        //get username from token?
        String username = jwtutil.getUserNameFromJwtToken(jwtutil.parseJwt(jwt));


        //get user's wishlist
        WishList wishList = wishListService.readWishList(username);

        //whole item
        List<String> ids = new ArrayList<String>();
        if(wishList != null){
            ids.addAll(wishList.getItemIdMap().keySet()); // ids.addAll(wishList.getItemIdList().keySet());
        }

        List<Boolean> statusList = new ArrayList<>();
        if(wishList != null) {
            for (String key: wishList.getItemIdMap().keySet()) {
                statusList.add(wishList.getItemIdMap().get(key));
            }
        }

        return new ResponseEntity<List<Boolean>>(statusList, HttpStatus.OK);
    }


    @PostMapping("/wishlist")
    public ResponseEntity<ApiResponse> addToWishlist(@RequestBody String itemId, @RequestHeader("Authorization") String jwt) {
        String username = jwtutil.getUserNameFromJwtToken(jwtutil.parseJwt(jwt));
        Map<String, Boolean> itemIdMap = new HashMap<>();
        WishList wishList;

        if (!wishListRepository.existsByUsername(username)) {
            itemIdMap.put(itemId, false);
            wishList = new WishList(username, itemIdMap); // wishList = new WishList(username, itemIdMap);

        } else {
            wishList = wishListService.readWishList(username);
            Set<String> tmp = wishList.getItemIdMap().keySet();
            if(!tmp.contains(itemId)){
                wishList.addToMap(itemId, false); //wishList.AddToMap(itemId, false);

            } else {
                return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Item already on wishlist"), HttpStatus.CONFLICT);
            }
        }

        wishListService.createWishlist(wishList);


        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Added to wishlist"), HttpStatus.CREATED);
    }



    @DeleteMapping("/wishlist")
    public ResponseEntity<ApiResponse> deleteAllItems(@RequestHeader("Authorization") String jwt) {
        try {
            String username = jwtutil.getUserNameFromJwtToken(jwtutil.parseJwt(jwt));
            wishListService.deleteAllByUsername(username);
            //clear alerts for that user too
            alertRepository.deleteAllByUsername(username);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "List cleared successfully"), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Error while clearing list"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @DeleteMapping("/wishlist/{id}")
    public ResponseEntity<ApiResponse> deleteItem(@PathVariable("id") String itemId, @RequestHeader("Authorization") String jwt) {
        try {
            String username = jwtutil.getUserNameFromJwtToken(jwtutil.parseJwt(jwt));
            WishList wishList = wishListRepository.findByUsername(username);
            wishList.removeFromMap(itemId);
            wishListService.createWishlist(wishList);

            //delete alert if existing
            if(alertRepository.existsByUsernameAndItemId(username,itemId)){
                alertRepository.delete(alertRepository.findByUsernameAndItemId(username, itemId));
            }


            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Item deleted successfully"), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Error while deleting item"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}