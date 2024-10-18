package ru.clevertec.application.datajpa;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import ru.clevertec.application.dto.CommittedOffersResponse;
import ru.clevertec.application.entity.Offer;
import ru.clevertec.application.repository.OfferRepository;
import ru.clevertec.application.service.OfferService;

import java.util.Optional;

@ActiveProfiles("test")
@DataJpaTest
@Sql(scripts = "classpath:db/insert-offers.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ContextConfiguration(classes = DataJpaTestConfiguration.class)
class DataJpaOfferIT {
    @Autowired
    private OfferService offerService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void testGetAllOffers() {
        //when
        CommittedOffersResponse response = offerService.getAll();

        //then
        Assertions.assertThat(response.offers())
                .hasSize(6);
    }

    @Test
    void testSaveOffer() {
        //given
        Offer offer = new Offer()
                .setCategory("category55")
                .setCode("code55")
                .setCondition("condition55")
                .setDescription("description55")
                .setTariff(0.1)
                .setTitle("title55");

        //when
        Long id = testEntityManager.persistAndGetId(offer, Long.class);
        Integer maxRows = jdbcTemplate.queryForObject("select count(*) as offer_count from offer", Integer.class);
        Optional<Offer> offerById = offerRepository.findById(id);

        //then
        Assertions.assertThat(offerById)
                .isPresent()
                .get()
                .isEqualTo(offer);

        Assertions.assertThat(maxRows)
                .isNotNull()
                .isEqualTo(7);
    }
}
