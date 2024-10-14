package ru.clevertec.application.component;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.clevertec.application.dto.OfferResponse;
import ru.clevertec.application.entity.Offer;
import ru.clevertec.application.repository.OfferRepository;
import ru.clevertec.application.service.OfferService;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ComponentTestConfiguration.class)
class ComponentTest {
    @Autowired
    private OfferService offerService;
    @Autowired
    private OfferRepository offerRepository;

    @Test
    @DisplayName("should success return offer by id /offers")
    void testGetOffersById() {
        //given
        long offerId = 1;
        Offer offer = new Offer()
                .setCategory("category#1")
                .setCode("code1")
                .setTariff(1.5);
        Mockito.when(offerRepository.findById(offerId))
                .thenReturn(Optional.of(offer));

        //when
        OfferResponse offerResponse = offerService.get(offerId);

        //then
        Assertions.assertEquals("category#1",offerResponse.offer().category());
        Assertions.assertEquals("code1",offerResponse.offer().code());
        Assertions.assertEquals(1.5,offerResponse.offer().tariff());
    }
}
