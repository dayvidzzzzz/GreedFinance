package dysystem.com.greedfinance.controller;

import dysystem.com.greedfinance.dto.request.UserCreateRequestDTO;
import dysystem.com.greedfinance.dto.response.UserCreateResponseDTO;
import dysystem.com.greedfinance.dto.response.UserResponseDTO;
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

    @PreAuthorize("hasRole('MASTER')")
    @PostMapping
    public ResponseEntity<UserCreateResponseDTO> createUser(@RequestBody @Valid UserCreateRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createUserUseCase.execute(dto));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAllUsers() {
        return ResponseEntity.ok(findAllUsersUseCase.execute());
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
}