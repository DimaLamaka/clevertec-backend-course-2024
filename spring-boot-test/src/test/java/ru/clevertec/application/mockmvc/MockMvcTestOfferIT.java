package ru.clevertec.application.mockmvc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.application.dto.OfferDto;
import ru.clevertec.application.dto.OfferResponse;
import ru.clevertec.application.service.DirectoryOfferService;
import ru.clevertec.application.service.OfferService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.clevertec.application.util.FileReaderUtil.readFile;

@WebMvcTest
@ContextConfiguration(classes = MockMvcTestConfiguration.class)
class MockMvcTestOfferIT {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OfferService offerService;
    @MockBean
    private DirectoryOfferService directoryOfferService;

    @Test
    @DisplayName("should success return all offers /offers")
    void testGetAllOffers() throws Exception {
        //given

        String expectedResponse = readFile("/offers/get-all-committed-offers-response.json");

        //when/then
        mockMvc.perform(get("/api/v1/offers"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("should success return offer by id /offers")
    void testGetOffersById() throws Exception {
        //given
        long offerId = 1;
        Mockito.when(offerService.get(offerId))
                .thenReturn(
                        new OfferResponse(
                                new OfferDto("code1", "category#1", "title1", "descr", "cond", 1.5)
                        )
                );

        //when/then
        mockMvc.perform(get("/api/v1/offers/{offerId}", offerId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.offer.category").value("category#1"))
                .andExpect(jsonPath("$.offer.code").value("code1"))
                .andExpect(jsonPath("$.offer.tariff").value(1.5));
    }
}
