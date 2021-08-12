package io.github.xpeteliu.mapper;

import io.github.xpeteliu.dto.AdminBankDto;
import io.github.xpeteliu.entity.AdminBank;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdminBankDtoMapper {

    AdminBankDtoMapper INSTANCE = Mappers.getMapper(AdminBankDtoMapper.class);

    AdminBankDto entity2Dto(AdminBank entity);

    AdminBank dto2Entity(AdminBankDto dto);

    List<AdminBankDto> entity2Dto(List<AdminBank> entity);

    List<AdminBank> dto2Entity(List<AdminBankDto> dto);

}
