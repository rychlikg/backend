package com.grzegorz.rychlik.backend.mapper;

import com.grzegorz.rychlik.backend.model.dao.Role;
import com.grzegorz.rychlik.backend.model.dao.User;
import com.grzegorz.rychlik.backend.model.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    @Mapping(source = "roles", target = "roles", qualifiedByName = "roleNames")
    UserDto toDto(User user);
    @Mapping(target = "roles", ignore = true)
    User toDao(UserDto userDto);
    @Named("roleNames")
    default List<String> roleNames(List<Role> roles){
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }
}
