package ru.clevertec.application.wiremock;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.application.BaseIT;
import ru.clevertec.application.configuration.PostgresContainerConfiguration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.clevertec.application.util.FileReaderUtil.readFile;

@AutoConfigureMockMvc
@WireMockTest(httpPort = 7777)
@ContextConfiguration(classes = PostgresContainerConfiguration.class)
class WiremockMockMvcTest extends BaseIT {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("should success return all directory offers /offers")
    void testGetAllDirectoryOffers() throws Exception {
        //given
        String expectedResponse = readFile("/directory/get-directory-offers-response.json");

        //and
        WireMock.stubFor(WireMock.post("/short-info-offers")
                .withRequestBody(WireMock.equalToJson(readFile("/directory/get-short-info-directory-offers-request.json"), true, true))
                .willReturn(WireMock.okJson(readFile("/directory/get-short-info-directory-offers-response.json"))
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)));

        WireMock.stubFor(WireMock.post("/full-info-offers")
                .withRequestBody(WireMock.equalToJson(readFile("/directory/get-full-info-directory-offers-request.json"), true, true))
                .willReturn(WireMock.okJson(readFile("/directory/get-full-info-directory-offers-response.json"))
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)));

        //when/then
        mockMvc.perform(post("/api/v1/offers/directory")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .content(readFile("/directory/get-directory-offers-request.json")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json(expectedResponse));
    }
}
