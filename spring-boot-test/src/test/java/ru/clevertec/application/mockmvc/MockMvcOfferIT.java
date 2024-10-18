package ru.clevertec.application.mockmvc;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.clevertec.application.util.FileReaderUtil.*;

@AutoConfigureMockMvc
@Sql(scripts = "classpath:db/insert-offers.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:db/rollback-script.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ContextConfiguration(classes = PostgresContainerConfiguration.class)
class MockMvcOfferIT extends BaseIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OfferRepository offerRepository;

    @Test
    @DisplayName("should success return all offers /offers")
    void testGetAllOffers() throws Exception {
        //given

        String expectedResponse = readFile("/offers/get-all-committed-offers-response.json");

        //when/then
        mockMvc.perform(get("/api/v1/offers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json(expectedResponse));
    }

    @Test
    @DisplayName("should success return offer by id /offers")
    void testGetOffersById() throws Exception {
        //given
        long offerId = 1;

        //when/then
        mockMvc.perform(get("/api/v1/offers/{offerId}", offerId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.offer.category").value("category#1"))
                .andExpect(jsonPath("$.offer.code").value("code1"))
                .andExpect(jsonPath("$.offer.tariff").value(1.5));
    }

    @Test
    @DisplayName("should success delete offer by id /offers")
    void testDeleteOfferById() throws Exception {
        //given
        long offerId = 1;

        //when/then
        MvcResult mvcResult = mockMvc.perform(delete("/api/v1/offers/{offerId}", offerId))
                .andReturn();

        Assertions.assertThat(mvcResult)
                .extracting(MvcResult::getResponse)
                .extracting(MockHttpServletResponse::getStatus)
                .isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("should success save offer /offers")
    void testSaveOffer() throws Exception {
        //given
        String request = readFile("/offers/save-offer-request.json");
        long expectedId = 7;

        //when/then
        mockMvc.perform(post("/api/v1/offers")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expectedId));

        Assertions.assertThat(offerRepository.findAll()).hasSize(7);
        Assertions.assertThat(offerRepository.findById(expectedId))
                .map(Offer::getCategory)
                .hasValue("category#666");
    }
}
