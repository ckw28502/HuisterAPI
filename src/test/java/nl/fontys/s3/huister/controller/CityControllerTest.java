package nl.fontys.s3.huister.controller;

import com.google.gson.Gson;
import nl.fontys.s3.huister.business.interfaces.city.GetAllCitiesUseCase;
import nl.fontys.s3.huister.business.response.city.GetAllCitiesResponse;
import nl.fontys.s3.huister.configuration.utilities.GsonConfig;
import nl.fontys.s3.huister.domain.entities.CityEntity;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(CityController.class)
@Import(GsonConfig.class)
class CityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private GetAllCitiesUseCase getAllCitiesUseCaseMock;

    /**
     * @verifies return an empty list if there are no city
     * @see CityController#getAllCities(long)
     */
    @Test
    void getAllCities_shouldReturnAnEmptyListIfThereAreNoCity() throws Exception {
        //Arrange
        GetAllCitiesResponse response=GetAllCitiesResponse.builder()
                .cities(List.of())
                .build();

        when(getAllCitiesUseCaseMock.getAllCities(1)).thenReturn(response);

        //Act + assert
        mockMvc.perform(get("/cities/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type",APPLICATION_JSON_VALUE))
                .andExpect(content().json(gson.toJson(response)));

        verify(getAllCitiesUseCaseMock).getAllCities(1);
    }

    /**
     * @verifies return a list of cities when there are cities
     * @see CityController#getAllCities(long)
     */
    @Test
    void getAllCities_shouldReturnAListOfCitiesWhenThereAreCities() throws Exception {
        //Arrange
        CityEntity city1=CityEntity.builder()
                .id(1)
                .name("city1")
                .build();
        CityEntity city2=CityEntity.builder()
                .id(2)
                .name("city2")
                .build();

        GetAllCitiesResponse response=GetAllCitiesResponse.builder()
                .cities(List.of(city1,city2))
                .build();

        when(getAllCitiesUseCaseMock.getAllCities(1)).thenReturn(response);

        //Act + assert
        mockMvc.perform(get("/cities/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type",APPLICATION_JSON_VALUE))
                .andExpect(content().json(new Gson().toJson(response)));

        verify(getAllCitiesUseCaseMock).getAllCities(1);
    }
}
