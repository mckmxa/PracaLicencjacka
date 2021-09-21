package com.dealalert.webapp.repository;

import com.dealalert.webapp.models.Item;
import com.dealalert.webapp.models.WishList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository extends MongoRepository<WishList, String> {
    WishList findByUsername(String username);
    void deleteAllByUsername(String username);
    boolean existsByUsername(String username);
    void deleteByUsername(String username);



}
