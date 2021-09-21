package com.dealalert.webapp.services;

import com.dealalert.webapp.models.Item;
import com.dealalert.webapp.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }


    public Optional<Item> getItemData(String id) {
        return itemRepository.findById(id);
    }

    public void createItem(Item item) {
            itemRepository.save(new Item(item.getId(), item.getName(), item.getImageURL(),
                    item.getPrice(), item.getDescription()));
    }

    public void deleteItem(String id) {
        itemRepository.deleteById(id);
    }


    public void deleteAllItems() {
        itemRepository.deleteAll();
    }

    public Page<Item> getAll(Pageable paging) {
        return itemRepository.findAll(paging);
    }

    public Page<Item> getItemsByNameIgnoreCase(Pageable paging, String name) {
        return itemRepository.findByNameContainingIgnoreCase(name, paging);
    }

    public Iterable<Item> getAllByIds(List<String> ids) {
        return itemRepository.findAllById(ids);
    }

    public Optional<Item>getOne(String id) {
        return itemRepository.findById(id);
    }
}

