package nl.fontys.s3.huister.controller;

import com.google.gson.Gson;
import nl.fontys.s3.huister.business.interfaces.city.GetAllCitiesUseCase;
import nl.fontys.s3.huister.business.response.city.GetAllCitiesResponse;
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
class CityControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private GetAllCitiesUseCase getAllCitiesUseCaseMock;

    /**
     * @verifies return 401 with unauthorized user
     * @see CityController#getAllCities()
     */
    @Test
    void getAllCities_shouldReturn401WithUnauthorizedUser() throws Exception {
        mockMvc.perform(get("/cities"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    /**
     * @verifies return 200 with logged-in user
     * @see CityController#getAllCities()
     */
    @Test
    @WithMockUser(roles = {"ADMIN","OWNER","CUSTOMER"})
    public void getAllCities_shouldReturn200WithLoggedinUser() throws Exception {
        //Arrange
        GetAllCitiesResponse response=GetAllCitiesResponse.builder()
                .cities(List.of())
                .build();

        when(getAllCitiesUseCaseMock.getAllCities()).thenReturn(response);

        //Act + Assert
        mockMvc.perform(get("/cities"))
                .andDo(print())
                .andExpect(header().string("Content-Type",APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(gson.toJson(response)));

        verify(getAllCitiesUseCaseMock).getAllCities();

    }
}
