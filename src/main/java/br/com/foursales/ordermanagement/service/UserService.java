package br.com.foursales.ordermanagement.service;

import br.com.foursales.ordermanagement.model.auth.UserInfo;
import br.com.foursales.ordermanagement.model.dto.auth.request.PasswordUpdateRequestDTO;
import br.com.foursales.ordermanagement.model.dto.auth.request.UserRequestDTO;
import br.com.foursales.ordermanagement.model.dto.auth.request.UserUpdateRequestDTO;
import br.com.foursales.ordermanagement.model.dto.auth.response.UserResponseDTO;

import java.util.List;

public interface UserService {

    List<UserResponseDTO> getAllUser();

    UserResponseDTO getById(Long id);

    UserInfo getUserInfo(String userName);

    UserResponseDTO saveUser(UserRequestDTO requestDTO);

    UserResponseDTO updateUser(Long id, UserUpdateRequestDTO requestDTO);

    void updatePassword(Long id, PasswordUpdateRequestDTO passwordUpdateRequestDTO);


}
