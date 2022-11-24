package edu.eskisehir.teklifyap.mapper;

import edu.eskisehir.teklifyap.domain.dto.ItemDto;
import edu.eskisehir.teklifyap.domain.dto.ItemNameDto;
import edu.eskisehir.teklifyap.domain.dto.MakeOfferItemsDto;
import edu.eskisehir.teklifyap.domain.model.Item;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    Item toItem(ItemDto itemDto);

    Item toItem(MakeOfferItemsDto itemDto);

    ItemDto toItemDto(Item item);

    List<ItemDto> toItemDto(List<Item> item);

    List<ItemNameDto> toItemNameDto(List<Item> item);

}
