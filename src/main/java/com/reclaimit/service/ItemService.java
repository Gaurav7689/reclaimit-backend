package com.reclaimit.service;

import com.reclaimit.entity.Item;
import com.reclaimit.entity.ItemStatus;
import com.reclaimit.entity.ItemType;
import com.reclaimit.entity.User;
import com.reclaimit.repository.ItemRepository;
import com.reclaimit.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public ItemService(ItemRepository itemRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    public Item createItem(Item item, String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        item.setPostedBy(user);
        item.setStatus(ItemStatus.OPEN);
        item.setPostedDate(LocalDate.now());

        return itemRepository.save(item);
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public List<Item> getItemsByType(ItemType type) {
        return itemRepository.findByItemType(type);
    }
}
