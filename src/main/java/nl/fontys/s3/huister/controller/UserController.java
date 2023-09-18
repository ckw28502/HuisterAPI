package nl.fontys.s3.huister.controller;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.user.GetUserByIdUseCase;
import nl.fontys.s3.huister.domain.response.user.GetUserByIdResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final GetUserByIdUseCase getUserByIdUseCase;
    @GetMapping("{id}")
    public ResponseEntity<GetUserByIdResponse>getUserById(@PathVariable(value = "id")final int id){
        return ResponseEntity.ok(getUserByIdUseCase.getUserById(id));
    }
}
