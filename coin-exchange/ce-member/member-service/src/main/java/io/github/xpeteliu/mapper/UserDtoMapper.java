package io.github.xpeteliu.mapper;

import io.github.xpeteliu.dto.UserDto;
import io.github.xpeteliu.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    UserDtoMapper INSTANCE = Mappers.getMapper(UserDtoMapper.class);

    UserDto entity2Dto(User entity);

    User dto2Entity(UserDto dto);

    List<UserDto> entity2Dto(List<User> entity);

    List<User> dto2Entity(List<UserDto> dto);

}
