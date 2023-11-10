package nl.fontys.s3.huister.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.interfaces.user.*;
import nl.fontys.s3.huister.business.request.user.*;
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
    private final SetProfilePictureUrlUseCase setProfilePictureUrlUseCase;


    /**
     *
     * @param id user id
     * @return user
     *
     * @should return user
     */
    @GetMapping("{id}")
    public ResponseEntity<GetUserByIdResponse>getUserById(@PathVariable(value = "id")final long id){
        return ResponseEntity.ok(getUserByIdUseCase.getUserById(id));
    }

    /**
     *
     * @return list of owner
     *
     * @should return list of owner
     */
    @GetMapping("/owners")
    public ResponseEntity<List<GetAllOwnersResponse>>getAllOwners(){
        return ResponseEntity.ok(getAllOwnersUseCase.getAllOwners());
    }

    /**
     *
     * @param request create user request
     * @return http with created status
     *
     * @should create user
     */
    @PostMapping()
    public ResponseEntity<Void>createUser(@RequestBody CreateUserRequest request){
        createUserUseCase.createUser(request);
       return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     *
     * @param request login request
     * @return login token
     *
     * @should return token
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse>login(@RequestBody@Valid LoginRequest request){
        return ResponseEntity.ok(loginUseCase.login(request));
    }


    /**
     *
     * @param request set profile picture url request
     * @return http response with no content status
     *
     * @should set profile picture url
     *
     */
    @PutMapping("/image")
    public ResponseEntity<Void>setProfilePictureUrl(SetProfilePictureUrlRequest request){
        setProfilePictureUrlUseCase.setProfilePictureUrl(request);
        return ResponseEntity.noContent().build();
    }

    /**
     *
     * @param id user id
     * @param request update user request
     * @return http response with no content status
     *
     * @should update user
     */
    @PutMapping("{id}")
    public ResponseEntity<Void>updateUser(
            @PathVariable(value = "id")final int id,
            @RequestBody@Valid UpdateUserRequest request
            ){
        request.setId(id);
        updateUserUseCase.updateUser(request);
        return ResponseEntity.noContent().build();
    }

    /**
     *
     * @param id user id
     * @param request new password request
     * @return http response with no content status
     *
     * @should update password
     */
    @PutMapping("/changePassword/{id}")
    public ResponseEntity<Void>changePassword(@PathVariable(value = "id")long id, @RequestBody@Valid ChangePasswordRequest request){
        request.setId(id);
        changePasswordUseCase.changePassword(request);
        return ResponseEntity.noContent().build();
    }
}
