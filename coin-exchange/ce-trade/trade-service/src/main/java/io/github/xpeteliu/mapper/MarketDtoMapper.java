package io.github.xpeteliu.mapper;


import io.github.xpeteliu.dto.MarketDto;
import io.github.xpeteliu.entity.Market;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MarketDtoMapper{

    MarketDtoMapper INSTANCE = Mappers.getMapper(MarketDtoMapper.class) ;

    Market dto2Entity(MarketDto source) ;

    MarketDto entity2Dto(Market source) ;


    List<Market> dto2Entity(List<MarketDto> dto) ;


    List<MarketDto> entity2Dto(List<Market> entity) ;
}
