package dysystem.com.greedfinance.controller;

import dysystem.com.greedfinance.dto.request.UserRequestDTO;
import dysystem.com.greedfinance.dto.response.UserCreateResponseDTO;
import dysystem.com.greedfinance.dto.response.UserResponseDTO;
import dysystem.com.greedfinance.dto.response.UserUpdateResponseDTO;
import dysystem.com.greedfinance.service.useCase.user.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/users")
@AllArgsConstructor
@PreAuthorize("hasAnyRole('USER', 'MASTER')")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final FindUserByEmailUseCase findUserByEmailUseCase;
    private final FindAllUsersUseCase findAllUsersUseCase;
    private final FindUserByIdUseCase findUserByIdUseCase;
    private final FindUserByUsernameUseCase findUserByUsernameUseCase;
    private final DeleteUserById deleteUserById;
    private final UpdateUserUseCase updateUserUseCase;
    private final ResetUserPassword resetUserPassword;
    private final SetUserIsActiveUserCase setUserIsActiveUserCase;
    private final FindAllUserByActiveUseCase findAllUserByActiveUseCase;

    @PreAuthorize("hasRole('MASTER')")
    @PostMapping
    public ResponseEntity<UserCreateResponseDTO> createUser(@RequestBody @Valid UserRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createUserUseCase.execute(dto));
    }

    @PreAuthorize("hasRole('MASTER')")
    @PutMapping("/{id}")
    public ResponseEntity<UserUpdateResponseDTO> update(@PathVariable String id,
                                                        @RequestBody @Valid UserRequestDTO dto) {
        return ResponseEntity.ok(updateUserUseCase.execute(id, dto));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll() {
        return ResponseEntity.ok(findAllUsersUseCase.execute());
    }

    @GetMapping("/active")
    public ResponseEntity<List<UserResponseDTO>> findAllByActive(
            @RequestParam(name = "active", defaultValue = "true") boolean active) {
        return ResponseEntity.ok(findAllUserByActiveUseCase.execute(active));
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findUserById(@PathVariable String id) {
        return ResponseEntity.ok(findUserByIdUseCase.execute(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        deleteUserById.execute(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDTO> findUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(findUserByEmailUseCase.execute(email));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponseDTO> findUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(findUserByUsernameUseCase.execute(username));
    }

    @PutMapping("/reset-password/{id}")
    public ResponseEntity<?> resetPassword(@PathVariable String id) {
        resetUserPassword.execute(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/is-active/{id}")
    public ResponseEntity<?> isActive(@PathVariable String id) {
        setUserIsActiveUserCase.execute(id);
        return ResponseEntity.ok().build();
    }
}