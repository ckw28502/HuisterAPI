package nl.fontys.s3.huister.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.user.CreateUserUseCase;
import nl.fontys.s3.huister.business.user.GetUserByIdUseCase;
import nl.fontys.s3.huister.domain.request.user.CreateUserRequest;
import nl.fontys.s3.huister.domain.response.user.GetUserByIdResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final CreateUserUseCase createUserUseCase;
    @GetMapping("{id}")
    public ResponseEntity<GetUserByIdResponse>getUserById(@PathVariable(value = "id")final int id){
        return ResponseEntity.ok(getUserByIdUseCase.getUserById(id));
    }
    @PostMapping
    public ResponseEntity<Void>createUser(@RequestBody@Valid CreateUserRequest request){
        createUserUseCase.createUser(request);
       return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
