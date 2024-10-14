package ru.clevertec.application.controller;

import com.google.gson.Gson;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.application.dto.*;
import ru.clevertec.application.service.DirectoryOfferService;
import ru.clevertec.application.service.OfferService;

@Slf4j
@RestController
@RequestMapping("api/v1/offers")
@RequiredArgsConstructor
public class OffersController {
    private final OfferService offerService;
    private final DirectoryOfferService directoryOfferService;
    private final Gson gson;

    @GetMapping
    public ResponseEntity<CommittedOffersResponse> getOffers() {
        log.info("GET /offers");
        CommittedOffersResponse committedOffersResponse = offerService.getAll();
        log.info("RESPONSE GET /offers: \n{}", gson.toJson(committedOffersResponse));
        return ResponseEntity.ok(committedOffersResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfferResponse> getOfferById(@PathVariable @NotBlank Long id) {
        log.info("RESPONSE GET /offers/{id}: {}", id);
        OfferResponse offerResponse = offerService.get(id);
        log.info("RESPONSE GET /offers/{id}: \n{}", gson.toJson(offerResponse));
        return ResponseEntity.ok(offerResponse);
    }

    @PostMapping
    public ResponseEntity<SaveOfferResponse> saveOffer(@Validated @RequestBody SaveOfferRequest request) {
        log.info("POST /offers with request: \n{}", gson.toJson(request));
        SaveOfferResponse saveOfferResponse = offerService.save(request);
        log.info("RESPONSE POST /offers: \n{}", gson.toJson(saveOfferResponse));
        return ResponseEntity.ok(saveOfferResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOfferById(@PathVariable @NotBlank Long id) {
        log.info("DELETE /offers/{id} by id: {}", id);
        offerService.delete(id);
        log.info("RESPONSE DELETE /offers/{id} offer with uuid: {} deleted", id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/directory")
    public ResponseEntity<DirectoryOfferResponse> getDirectoryOffers(@Validated @RequestBody DirectoryOfferRequest request) {
        log.info("GET /offers/directory");
        DirectoryOfferResponse response = directoryOfferService.getDirectoryOffers(request);
        log.info("RESPONSE GET /offers/directory: \n{}", gson.toJson(response));
        return ResponseEntity.ok(response);
    }
}
