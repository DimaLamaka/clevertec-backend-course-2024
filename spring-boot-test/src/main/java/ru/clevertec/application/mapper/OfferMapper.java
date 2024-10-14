package ru.clevertec.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.clevertec.application.dto.OfferDto;
import ru.clevertec.application.entity.Offer;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OfferMapper {

    OfferDto toOfferDto(Offer offer);

    Offer toOffer(OfferDto offer);

}
