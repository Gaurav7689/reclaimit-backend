package com.reclaimit.repository;

import com.reclaimit.entity.Item;
import com.reclaimit.entity.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByItemType(ItemType itemType);
    List<Item> findByPostedByEmail(String email);

}
