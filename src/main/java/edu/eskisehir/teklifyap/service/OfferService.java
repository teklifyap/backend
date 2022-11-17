package edu.eskisehir.teklifyap.service;

import edu.eskisehir.teklifyap.domain.dto.MakeOfferDto;
import edu.eskisehir.teklifyap.domain.dto.OfferDto;
import edu.eskisehir.teklifyap.domain.model.Offer;
import edu.eskisehir.teklifyap.domain.model.User;
import edu.eskisehir.teklifyap.repository.OfferRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferService {

    private OfferRepository offerRepository;

    public OfferService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    public OfferService() {
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


    }

}
