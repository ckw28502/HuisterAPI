package nl.fontys.s3.huister.controller;

import com.google.gson.Gson;
import nl.fontys.s3.huister.business.interfaces.property.*;
import nl.fontys.s3.huister.business.request.property.CreatePropertyRequest;
import nl.fontys.s3.huister.business.request.property.UpdatePropertyRequest;
import nl.fontys.s3.huister.business.response.property.GetAllPropertiesResponse;
import nl.fontys.s3.huister.business.response.property.GetPropertyDetailResponse;
import nl.fontys.s3.huister.business.response.property.GetRentedNotRentedPropertyRatioResponse;
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

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class PropertyControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Gson gson;

    @MockBean
    private GetAllPropertiesUseCase getAllPropertiesUseCaseMock;
    @MockBean
    private GetPropertyDetailUseCase getPropertyDetailUseCaseMock;
    @MockBean
    private CreatePropertyUseCase createPropertyUseCaseMock;
    @MockBean
    private UpdatePropertyUseCase updatePropertyUseCaseMock;
    @MockBean
    private DeletePropertyUseCase deletePropertyUseCaseMock;
    @MockBean
    private GetRentedNotRentedPropertyRatioUseCase getRentedNotRentedPropertyRatioUseCaseMock;
    /**
     * @verifies return 401 if user is not logged-in
     * @see PropertyController#getAllProperties()
     */
    @Test
    void getAllProperties_shouldReturn401IfUserIsNotLoggedin() throws Exception {
        mockMvc.perform(get("/properties"))
                .andDo(print())
                .andExpect(status().isUnauthorized());

    }

    /**
     * @verifies return 200 if user is logged-in
     * @see PropertyController#getAllProperties()
     */
    @Test
    @WithMockUser(roles = {"ADMIN","OWNER","CUSTOMER"})
    void getAllProperties_shouldReturn200IfUserIsLoggedin() throws Exception {
        //Arrange
        List<GetAllPropertiesResponse> responses= List.of(GetAllPropertiesResponse.builder().build());

        when(getAllPropertiesUseCaseMock.getAllProperties()).thenReturn(responses);

        //Act + Assert
        mockMvc.perform(get("/properties"))
                .andDo(print())
                .andExpect(header().string("Content-Type",APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(gson.toJson(responses)));

    }

    /**
     * @verifies return 401 if user is not logged-in
     * @see PropertyController#getPropertyDetail(long)
     */
    @Test
    void getPropertyDetail_shouldReturn401IfUserIsNotLoggedin() throws Exception {
        mockMvc.perform(get("/properties/1"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    /**
     * @verifies return 200 if user is logged-in
     * @see PropertyController#getPropertyDetail(long)
     */
    @Test
    @WithMockUser(roles = {"ADMIN","OWNER","CUSTOMER"})
    void getPropertyDetail_shouldReturn200IfUserIsLoggedin() throws Exception {
        //Arrange
        GetPropertyDetailResponse response=GetPropertyDetailResponse.builder()
                .id(1L)
                .build();

        when(getPropertyDetailUseCaseMock.getPropertyDetail(1L)).thenReturn(response);

        //Act + Assert
        mockMvc.perform(get("/properties/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type",APPLICATION_JSON_VALUE))
                .andExpect(content().json(gson.toJson(response)));

    }

    /**
     * @verifies return 401 if user is not logged-in
     * @see PropertyController#getRentedNotRentedPropertyRatio()
     */
    @Test
    void getRentedNotRentedPropertyRatio_shouldReturn401IfUserIsNotLoggedin() throws Exception {
        mockMvc.perform(get("/properties/dashboard/rentedRatio"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    /**
     * @verifies return 403 if user is customer
     * @see PropertyController#getRentedNotRentedPropertyRatio()
     */
    @Test
    @WithMockUser(roles = "CUSTOMER")
    void getRentedNotRentedPropertyRatio_shouldReturn403IfUserIsCustomer() throws Exception {
        mockMvc.perform(get("/properties/dashboard/rentedRatio"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    /**
     * @verifies return 204 if user is authorized
     * @see PropertyController#getRentedNotRentedPropertyRatio()
     */
    @Test
    @WithMockUser(roles = {"ADMIN","OWNER"})
    void getRentedNotRentedPropertyRatio_shouldReturn204IfUserIsAuthorized() throws Exception {
        //Arrange
        GetRentedNotRentedPropertyRatioResponse response=GetRentedNotRentedPropertyRatioResponse.builder()
                .build();

        when(getRentedNotRentedPropertyRatioUseCaseMock.getRentedNotRentedPropertyRatio()).thenReturn(response);

        //Act + Assert
        mockMvc.perform(get("/properties/dashboard/rentedRatio"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type",APPLICATION_JSON_VALUE))
                .andExpect(content().json(gson.toJson(response)));

    }

    /**
     * @verifies return 401 if user is not logged-in
     * @see PropertyController#createProperty(nl.fontys.s3.huister.business.request.property.CreatePropertyRequest)
     */
    @Test
    void createProperty_shouldReturn401IfUserIsNotLoggedin() throws Exception {
        //Arrange
        CreatePropertyRequest request=CreatePropertyRequest.builder().build();

        //Act + Assert
        mockMvc.perform(post("/properties")
                .contentType(APPLICATION_JSON_VALUE)
                .content(gson.toJson(request)))
                .andDo(print())
                .andExpect(status().isUnauthorized());

    }

    /**
     * @verifies return 403 if user is not authorized
     * @see PropertyController#createProperty(nl.fontys.s3.huister.business.request.property.CreatePropertyRequest)
     */
    @Test
    @WithMockUser(roles = {"ADMIN","CUSTOMER"})
    void createProperty_shouldReturn403IfUserIsNotAuthorized() throws Exception {
        //Arrange
        CreatePropertyRequest request=CreatePropertyRequest.builder().build();

        //Act + Assert
        mockMvc.perform(post("/properties")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(gson.toJson(request)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    /**
     * @verifies return 201 if user is owner
     * @see PropertyController#createProperty(nl.fontys.s3.huister.business.request.property.CreatePropertyRequest)
     */
    @Test
    @WithMockUser(roles = {"OWNER"})
    void createProperty_shouldReturn201IfUserIsOwner() throws Exception {
        //Arrange
        CreatePropertyRequest request=CreatePropertyRequest.builder().build();

        GetPropertyDetailResponse response=GetPropertyDetailResponse.builder().build();
        when(createPropertyUseCaseMock.createProperty(request)).thenReturn(response);

        //Act + Assert
        mockMvc.perform(post("/properties")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(gson.toJson(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Content-Type",APPLICATION_JSON_VALUE))
                .andExpect(content().json(gson.toJson(response)));

        verify(createPropertyUseCaseMock).createProperty(request);

    }

    /**
     * @verifies return 401 if user is not logged-in
     * @see PropertyController#updateProperty(nl.fontys.s3.huister.business.request.property.UpdatePropertyRequest, long)
     */
    @Test
    void updateProperty_shouldReturn401IfUserIsNotLoggedin() throws Exception {
        //Arrange
        UpdatePropertyRequest request=UpdatePropertyRequest.builder()
                .id(1L)
                .build();

        //Act + Assert
        mockMvc.perform(put("/properties/1")
                .contentType(APPLICATION_JSON_VALUE)
                .content(gson.toJson(request)))
                .andDo(print())
                .andExpect(status().isUnauthorized());

    }

    /**
     * @verifies return 403 if user is not authorized
     * @see PropertyController#updateProperty(nl.fontys.s3.huister.business.request.property.UpdatePropertyRequest, long)
     */
    @Test
    @WithMockUser(roles = {"ADMIN","CUSTOMER"})
    void updateProperty_shouldReturn403IfUserIsNotAuthorized() throws Exception {
        //Arrange
        UpdatePropertyRequest request=UpdatePropertyRequest.builder()
                .id(1L)
                .build();

        //Act + Assert
        mockMvc.perform(put("/properties/1")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(gson.toJson(request)))
                .andDo(print())
                .andExpect(status().isForbidden());


    }

    /**
     * @verifies return 201 if user is owner
     * @see PropertyController#updateProperty(nl.fontys.s3.huister.business.request.property.UpdatePropertyRequest, long)
     */
    @Test
    @WithMockUser(roles = "OWNER")
    void updateProperty_shouldReturn201IfUserIsOwner() throws Exception {
        //Arrange
        UpdatePropertyRequest request=UpdatePropertyRequest.builder()
                .id(1L)
                .build();

        //Act + Assert
        mockMvc.perform(put("/properties/1")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(gson.toJson(request)))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(updatePropertyUseCaseMock).updateProperty(request);

    }

    /**
     * @verifies return 401 if user is not logged-in
     * @see PropertyController#deleteProperty(long)
     */
    @Test
    void deleteProperty_shouldReturn401IfUserIsNotLoggedin() throws Exception {
        mockMvc.perform(delete("/properties/1"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    /**
     * @verifies return 403 if user is not authorized
     * @see PropertyController#deleteProperty(long)
     */
    @Test
    @WithMockUser(roles = {"ADMIN","CUSTOMER"})
    void deleteProperty_shouldReturn403IfUserIsNotAuthorized() throws Exception {
        mockMvc.perform(delete("/properties/1"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    /**
     * @verifies return 201 if user is owner
     * @see PropertyController#deleteProperty(long)
     */
    @Test
    @WithMockUser(roles = "OWNER")
    void deleteProperty_shouldReturn201IfUserIsOwner() throws Exception {
        mockMvc.perform(delete("/properties/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(deletePropertyUseCaseMock).deleteProperty(1L);

    }
}
