package nl.fontys.s3.huister.controller;

import com.google.gson.Gson;
import nl.fontys.s3.huister.business.interfaces.user.*;
import nl.fontys.s3.huister.business.request.user.*;
import nl.fontys.s3.huister.business.response.user.GetAllOwnersResponse;
import nl.fontys.s3.huister.business.response.user.GetUserByIdResponse;
import nl.fontys.s3.huister.business.response.user.LoginResponse;
import nl.fontys.s3.huister.business.response.user.RefreshTokenResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
 class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Gson gson;

    @MockBean
    private  GetUserByIdUseCase getUserByIdUseCase;
    @MockBean
    private CreateUserUseCase createUserUseCase;
    @MockBean
    private UpdateUserUseCase updateUserUseCase;
    @MockBean
    private LoginUseCase loginUseCase;
    @MockBean
    private ChangePasswordUseCase changePasswordUseCase;
    @MockBean
    private GetAllOwnersUseCase getAllOwnersUseCase;
    @MockBean
    private SetProfilePictureUrlUseCase setProfilePictureUrlUseCase;
    @MockBean
    private ActivateAccountUseCase activateAccountUseCase;
    @MockBean
    private ForgotPasswordUseCase forgotPasswordUseCase;
    @MockBean
    private RefreshTokenUseCase refreshTokenUseCase;
    /**
     * @verifies return 401 if user is unauthorized
     * @see UserController#getUserById(long)
     */
    @Test
    void getUserById_shouldReturn401IfUserIsUnauthorized() throws Exception {
        mockMvc.perform(get("/users/1"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    /**
     * @verifies return 200 if user is authorized
     * @see UserController#getUserById(long)
     */
    @Test
    @WithMockUser(roles = {"ADMIN","OWNER","CUSTOMER"})
    void getUserById_shouldReturn200IfUserIsAuthorized() throws Exception {
        //Arrange
        GetUserByIdResponse response=GetUserByIdResponse.builder().build();

        when(getUserByIdUseCase.getUserById(1L)).thenReturn(response);

        //Act + Assert
        mockMvc.perform(get("/users/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type",APPLICATION_JSON_VALUE))
                .andExpect(content().json(gson.toJson(response)));

        verify(getUserByIdUseCase).getUserById(1L);

    }

    /**
     * @verifies return 401 when user is not logged-in
     * @see UserController#getAllOwners()
     */
    @Test
    void getAllOwners_shouldReturn401WhenUserIsNotLoggedin() throws Exception {
        mockMvc.perform(get("/users/owners"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    /**
     * @verifies return 403 when user is not admin
     * @see UserController#getAllOwners()
     */
    @Test
    @WithMockUser(roles = {"OWNER","CUSTOMER"})
    void getAllOwners_shouldReturn403WhenUserIsNotAdmin() throws Exception {
        mockMvc.perform(get("/users/owners"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    /**
     * @verifies return 200 when user is admin
     * @see UserController#getAllOwners()
     */
    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllOwners_shouldReturn200WhenUserIsAdmin() throws Exception {
        //Arrange
        List<GetAllOwnersResponse> responses=List.of(GetAllOwnersResponse.builder().build());

        when(getAllOwnersUseCase.getAllOwners()).thenReturn(responses);

        //Act + Assert
        mockMvc.perform(get("/users/owners"))
                .andDo(print())
                .andExpect(header().string("Content-Type",APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(gson.toJson(responses)));

        verify(getAllOwnersUseCase).getAllOwners();

    }

    /**
     * @verifies return 401 when user is not logged-in
     * @see UserController#updateUser(nl.fontys.s3.huister.business.request.user.UpdateUserRequest)
     */
    @Test
    void updateUser_shouldReturn401WhenUserIsNotLoggedin() throws Exception {
        //Arrange
        UpdateUserRequest request=UpdateUserRequest.builder().build();

        //Act + Assert
        mockMvc.perform(put("/users")
                .contentType(APPLICATION_JSON_VALUE)
                .content(gson.toJson(request)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    /**
     * @verifies return 204 when user is authorized
     * @see UserController#updateUser(nl.fontys.s3.huister.business.request.user.UpdateUserRequest)
     */
    @Test
    @WithMockUser(roles = {"OWNER","CUSTOMER"})
    void updateUser_shouldReturn204WhenUserIsAuthorized() throws Exception {
        //Arrange
        UpdateUserRequest request=UpdateUserRequest.builder().build();

        //Act + Assert
        mockMvc.perform(put("/users")
                .contentType(APPLICATION_JSON_VALUE)
                .content(gson.toJson(request)))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(updateUserUseCase).updateUser(request);

    }

    /**
     * @verifies return 201
     * @see UserController#createUser(CreateUserRequest)
     */
    @Test
    void createUser_shouldReturn201() throws Exception {
        //Arrange
        CreateUserRequest request=CreateUserRequest.builder().build();

        //Act + Assert
        mockMvc.perform(post("/users")
                .contentType(APPLICATION_JSON_VALUE)
                .content(gson.toJson(request)))
                .andDo(print())
                .andExpect(status().isCreated());

        verify(createUserUseCase).createUser(request);

    }

    /**
     * @verifies return 200
     * @see UserController#login(nl.fontys.s3.huister.business.request.user.LoginRequest)
     */
    @Test
    void login_shouldReturn200() throws Exception {
        //Arrange
        LoginRequest request=LoginRequest.builder().build();

        LoginResponse response=LoginResponse.builder().build();

        when(loginUseCase.login(request)).thenReturn(response);

        //Act + Assert
        mockMvc.perform(post("/users/login")
                .contentType(APPLICATION_JSON_VALUE)
                .content(gson.toJson(request)))
                .andDo(print())
                .andExpect(header().string("Content-Type",APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(gson.toJson(response)));

        verify(loginUseCase).login(request);
    }

    /**
     * @verifies return 403 when user is admin
     * @see UserController#updateUser(nl.fontys.s3.huister.business.request.user.UpdateUserRequest)
     */
    @Test
    @WithMockUser(roles = "ADMIN")
    void updateUser_shouldReturn403WhenUserIsAdmin() throws Exception {
        //Arrange
        UpdateUserRequest request=UpdateUserRequest.builder().build();

        //Act + Assert
        mockMvc.perform(put("/users")
                .contentType(APPLICATION_JSON_VALUE)
                .content(gson.toJson(request)))
                .andDo(print())
                .andExpect(status().isForbidden());

    }

    /**
     * @verifies return 204
     * @see UserController#setProfilePictureUrl(nl.fontys.s3.huister.business.request.user.SetProfilePictureUrlRequest)
     */
    @Test
    void setProfilePictureUrl_shouldReturn204() throws Exception {
        //Arrange
        SetProfilePictureUrlRequest request=SetProfilePictureUrlRequest.builder().build();

        //Act + Assert
        mockMvc.perform(put("/users/image")
                .contentType(APPLICATION_JSON_VALUE)
                .content(gson.toJson(request)))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(setProfilePictureUrlUseCase).setProfilePictureUrl(request);

    }

    /**
     * @verifies return 204
     * @see UserController#changePassword(nl.fontys.s3.huister.business.request.user.ChangePasswordRequest)
     */
    @Test
    void changePassword_shouldReturn204() throws Exception {
        //Arrange
        ChangePasswordRequest request=ChangePasswordRequest.builder()
                .username("user")
                .build();

        //Act + Assert
        mockMvc.perform(put("/users/changePassword")
                .contentType(APPLICATION_JSON_VALUE)
                .content(gson.toJson(request)))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(changePasswordUseCase).changePassword(request);

    }

    /**
     * @verifies return 204
     * @see UserController#activateAccount(ActivateAccountRequest)
     */
    @Test
    void activateAccount_shouldReturn204() throws Exception {
        //Arrange
        ActivateAccountRequest request= ActivateAccountRequest.builder().build();

        //Act + Assert
        mockMvc.perform(put("/users/activate")
                .contentType(APPLICATION_JSON_VALUE)
                .content(gson.toJson(request)))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(activateAccountUseCase).activateAccount(request);
    }

    /**
     * @verifies return 204
     * @see UserController#forgotPassword(ForgotPasswordRequest)
     */
    @Test
    void forgotPassword_shouldReturn204() throws Exception {
        //Arrange
        ForgotPasswordRequest request=ForgotPasswordRequest.builder().build();

        //Act + Assert
        mockMvc.perform(post("/users/forgot")
                .contentType(APPLICATION_JSON_VALUE)
                .content(gson.toJson(request)))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(forgotPasswordUseCase).forgotPassword(request);

    }

    /**
     * @verifies return 200
     * @see UserController#refreshToken(RefreshTokenRequest)
     */
    @Test
    void refreshToken_shouldReturn200() throws Exception {
        //Arrange
        RefreshTokenRequest request= RefreshTokenRequest.builder().build();
        RefreshTokenResponse response=RefreshTokenResponse.builder().build();

        when(refreshTokenUseCase.refreshToken(request)).thenReturn(response);

        //Act + Assert
        mockMvc.perform(post("/users/token")
                .contentType(APPLICATION_JSON_VALUE)
                .content(gson.toJson(request)))
                .andDo(print())
                .andExpect(header().string("Content-Type",APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(gson.toJson(response)));

        verify(refreshTokenUseCase).refreshToken(request);

    }
}
