package edu.eskisehir.teklifyap.service;

import edu.eskisehir.teklifyap.domain.dto.*;
import edu.eskisehir.teklifyap.domain.model.Item;
import edu.eskisehir.teklifyap.domain.model.Offer;
import edu.eskisehir.teklifyap.domain.model.OfferItem;
import edu.eskisehir.teklifyap.domain.model.User;
import edu.eskisehir.teklifyap.mapper.OfferMapper;
import edu.eskisehir.teklifyap.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class OfferService {

    @Autowired
    private OfferMapper offerMapper;
    private final OfferRepository offerRepository;
    private final ItemService itemService;

    public OfferService(OfferRepository offerRepository, ItemService itemService) {
        this.offerRepository = offerRepository;
        this.itemService = itemService;
    }

    public Offer findById(Long id) throws Exception {
        return offerRepository.findById(id).orElseThrow(() -> new Exception("Offer not found"));
    }

    public List<OfferDto> getOffers(User user) {
        return offerMapper.toOfferDtoList(offerRepository.findAllByUser(user));
    }

    public void deleteOffer(Long oid) {
        offerRepository.deleteById(oid);
    }

    public OfferDto updateOffer(Long id, UpdateOfferDto body) throws Exception {

        Offer offer = findById(id);

        if (body.getUserName() != null) {
            offer.setUserName(body.getUserName());
        }

        if (body.getReceiverName() != null) {
            offer.setReceiverName(body.getReceiverName());
        }

        if (body.getProfitRate() != offer.getProfitRate() && body.getProfitRate() != 0.0) {
            offer.setProfitRate(body.getProfitRate());
        }

        return offerMapper.toOfferDto(save(offer));
    }

    public OfferDto getOffer(Long id) throws Exception {
        return offerMapper.toOfferDto(findById(id));
    }

    public Offer save(Offer offer) {
        return offerRepository.save(offer);
    }

    public void makeOffer(MakeOfferDto makeOfferDto, User user) throws Exception {

        Offer offer = new Offer();
        offer.setUser(user);
        offer.setStatus(false);
        if (makeOfferDto.getUserName() != null) {
            offer.setUserName(makeOfferDto.getUserName());
        } else {
            offer.setUserName(user.getName() + " " + user.getSurname());
        }
        offer.setReceiverName(makeOfferDto.getReceiverName());
        offer.setProfitRate(makeOfferDto.getProfitRate());
        offer.setDate(LocalDateTime.now());
        offer.setOfferItems(new LinkedList<>());

        for (int i = 0; i < makeOfferDto.getItems().size(); i++) {
            OfferItem offerItem = new OfferItem();
            offerItem.setOffer(offer);
            offerItem.setItem(itemService.findById(makeOfferDto.getItems().get(i).getId()));
            if (makeOfferDto.getItems().get(i).getQuantity() > 0 && offerItem.getItem().getValue() > 0) {
                offerItem.setQuantity(makeOfferDto.getItems().get(i).getQuantity());
            } else {
                throw new Exception("Quantity and value must be greater than 0");
            }
            offer.getOfferItems().add(offerItem);
        }

        offerRepository.save(offer);
    }

    public void updateOfferStatus(Long id) throws Exception {
        Offer offer = findById(id);
        if (offer.getWorksite() != null) {
            offer.setStatus(!offer.isStatus());
            offerRepository.save(offer);
        } else {
            throw new Exception("Worksite is founded");
        }
    }

    public void addItemsToOffer(UpdateOfferItemDto body, Long offerId) throws Exception {

        Offer offer = findById(offerId);

        Item item = itemService.findById(body.getId());

        for (int i = 0; i < offer.getOfferItems().size(); i++) {
            if (offer.getOfferItems().get(i).getItem().getId().equals(item.getId())) {
                throw new Exception("Item already exists in offer");
            }
        }

        if (item.getValue() <= 0 || body.getQuantity() <= 0) {
            throw new Exception("Quantity and value must be greater than 0");
        }

        OfferItem offerItem = new OfferItem();
        offerItem.setOffer(offer);
        offerItem.setItem(item);
        offerItem.setQuantity(body.getQuantity());
        offer.getOfferItems().add(offerItem);
        save(offer);
    }

    public void deleteItemFromOffer(Long iid, Long oid) {
        offerRepository.deleteOfferItem(iid, oid);
    }

}
