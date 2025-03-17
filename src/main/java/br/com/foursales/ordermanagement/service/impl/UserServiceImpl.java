package br.com.foursales.ordermanagement.service.impl;


import br.com.foursales.ordermanagement.exception.BusinessException;
import br.com.foursales.ordermanagement.model.auth.UserInfo;
import br.com.foursales.ordermanagement.model.dto.auth.request.PasswordUpdateRequestDTO;
import br.com.foursales.ordermanagement.model.dto.auth.request.UserRequestDTO;
import br.com.foursales.ordermanagement.model.dto.auth.request.UserUpdateRequestDTO;
import br.com.foursales.ordermanagement.model.dto.auth.response.UserResponseDTO;
import br.com.foursales.ordermanagement.mapper.auth.UserMapper;
import br.com.foursales.ordermanagement.repository.jpa.UserRepository;
import br.com.foursales.ordermanagement.service.UserService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final  String USER_ALREADY_EXIST = "Já existe um usuário com esse username cadastrado no sistema";

    private static final  String USER_NOT_FOUND = "Usuário não encontrado!";
    private static final  String INVALID_CURRENT_PASSWORD = "Senha atual inválida!";
    private static final  String INVALID_PASSWORD_UPDATE = "Nova senha ja cadastrada atualmente!";
    public static final String USER_NOT_PERMISSION = "Você não tem permissão para editar este usuário";

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserResponseDTO> getAllUser() {
        List<UserInfo> users = userRepository.findAll();
        return users.stream().map(userMapper::userEntityToResponseDTO)
                .toList();
    }

    @Override
    public UserResponseDTO getById(Long id) {
        UserInfo userInfo = getUserInfo(id);
        return userMapper.userEntityToResponseDTO(userInfo);
    }

    @Override
    public UserInfo getUserInfo(String userName) {
        UserInfo user = userRepository.findByUsername(userName);

        if (user == null) {
            throw new BusinessException(USER_NOT_FOUND);
        }

        return user;
    }

    @Override
    public UserResponseDTO saveUser(UserRequestDTO userRequestDTO) {

        UserInfo userSearched = userRepository.findByUsername(userRequestDTO.getUsername());

        if(userSearched != null){
            throw new BusinessException(USER_ALREADY_EXIST);
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = userRequestDTO.getPassword();
        String encodedPassword = encoder.encode(rawPassword);

        UserInfo user = userMapper.userRequestDTOToEntity(userRequestDTO);
        user.setPassword(encodedPassword);
        UserInfo userCreated = userRepository.save(user);
        return userMapper.userEntityToResponseDTO(getUserInfo(userCreated.getUsername()));
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserUpdateRequestDTO requestDTO) {
        UserInfo user = getUserInfo(id);

        validateUserPermission(user.getUsername());

        userMapper.updateUserFromDTO(requestDTO, user);

        UserInfo updatedUser = userRepository.save(user);
        return userMapper.userEntityToResponseDTO(updatedUser);
    }


    @Override
    public void updatePassword(Long id, PasswordUpdateRequestDTO passwordUpdateRequestDTO) {

        UserInfo user = getUserInfo(id);

        validateUserPermission(user.getUsername());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(passwordUpdateRequestDTO.getCurrentPassword(), user.getPassword())) {
            throw new BusinessException(INVALID_CURRENT_PASSWORD);
        }

        if (encoder.matches(passwordUpdateRequestDTO.getNewPassword(), user.getPassword())) {
            throw new BusinessException(INVALID_PASSWORD_UPDATE);
        }

        String encodedPassword = encoder.encode(passwordUpdateRequestDTO.getNewPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);
    }

    private void validateUserPermission(String userName) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) authentication.getPrincipal();
        String usernameFromAccessToken = userDetail.getUsername();

        if (!userName.equals(usernameFromAccessToken)) {
            throw new BusinessException(USER_NOT_PERMISSION);
        }
    }

    private UserInfo getUserInfo(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(USER_NOT_FOUND));
    }


}
