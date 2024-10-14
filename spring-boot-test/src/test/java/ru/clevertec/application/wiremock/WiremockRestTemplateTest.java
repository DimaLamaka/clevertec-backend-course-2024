package ru.clevertec.application.wiremock;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import ru.clevertec.application.Application;
import ru.clevertec.application.BaseIT;
import ru.clevertec.application.IT;
import ru.clevertec.application.configuration.PostgresContainerConfiguration;
import ru.clevertec.application.dto.DirectoryOfferRequest;
import ru.clevertec.application.dto.DirectoryOfferResponse;

import static ru.clevertec.application.util.FileReaderUtil.readFile;

//@AutoConfigureMockMvc
@WireMockTest(httpPort = 7777)
@ContextConfiguration(classes = PostgresContainerConfiguration.class)
@IT
class WiremockRestTemplateTest {
    @Autowired
    private Gson gson;
    @LocalServerPort
    private int port;

    @Test
    @DisplayName("should success return all directory offers /offers")
    void testGetAllDirectoryOffers(@Autowired TestRestTemplate testRestTemplate) throws Exception {
        //given
        String basePath = "http://localhost:" + port;
        DirectoryOfferRequest request = gson.fromJson(readFile("/directory/get-directory-offers-request.json"), DirectoryOfferRequest.class);
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

        //when
        ResponseEntity<DirectoryOfferResponse> response = testRestTemplate.postForEntity(basePath + "/api/v1/offers/directory", request, DirectoryOfferResponse.class);

        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(
                expectedResponse,
                gson.toJson(response.getBody()),
                false
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"cat1", "cat2", "cat3", "cat5"})
    @DisplayName("should success return all directory offers /offers")
    void testGetAllDirectoryOffersWithTransformer(String category, @Autowired TestRestTemplate testRestTemplate) throws Exception {
        //given
        String basePath = "http://localhost:" + port;
        DirectoryOfferRequest request = gson.fromJson(readFile("/directory/get-directory-offers-request.json"), DirectoryOfferRequest.class);

        WireMock.stubFor(WireMock.post("/short-info-offers")
                .withRequestBody(WireMock.equalToJson(readFile("/directory/get-short-info-directory-offers-request.json"), true, true))
                .willReturn(WireMock.okJson(readFile("/directory/get-short-info-directory-offers-response.json"))
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)));

        WireMock.stubFor(WireMock.post("/full-info-offers")
                .withRequestBody(WireMock.equalToJson(readFile("/directory/get-full-info-directory-offers-request.json"), true, true))
                .willReturn(WireMock.okJson(readFile("/directory/get-full-info-directory-offers-transformer-response.json"))
                        .withTransformers("response-template")
                        .withTransformerParameter("cat", category)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)));

        //when
        ResponseEntity<DirectoryOfferResponse> response = testRestTemplate.postForEntity(basePath + "/api/v1/offers/directory", request, DirectoryOfferResponse.class);

        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertTrue(
                response.getBody().directoryOffers()
                        .stream()
                        .allMatch(offer -> category.equals(offer.category()))
        );
    }

}
