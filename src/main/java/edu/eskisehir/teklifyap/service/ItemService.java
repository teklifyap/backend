package edu.eskisehir.teklifyap.service;

import edu.eskisehir.teklifyap.domain.dto.ItemDto;
import edu.eskisehir.teklifyap.domain.dto.ItemNameDto;
import edu.eskisehir.teklifyap.domain.model.Item;
import edu.eskisehir.teklifyap.domain.model.User;
import edu.eskisehir.teklifyap.mapper.ItemMapper;
import edu.eskisehir.teklifyap.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    public ItemService(ItemRepository itemRepository, ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }

    public ItemDto createItem(ItemDto itemDto, User user) {

        Item item = itemMapper.toItem(itemDto);
        item.setUser(user);
        item = itemRepository.save(item);
        return itemMapper.toItemDto(item);
    }

    public List<ItemNameDto> getItems(User user) {
        List<Item> all = itemRepository.findAllByUserId(user.getId());
        return itemMapper.toItemNameDto(all);
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

    public ItemDto updateItem(ItemDto itemDto) throws Exception {

        Item item = itemRepository.findById(itemDto.getId()).orElseThrow(() -> new Exception("ItemNotFound!"));
        item.setName(itemDto.getName());
        item.setValue(itemDto.getValue());
        Item save = save(item);
        return itemMapper.toItemDto(save);
    }

    protected Item save(Item item) {
        return itemRepository.save(item);
    }

    public ItemDto getItem(Long id) throws Exception {
        Item item = itemRepository.findById(id).orElseThrow(() -> new Exception("ItemNotFound!"));
        return itemMapper.toItemDto(item);
    }

}
