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

    public OfferDto updateOffer(OfferDto offerDto) {

        return null;
    }

    public OfferDto getOffer(Long id) throws Exception {
        return offerMapper.toOfferDto(findById(id));
    }

    public OfferDto save(Offer offer) {
        return null;
    }

    public void makeOffer(MakeOfferDto makeOfferDto, User user) throws Exception {

        Offer offer = new Offer();
        offer.setUser(user);
        offer.setStatus(false);
        offer.setUserName(user.getName());
        offer.setProfitRate(makeOfferDto.getProfitRate());
        offer.setReceiverName(makeOfferDto.getReceiverName());
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
