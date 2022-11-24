package edu.eskisehir.teklifyap.mapper;

import edu.eskisehir.teklifyap.domain.dto.ItemDto;
import edu.eskisehir.teklifyap.domain.dto.OfferDto;
import edu.eskisehir.teklifyap.domain.dto.ShortOfferDto;
import edu.eskisehir.teklifyap.domain.model.Offer;
import org.mapstruct.Mapper;

import java.util.LinkedList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OfferMapper {

    default OfferDto toOfferDto(Offer offer) {
        OfferDto offerDto = new OfferDto();
        offerDto.setId(offer.getId());
        offerDto.setTitle(offer.getTitle());
        offerDto.setDate(offer.getDate());
        offerDto.setStatus(offer.isStatus());
        offerDto.setReceiverName(offer.getReceiverName());
        offerDto.setProfitRate(offer.getProfitRate());
        offerDto.setItems(new LinkedList<>());
        offerDto.setUserName(offer.getUserName());

        offer.getOfferItems().forEach(offerItem -> {
            offerDto.getItems().add(new ItemDto(offerItem.getItem().getId(), offerItem.getItem().getName(),
                    offerItem.getItem().getValue(), offerItem.getItem().getUnit(), offerItem.getQuantity()));
        });

        return offerDto;
    }

    List<ShortOfferDto> toShortOfferDtoList(List<Offer> allByUser);

}
