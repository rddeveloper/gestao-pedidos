package br.com.foursales.ordermanagement.mapper.auth;

import br.com.foursales.ordermanagement.model.auth.UserInfo;
import br.com.foursales.ordermanagement.model.dto.auth.request.UserRequestDTO;
import br.com.foursales.ordermanagement.model.dto.auth.request.UserUpdateRequestDTO;
import br.com.foursales.ordermanagement.model.dto.auth.response.UserResponseDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {

    UserResponseDTO userEntityToResponseDTO(UserInfo userInfo);

    UserInfo userRequestDTOToEntity(UserRequestDTO requestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    void updateUserFromDTO(UserUpdateRequestDTO dto, @MappingTarget UserInfo entity);

}
