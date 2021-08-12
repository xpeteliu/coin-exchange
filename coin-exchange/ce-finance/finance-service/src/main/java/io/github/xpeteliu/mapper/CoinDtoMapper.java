package io.github.xpeteliu.mapper;

import io.github.xpeteliu.dto.CoinDto;
import io.github.xpeteliu.entity.Coin;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CoinDtoMapper {

    CoinDtoMapper INSTANCE = Mappers.getMapper(CoinDtoMapper.class);

    CoinDto entity2Dto(Coin entity);

    Coin dto2Entity(CoinDto dto);

    List<CoinDto> entity2Dto(List<Coin> entity);

    List<Coin> dto2Entity(List<CoinDto> dto);
}
