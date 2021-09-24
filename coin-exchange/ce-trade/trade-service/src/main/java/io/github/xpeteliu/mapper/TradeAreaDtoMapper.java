package io.github.xpeteliu.mapper;

import io.github.xpeteliu.dto.TradeAreaDto;
import io.github.xpeteliu.entity.TradeArea;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TradeAreaDtoMapper {

    TradeAreaDtoMapper INSTANCE = Mappers.getMapper(TradeAreaDtoMapper.class);

    @Mapping(source = "createTime", target = "created")
    TradeArea dto2Entity(TradeAreaDto source);

    @Mapping(source = "created", target = "createTime")
    TradeAreaDto entity2Dto(TradeArea source);


    List<TradeArea> dto2Entity(List<TradeAreaDto> dto);


    List<TradeAreaDto> entity2Dto(List<TradeArea> entity);
}

