package ru.clevertec.application.mock;


import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.clevertec.application.BaseIT;
import ru.clevertec.application.configuration.PostgresContainerConfiguration;
import ru.clevertec.application.entity.Offer;
import ru.clevertec.application.repository.OfferRepository;
import ru.clevertec.application.service.OfferService;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@WireMockTest(httpPort = 7777)
@ContextConfiguration(classes = PostgresContainerConfiguration.class)
class MockMvcMockitoOfferIT extends BaseIT {
    @Autowired
    private MockMvc mockMvc;
    @SpyBean
    private OfferService offerService;
    @MockBean
    private OfferRepository offerRepository;

    @Test
    @DisplayName("should success return offer by id /offers")
    void testGetOffersById() throws Exception {
        //given
        long offerId = 1;
        Offer offer = new Offer()
                .setCategory("category#1")
                .setCode("code1")
                .setTariff(1.5);
        Mockito.when(offerRepository.findById(offerId))
                .thenReturn(Optional.of(offer));

        //when/then
        mockMvc.perform(get("/api/v1/offers/{offerId}", offerId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.offer.category").value("category#1"))
                .andExpect(jsonPath("$.offer.code").value("code1"))
                .andExpect(jsonPath("$.offer.tariff").value(1.5));
        Mockito.verify(offerService, Mockito.times(1)).get(offerId);
    }

    @Test
    @DisplayName("should success delete offer by id /offers")
    void testDeleteOfferById() throws Exception {
        //given
        long offerId = 1;
        Mockito.doNothing().when(offerRepository).deleteById(offerId);

        //when/then
        MvcResult mvcResult = mockMvc.perform(delete("/api/v1/offers/{offerId}", offerId))
                .andReturn();

        Assertions.assertThat(mvcResult)
                .extracting(MvcResult::getResponse)
                .extracting(MockHttpServletResponse::getStatus)
                .isEqualTo(HttpStatus.NO_CONTENT.value());
        Mockito.verify(offerService, Mockito.times(1)).delete(offerId);
    }
}
