package edu.eskisehir.teklifyap.service;

import edu.eskisehir.teklifyap.domain.dto.MakeOfferDto;
import edu.eskisehir.teklifyap.domain.dto.OfferDto;
import edu.eskisehir.teklifyap.domain.model.Item;
import edu.eskisehir.teklifyap.domain.model.Offer;
import edu.eskisehir.teklifyap.domain.model.OfferItem;
import edu.eskisehir.teklifyap.domain.model.User;
import edu.eskisehir.teklifyap.mapper.ItemMapper;
import edu.eskisehir.teklifyap.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OfferService {

    @Autowired
    private ItemMapper itemMapper;
    private final OfferRepository offerRepository;
    private final ItemService itemService;

    public OfferService(OfferRepository offerRepository, ItemService itemService) {
        this.offerRepository = offerRepository;
        this.itemService = itemService;
    }

    public List<OfferDto> getOffers(User user) {

        return null;
    }

    public void deleteOffer(Long uid) {

    }

    public OfferDto updateOffer(OfferDto offerDto) {

        return null;
    }

    public OfferDto getOffer(User user) {

        return null;
    }

    public OfferDto save(Offer offer) {
        return null;
    }

    public void makeOffer(MakeOfferDto makeOfferDto, User user) {

        Offer offer = new Offer();
        offer.setUser(user);
        offer.setStatus(false);
        offer.setUserName(user.getName());
        offer.setProfitRate(makeOfferDto.getProfitRate());
        offer.setReceiverName(makeOfferDto.getReceiverName());
        offer.setDate(LocalDateTime.now());
        offer.setOfferItems(makeOfferDto.getItems().stream().map(itemsDto -> {
            OfferItem offerItem = new OfferItem();
            offerItem.setOffer(offer);
            offerItem.setQuantity(itemsDto.getQuantity());
            Item item = itemService.findById(itemsDto.getId());
            offerItem.setItem(item);
            return offerItem;
        }).toList());

        offerRepository.save(offer);
    }

}
