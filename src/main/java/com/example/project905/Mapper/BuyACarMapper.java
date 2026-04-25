package com.example.project905.Mapper;

import com.example.project905.Dto.BuyACarDto;
import com.example.project905.Modle.BuyCar;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface BuyACarMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "carId", source = "car.id")
    @Mapping(target = "status", source = "status")
    BuyACarDto toDto(BuyCar buyCar);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "car", ignore = true)
    BuyCar toEntity(BuyACarDto dto);

    List<BuyACarDto> toDtoList(List<BuyCar> buyCars);
}