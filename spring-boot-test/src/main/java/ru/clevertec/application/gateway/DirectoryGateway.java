package ru.clevertec.application.gateway;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import ru.clevertec.application.dto.directory.DirectoryFullInfoOffersRequest;
import ru.clevertec.application.dto.directory.DirectoryFullInfoOffersResponse;
import ru.clevertec.application.dto.directory.DirectoryShortInfoOffersRequest;
import ru.clevertec.application.dto.directory.DirectoryShortInfoOffersResponse;

@FeignClient(name = "directoryGateway",
        url = "${directory.gateway.url}")
public interface DirectoryGateway {

    @PostMapping(path = "${directory.gateway.getshortinfooffers.endpoint}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    DirectoryShortInfoOffersResponse getShortInfoOffers(DirectoryShortInfoOffersRequest request);

    @PostMapping(path = "${directory.gateway.getfullinfooffers.endpoint}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    DirectoryFullInfoOffersResponse getFullInfoOffers(DirectoryFullInfoOffersRequest request);
}
