package nl.fontys.s3.huister.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.interfaces.user.*;
import nl.fontys.s3.huister.business.request.user.ChangePasswordRequest;
import nl.fontys.s3.huister.business.request.user.CreateUserRequest;
import nl.fontys.s3.huister.business.request.user.LoginRequest;
import nl.fontys.s3.huister.business.request.user.UpdateUserRequest;
import nl.fontys.s3.huister.business.response.user.GetAllOwnersResponse;
import nl.fontys.s3.huister.business.response.user.GetUserByIdResponse;
import nl.fontys.s3.huister.business.response.user.LoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
@CrossOrigin(origins = {"http://localhost:5173","http://localhost:4173"})
public class UserController {
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final LoginUseCase loginUseCase;
    private final ChangePasswordUseCase changePasswordUseCase;
    private final GetAllOwnersUseCase getAllOwnersUseCase;
    @GetMapping("{id}")
    public ResponseEntity<GetUserByIdResponse>getUserById(@PathVariable(value = "id")final int id){
        return ResponseEntity.ok(getUserByIdUseCase.getUserById(id));
    }

    @GetMapping("/owners")
    public ResponseEntity<List<GetAllOwnersResponse>>getAllOwners(){
        return ResponseEntity.ok(getAllOwnersUseCase.getAllOwners());
    }

    @PostMapping()
    public ResponseEntity<Void>createUser(@RequestBody CreateUserRequest request){
        createUserUseCase.createUser(request);
       return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse>Login(@RequestBody@Valid LoginRequest request){
        return ResponseEntity.ok(loginUseCase.Login(request));
    }

    @PutMapping("{id}")
    public ResponseEntity<Void>updateUser(
            @PathVariable(value = "id")final int id,
            @RequestBody@Valid UpdateUserRequest request
            ){
        request.setId(id);
        updateUserUseCase.updateUser(request);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/changePassword/{id}")
    public ResponseEntity<Void>changePassword(@PathVariable(value = "id")int id, @RequestBody@Valid ChangePasswordRequest request){
        request.setId(id);
        changePasswordUseCase.changePassword(request);
        return ResponseEntity.ok().build();
    }
}
