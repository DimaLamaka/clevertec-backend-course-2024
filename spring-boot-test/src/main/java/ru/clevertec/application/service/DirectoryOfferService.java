package ru.clevertec.application.service;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.clevertec.application.dto.DirectoryOffer;
import ru.clevertec.application.dto.DirectoryOfferRequest;
import ru.clevertec.application.dto.DirectoryOfferResponse;
import ru.clevertec.application.dto.directory.*;
import ru.clevertec.application.exception.BusinessServiceException;
import ru.clevertec.application.gateway.DirectoryGateway;
import ru.clevertec.application.mapper.DirectoryOfferMapper;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DirectoryOfferService {
    private final DirectoryGateway directoryGateway;
    private final DirectoryOfferMapper directoryOfferMapper;
    private final Gson gson;

    public DirectoryOfferResponse getDirectoryOffers(DirectoryOfferRequest request) {
        return Optional.of(request)
                .map(this::getShortInfoOffers)
                .map(DirectoryShortInfoOffersResponse::directoryOffers)
                .map(this::toOfferCodes)
                .map(this::getFullInfoOffers)
                .map(DirectoryFullInfoOffersResponse::directoryOffers)
                .map(directoryOfferMapper::toDirectoryOffers)
                .map(DirectoryOfferResponse::new)
                .orElseThrow(() -> new BusinessServiceException("Can't get offers from directory"));
    }

    private DirectoryShortInfoOffersResponse getShortInfoOffers(DirectoryOfferRequest request) {
        DirectoryShortInfoOffersRequest shortInfoRequest = new DirectoryShortInfoOffersRequest(request.directoryName());
        log.debug("Trying to get short info offers, with request: {}", gson.toJson(shortInfoRequest));
        DirectoryShortInfoOffersResponse shortInfoResponse = directoryGateway.getShortInfoOffers(shortInfoRequest);
        log.debug("Directory service returned short info offers response: {}", gson.toJson(shortInfoResponse));
        return shortInfoResponse;
    }

    private DirectoryFullInfoOffersResponse getFullInfoOffers(Set<String> codes) {
        if (codes.isEmpty()) {
            log.debug("Codes are empty. Returned default response");
            return new DirectoryFullInfoOffersResponse(Collections.emptyList());
        }
        DirectoryFullInfoOffersRequest fullInfoRequest = new DirectoryFullInfoOffersRequest(codes);
        log.debug("Trying to get full info offers, with request: {}", gson.toJson(fullInfoRequest));
        DirectoryFullInfoOffersResponse fullInfoResponse = directoryGateway.getFullInfoOffers(fullInfoRequest);
        log.debug("Directory service returned full info offers response: {}", gson.toJson(fullInfoResponse));
        return fullInfoResponse;
    }

    private Set<String> toOfferCodes(List<DirectoryShortInfoOffer> shortInfoOffers) {
        return shortInfoOffers.stream()
                .filter(offer -> this.isDateNowBetween(offer.startDate(), offer.endDate()))
                .map(DirectoryShortInfoOffer::code)
                .collect(Collectors.toSet());
    }

    private boolean isDateNowBetween(LocalDate startDate, LocalDate endDate) {
        if (Objects.isNull(startDate) || Objects.isNull(endDate)) {
            return false;
        }
        LocalDate dateNow = LocalDate.now();
        return dateNow.isAfter(startDate) && dateNow.isBefore(endDate);
    }

}
