package nl.fontys.s3.huister.controller;

import com.google.gson.Gson;
import nl.fontys.s3.huister.business.interfaces.property.*;
import nl.fontys.s3.huister.business.request.property.CreatePropertyRequest;
import nl.fontys.s3.huister.business.request.property.UpdatePropertyRequest;
import nl.fontys.s3.huister.business.response.property.GetAllPropertiesResponse;
import nl.fontys.s3.huister.business.response.property.GetPropertyDetailResponse;
import nl.fontys.s3.huister.business.response.property.GetRentedNotRentedPropertyRatioResponse;
import nl.fontys.s3.huister.configuration.utilities.GsonConfig;
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
@WebMvcTest(PropertyController.class)
@Import(GsonConfig.class)
public class PropertyControllerTest {

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
     * @verifies return an empty list content if no properties found
     * @see PropertyController#getAllProperties(long)
     */
    @Test
    void getAllProperties_shouldReturnAnEmptyListContentIfNoPropertiesFound() throws Exception {
        //Arrange
        List<GetAllPropertiesResponse>responses=List.of();

        when(getAllPropertiesUseCaseMock.getAllProperties(1L)).thenReturn(responses);

        //Act + Assert
        mockMvc.perform(get("/properties/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("content-type",APPLICATION_JSON_VALUE))
                .andExpect(content().json(gson.toJson(responses)));

    }

    /**
     * @verifies return list of properties content if properties are found
     * @see PropertyController#getAllProperties(long)
     */
    @Test
    void getAllProperties_shouldReturnListOfPropertiesContentIfPropertiesAreFound() throws Exception {
        //Arrange
        List<GetAllPropertiesResponse>responses=List.of(GetAllPropertiesResponse.builder()
                .price(300)
                .id(1L)
                .cityName("city")
                .area(12)
                .ownerName("owner")
                .postCode("1111AA")
                .streetName("property street")
                .description("description")
                .imageUrl("property.jpg")
                .build());

        when(getAllPropertiesUseCaseMock.getAllProperties(1L)).thenReturn(responses);

        //Act + Assert
        mockMvc.perform(get("/properties/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("content-type",APPLICATION_JSON_VALUE))
                .andExpect(content().json(gson.toJson(responses)));
    }

    /**
     * @verifies return property content
     * @see PropertyController#getPropertyDetail(long)
     */
    @Test
    void getPropertyDetail_shouldReturnPropertyContent() throws Exception {
        //Arrange
        GetPropertyDetailResponse response=GetPropertyDetailResponse.builder()
                .area(1)
                .ownerId(1L)
                .description("property")
                .postCode("1111AA")
                .streetName("property street")
                .ownerName("owner")
                .cityName("city")
                .price(1)
                .id(1L)
                .imageUrl("property.jpg")
                .build();

        when(getPropertyDetailUseCaseMock.getPropertyDetail(response.getId())).thenReturn(response);

        //Act + Assert
        mockMvc.perform(get("/properties/detail/1"))
                .andDo(print())
                .andExpect(header().string("content-type",APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(gson.toJson(response)));

    }

    /**
     * @verifies return a valid response
     * @see PropertyController#getRentedNotRentedPropertyRatio(long)
     */
    @Test
    void getRentedNotRentedPropertyRatio_shouldReturnAValidResponse() throws Exception {
        //Arrange
        GetRentedNotRentedPropertyRatioResponse response=GetRentedNotRentedPropertyRatioResponse.builder()
                .rented(1)
                .notRented(1)
                .build();

        when(getRentedNotRentedPropertyRatioUseCaseMock.getRentedNotRentedPropertyRatio(1L)).thenReturn(response);
        //Act + Assert
        mockMvc.perform(get("/properties/dashboard/rentedRatio/1"))
                .andDo(print())
                .andExpect(header().string("content-type",APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(gson.toJson(response)));
    }

    /**
     * @verifies create property
     * @see PropertyController#createProperty(nl.fontys.s3.huister.business.request.property.CreatePropertyRequest)
     */
    @Test
    void createProperty_shouldCreateProperty() throws Exception {
        //Arrange
        CreatePropertyRequest request=CreatePropertyRequest.builder()
                .area(1)
                .ownerId(1L)
                .description("property")
                .postCode("1111AA")
                .streetName("property street")
                .cityName("city")
                .price(1)
                .imageUrl("property.jpg")
                .build();

        //Act + Assert
        mockMvc.perform(post("/properties")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(gson.toJson(request)))
                .andDo(print())
                .andExpect(status().isCreated());

        verify(createPropertyUseCaseMock).createProperty(request);

    }

    /**
     * @verifies update property
     * @see PropertyController#updateProperty(nl.fontys.s3.huister.business.request.property.UpdatePropertyRequest, long)
     */
    @Test
    void updateProperty_shouldUpdateProperty() throws Exception {
        //Arrange
        UpdatePropertyRequest request=UpdatePropertyRequest.builder()
                .imageUrl("property.png")
                .description("property")
                .id(1L)
                .price(1)
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
     * @verifies delete property
     * @see PropertyController#deleteProperty(long)
     */
    @Test
    public void deleteProperty_shouldDeleteProperty() throws Exception {
        mockMvc.perform(delete("/properties/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(deletePropertyUseCaseMock).deleteProperty(1L);

    }
}
