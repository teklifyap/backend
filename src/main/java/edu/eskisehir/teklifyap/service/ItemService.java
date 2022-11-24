package edu.eskisehir.teklifyap.service;

import edu.eskisehir.teklifyap.domain.dto.ItemDto;
import edu.eskisehir.teklifyap.domain.dto.ItemNameDto;
import edu.eskisehir.teklifyap.domain.model.Item;
import edu.eskisehir.teklifyap.domain.model.User;
import edu.eskisehir.teklifyap.mapper.ItemMapper;
import edu.eskisehir.teklifyap.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ItemService {

    @Autowired
    private ItemMapper itemMapper;
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public ItemDto createItem(ItemDto itemDto, User user) {

        Item item = itemMapper.toItem2(itemDto);
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

    public Item findById(Long id) {
        return itemRepository.findById(id).orElse(null);
    }

}
