package com.dealalert.webapp.services;

import com.dealalert.webapp.models.History;
import com.dealalert.webapp.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class HistoryService {

    private final HistoryRepository historyRepository;

    @Autowired
    public HistoryService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public void addHistory(History history) {
        historyRepository.save(history);
    }

    public History readItemHistory(String id) {
        return historyRepository.findByItemId(id);
    }

    public boolean alreadyExists(String id) { return historyRepository.existsByItemId(id); }
}