package edu.eskisehir.teklifyap.service;

import edu.eskisehir.teklifyap.domain.dto.ItemDto;
import edu.eskisehir.teklifyap.domain.model.Item;
import edu.eskisehir.teklifyap.domain.model.User;
import edu.eskisehir.teklifyap.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public ItemDto createItem(ItemDto itemDto, User user) {

//        Item item = itemMappeer.toItem(itemDto);
//        item.setUser(user);
//        item = itemRepository.save(item);
//        return itemMappeer.toItemDto(item);
        return null;
    }

    public List<ItemDto> getItems(User user) {
        List<Item> all = itemRepository.findAllByUserId(user.getId());
//        return itemMappeer.toItemDto(all);
        return null;
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

    public ItemDto updateItem(ItemDto itemDto) throws Exception {

        Item item = itemRepository.findById(itemDto.getId()).orElseThrow(() -> new Exception("ItemNotFound!"));
        item.setName(itemDto.getName());
        item.setValue(itemDto.getValue());
        Item save = save(item);
//        return itemMappeer.toItemDto(save);
        return null;
    }

    protected Item save(Item item) {
        return itemRepository.save(item);
    }

    public ItemDto getItem(Long id) throws Exception {
        Item item = itemRepository.findById(id).orElseThrow(() -> new Exception("ItemNotFound!"));
//        return itemMappeer.toItemDto(item);
        return null;
    }

}
