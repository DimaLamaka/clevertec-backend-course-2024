package ru.clevertec.application.datajpa;

import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import ru.clevertec.application.mapper.OfferMapper;
import ru.clevertec.application.repository.OfferRepository;
import ru.clevertec.application.service.OfferService;

@TestConfiguration
public class DataJpaTestConfiguration {

    @Bean
    public OfferService offerService(OfferRepository offerRepository) {
        return new OfferService(offerRepository, offerMapper());
    }

    @Bean
    public OfferMapper offerMapper() {
        return Mappers.getMapper(OfferMapper.class);
    }
}
