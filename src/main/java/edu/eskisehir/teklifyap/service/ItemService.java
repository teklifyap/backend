package edu.eskisehir.teklifyap.service;

import edu.eskisehir.teklifyap.domain.dto.ItemDto;
import edu.eskisehir.teklifyap.domain.model.Item;
import edu.eskisehir.teklifyap.domain.model.User;
import edu.eskisehir.teklifyap.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public ItemDto createItem(ItemDto itemDto, User user) {

        Item item = Item.builder()
                .value(itemDto.getValue())
                .name(itemDto.getName())
                .unit(itemDto.getUnit())
                .user(user)
                .build();
        item = itemRepository.save(item);
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .value(item.getValue())
                .build();
    }

    protected Item create(ItemDto itemDto) {
        Item item = Item.builder()
                .value(itemDto.getValue())
                .name(itemDto.getName())
                .build();
        return itemRepository.save(item);
    }
}
