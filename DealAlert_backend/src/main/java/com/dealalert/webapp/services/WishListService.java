package com.dealalert.webapp.services;

import java.util.List;

import com.dealalert.webapp.models.WishList;
import com.dealalert.webapp.repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class WishListService {

    private final WishListRepository wishListRepository;

    @Autowired
    public WishListService(WishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    //Create Wishlist
    public void createWishlist(WishList wishList) {
        wishListRepository.save(wishList);
    }

    //ReadWishlist
    public WishList readWishList(String username) {
        return wishListRepository.findByUsername(username);
    }


    public void deleteOne(String id, String username) {
        wishListRepository.deleteByUsername(username);
    }

    public void deleteAllByUsername(String username) {
        wishListRepository.deleteAllByUsername(username);
    }
}