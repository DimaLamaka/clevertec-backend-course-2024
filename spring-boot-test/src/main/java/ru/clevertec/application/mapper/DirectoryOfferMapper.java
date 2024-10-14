package ru.clevertec.application.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import ru.clevertec.application.dto.DirectoryOffer;
import ru.clevertec.application.dto.directory.DirectoryFullInfoOffer;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DirectoryOfferMapper {

    DirectoryOffer toDirectoryOffer(DirectoryFullInfoOffer directoryFullInfoOffer);

    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
    List<DirectoryOffer> toDirectoryOffers(List<DirectoryFullInfoOffer> directoryFullInfoOffers);
}
