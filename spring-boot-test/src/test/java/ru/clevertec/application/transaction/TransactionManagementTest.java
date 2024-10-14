package ru.clevertec.application.transaction;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import ru.clevertec.application.entity.Offer;
import ru.clevertec.application.repository.OfferRepository;

import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.class)
class TransactionManagementTest {
    @Autowired
    private OfferRepository offerRepository;

    @BeforeTransaction
    void beforeTrans() {
        System.out.println("before trans");
    }

    @AfterTransaction
    void afterTrans() {
        System.out.println("after trans");
    }

    @Commit
    @Test
    void testSaveOffer() {
        //given
        System.out.println(offerRepository.findAll().size());
        Offer offer = new Offer()
                .setCategory("category55")
                .setCode("code55")
                .setCondition("condition55")
                .setDescription("description55")
                .setTariff(0.1)
                .setTitle("title55");

        //when
        Offer save = offerRepository.save(offer);

        Assertions.assertThat(save.getId())
                .isEqualTo(1);
    }

    @Test
    void testFindAllOffers() {
        //when
        List<Offer> offers = offerRepository.findAll();

        Assertions.assertThat(offers)
                .hasSize(1);
    }
}
