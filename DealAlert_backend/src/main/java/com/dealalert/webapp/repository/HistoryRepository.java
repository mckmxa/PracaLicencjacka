package com.dealalert.webapp.repository;

import com.dealalert.webapp.models.History;
import com.dealalert.webapp.models.WishList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends MongoRepository<History, String>  {

    History findByItemId(String id);
    boolean existsByItemId(String itemId);
}
