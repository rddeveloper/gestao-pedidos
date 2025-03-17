package br.com.foursales.ordermanagement.controller.v1;

import br.com.foursales.ordermanagement.model.dto.auth.request.PasswordUpdateRequestDTO;
import br.com.foursales.ordermanagement.model.dto.auth.request.UserRequestDTO;
import br.com.foursales.ordermanagement.model.dto.auth.request.UserUpdateRequestDTO;
import br.com.foursales.ordermanagement.model.dto.auth.response.UserResponseDTO;
import br.com.foursales.ordermanagement.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Usuário", description = "Endpoints para gerenciamentos de usuários")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('LISTAR_OS')")
    @GetMapping()
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
            List<UserResponseDTO> userResponsDTOS = userService.getAllUser();
            return ResponseEntity.ok(userResponsDTOS);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> saveUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
            UserResponseDTO userResponseDTO = userService.saveUser(userRequestDTO);
            return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id,
                                                      @Valid @RequestBody UserUpdateRequestDTO userUpdateRequestDTO) {
        UserResponseDTO userResponseDTO = userService.updateUser(id, userUpdateRequestDTO);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id,
                                               @Valid @RequestBody PasswordUpdateRequestDTO passwordUpdateRequestDTO) {
        userService.updatePassword(id, passwordUpdateRequestDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
