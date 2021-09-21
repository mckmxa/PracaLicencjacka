package com.dealalert.webapp.repository;

import com.dealalert.webapp.models.Alert;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertRepository extends MongoRepository<Alert, String> {
    boolean existsByUsernameAndItemId(String username, String id);
    boolean existsByStatus(boolean status);
    List<Alert> findAllByStatus(boolean status);
    Alert findByUsernameAndItemId(String username, String itemId);
    void deleteAllByUsername(String username);




}
