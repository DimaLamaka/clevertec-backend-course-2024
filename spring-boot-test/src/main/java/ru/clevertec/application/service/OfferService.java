package ru.clevertec.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.application.dto.*;
import ru.clevertec.application.entity.Offer;
import ru.clevertec.application.exception.BusinessServiceException;
import ru.clevertec.application.mapper.OfferMapper;
import ru.clevertec.application.repository.OfferRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OfferService {
    private final OfferRepository offerRepository;
    private final OfferMapper offerMapper;

    public CommittedOffersResponse getAll() {
        List<OfferDto> offers = offerRepository.findAll()
                .stream()
                .map(offerMapper::toOfferDto)
                .toList();
        return new CommittedOffersResponse(offers);
    }

    public OfferResponse get(Long id) {
        return offerRepository.findById(id)
                .map(offerMapper::toOfferDto)
                .map(OfferResponse::new)
                .orElseThrow(() -> new BusinessServiceException("Offer not found", HttpStatus.NOT_FOUND));
    }

    @Transactional
    public SaveOfferResponse save(SaveOfferRequest request) {
        return Optional.of(request)
                .map(SaveOfferRequest::offer)
                .map(offerMapper::toOffer)
                .map(offerRepository::save)
                .map(Offer::getId)
                .map(SaveOfferResponse::new)
                .orElseThrow(() -> new BusinessServiceException("Save offer error"));
    }

    @Transactional
    public void delete(Long id) {
        offerRepository.deleteById(id);
    }
}
