package ru.clevertec.application.component;

import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import ru.clevertec.application.mapper.OfferMapper;
import ru.clevertec.application.repository.OfferRepository;
import ru.clevertec.application.service.OfferService;

@TestConfiguration
public class ComponentTestConfiguration {

    @Bean
    public OfferRepository offerRepository() {
        return Mockito.mock(OfferRepository.class);
    }

    @Bean
    public OfferService offerService(OfferRepository offerRepository) {
        return new OfferService(offerRepository, offerMapper());
    }

    @Bean
    public OfferMapper offerMapper() {
        return Mappers.getMapper(OfferMapper.class);
    }
}
