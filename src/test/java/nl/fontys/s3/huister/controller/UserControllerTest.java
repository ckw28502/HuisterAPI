package nl.fontys.s3.huister.controller;

import com.google.gson.Gson;
import nl.fontys.s3.huister.business.interfaces.user.*;
import nl.fontys.s3.huister.business.request.user.*;
import nl.fontys.s3.huister.business.response.user.GetAllOwnersResponse;
import nl.fontys.s3.huister.business.response.user.GetUserByIdResponse;
import nl.fontys.s3.huister.business.response.user.LoginResponse;
import nl.fontys.s3.huister.configuration.utilities.GsonConfig;
import nl.fontys.s3.huister.domain.entities.enumerator.UserRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
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
@WebMvcTest(UserController.class)
@Import(GsonConfig.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Gson gson;

    @MockBean
    private GetUserByIdUseCase getUserByIdUseCaseMock;
    @MockBean
    private CreateUserUseCase createUserUseCaseMock;
    @MockBean
    private UpdateUserUseCase updateUserUseCaseMock;
    @MockBean
    private LoginUseCase loginUseCaseMock;
    @MockBean
    private ChangePasswordUseCase changePasswordUseCaseMock;
    @MockBean
    private GetAllOwnersUseCase getAllOwnersUseCaseMock;
    @MockBean
    private SetProfilePictureUrlUseCase setProfilePictureUrlUseCase;

    /**
     * @verifies return user
     * @see UserController#getUserById(long)
     */
    @Test
    void getUserById_shouldReturnUser() throws Exception {
        //Arrange
        GetUserByIdResponse response=GetUserByIdResponse.builder()
                .id(1L)
                .name("user")
                .email("user@email.com")
                .phoneNumber("0123456789")
                .profilePictureUrl("user.png")
                .role(UserRole.OWNER)
                .build();

        when(getUserByIdUseCaseMock.getUserById(response.getId())).thenReturn(response);

        //Act + Assert
        mockMvc.perform(get("/users/1"))
                .andDo(print())
                .andExpect(header().string("content-type",APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(gson.toJson(response)));

    }

    /**
     * @verifies return list of owner
     * @see UserController#getAllOwners()
     */
    @Test
    void getAllOwners_shouldReturnListOfOwner() throws Exception {
        //Arrange
        List<GetAllOwnersResponse> responses=List.of(GetAllOwnersResponse.builder()
                .propertyRented(1)
                .propertyOwned(1)
                .name("owner")
                .profilePictureUrl("owner.png")
                .email("owner@gmail.com")
                .id(1L)
                .build());

        when(getAllOwnersUseCaseMock.getAllOwners()).thenReturn(responses);

        //Act + Assert
        mockMvc.perform(get("/users/owners"))
                .andDo(print())
                .andExpect(header().string("content-type",APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(gson.toJson(responses)));

    }

    /**
     * @verifies create user
     * @see UserController#createUser(nl.fontys.s3.huister.business.request.user.CreateUserRequest)
     */
    @Test
    void createUser_shouldCreateUser() throws Exception {
        //Arrange
        CreateUserRequest request=CreateUserRequest.builder()
                .email("user@email.com")
                .phoneNumber("0123456789")
                .profilePictureUrl("user.png")
                .role(UserRole.OWNER)
                .password("user")
                .username("user")
                .build();

        //Act + Assert
        mockMvc.perform(post("/users")
                .contentType(APPLICATION_JSON_VALUE)
                .content(gson.toJson(request)))
                .andDo(print())
                .andExpect(status().isCreated());

        verify(createUserUseCaseMock).createUser(request);

    }

    /**
     * @verifies return token
     * @see UserController#login(nl.fontys.s3.huister.business.request.user.LoginRequest)
     */
    @Test
    void login_shouldReturnToken() throws Exception {
        //Arrange
        LoginRequest request=LoginRequest.builder().build();
        LoginResponse response=LoginResponse.builder().build();

        when(loginUseCaseMock.login(request)).thenReturn(response);

        //Act + Assert
        mockMvc.perform(post("/users/login")
                .contentType(APPLICATION_JSON_VALUE)
                .content(gson.toJson(request)))
                .andDo(print())
                .andExpect(header().string("content-type",APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(gson.toJson(response)));

    }

    /**
     * @verifies update user
     * @see UserController#updateUser(int, nl.fontys.s3.huister.business.request.user.UpdateUserRequest)
     */
    @Test
    void updateUser_shouldUpdateUser() throws Exception {
        //Arrange
        UpdateUserRequest request=UpdateUserRequest.builder()
                .id(1L)
                .build();

        //Act + Assert
        mockMvc.perform(put("/users/1")
                .contentType(APPLICATION_JSON_VALUE)
                .content(gson.toJson(request)))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(updateUserUseCaseMock).updateUser(request);
    }

    /**
     * @verifies update password
     * @see UserController#changePassword(long, nl.fontys.s3.huister.business.request.user.ChangePasswordRequest)
     */
    @Test
    void changePassword_shouldUpdatePassword() throws Exception {
        //Arrange
        ChangePasswordRequest request=ChangePasswordRequest.builder()
                .id(1L)
                .build();

        //Act + Assert
        mockMvc.perform(put("/users/changePassword/1")
                .contentType(APPLICATION_JSON_VALUE)
                .content(gson.toJson(request)))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(changePasswordUseCaseMock).changePassword(request);

    }

    /**
     * @verifies set profile picture url
     * @see UserController#setProfilePictureUrl(nl.fontys.s3.huister.business.request.user.SetProfilePictureUrlRequest)
     */
    @Test
    void setProfilePictureUrl_shouldSetProfilePictureUrl() throws Exception {
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
}
