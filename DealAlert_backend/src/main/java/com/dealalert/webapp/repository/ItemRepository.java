package com.dealalert.webapp.repository;

import com.dealalert.webapp.models.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends MongoRepository<Item, String> {
    Page<Item> findByNameContainingIgnoreCase(String name, Pageable pageable);

}

