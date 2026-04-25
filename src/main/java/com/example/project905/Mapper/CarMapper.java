package com.example.project905.Mapper;

import com.example.project905.Dto.CarDto;
import com.example.project905.Modle.Car;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CarMapper {

    @Mapping(target = "returnedImage", source = "image")
    @Mapping(target = "image", ignore = true)
    CarDto toDto(Car car);

    @Mapping(target = "image", ignore = true)
    Car toEntity(CarDto dto);

    List<CarDto> toDtoList(List<Car> cars);
    List<Car> toEntityList(List<CarDto> dtos);
}