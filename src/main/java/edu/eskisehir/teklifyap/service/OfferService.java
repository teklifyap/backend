package edu.eskisehir.teklifyap.service;

import edu.eskisehir.teklifyap.domain.dto.MakeOfferDto;
import edu.eskisehir.teklifyap.domain.dto.OfferDto;
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

        return null;
    }

    public void deleteOffer(Long oid) {
        offerRepository.deleteById(oid);
    }

    public OfferDto updateOffer(Long id, OfferDto offerDto) throws Exception {

        Offer offer = findById(id);

        if (offerDto.getUserName() != null) {
            offer.setUserName(offerDto.getUserName());
        }

        if (offerDto.getReceiverName() != null) {
            offer.setReceiverName(offerDto.getReceiverName());
        }

        if (offerDto.getProfitRate() != offer.getProfitRate() && offerDto.getProfitRate() != 0.0) {
            offer.setProfitRate(offerDto.getProfitRate());
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
        offer.setStatus(!offer.isStatus());
        offerRepository.save(offer);
    }
}
