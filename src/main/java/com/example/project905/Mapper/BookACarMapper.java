package com.example.project905.Mapper;

import com.example.project905.Dto.BookACarDto;
import com.example.project905.Modle.BookACar;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface BookACarMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "carId", source = "car.id")
    BookACarDto toDto(BookACar bookACar);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "car", ignore = true)
    BookACar toEntity(BookACarDto dto);

    List<BookACarDto> toDtoList(List<BookACar> bookACars);
}