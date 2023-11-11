package nl.fontys.s3.huister.business.impl.city;

import nl.fontys.s3.huister.business.exception.user.UserNotFoundException;
import nl.fontys.s3.huister.business.response.city.GetAllCitiesResponse;
import nl.fontys.s3.huister.configuration.security.token.AccessToken;
import nl.fontys.s3.huister.domain.entities.CityEntity;
import nl.fontys.s3.huister.domain.entities.UserEntity;
import nl.fontys.s3.huister.domain.entities.enumerator.UserRole;
import nl.fontys.s3.huister.persistence.CityRepository;
import nl.fontys.s3.huister.persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllCitiesUseCaseImplTest {
    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private CityRepository cityRepositoryMock;
    @Mock
    private AccessToken requestAccessTokenMock;
    @InjectMocks
    private GetAllCitiesUseCaseImpl getAllCitiesUseCase;

    /**
     * @verifies throw UserNotFoundException when user is not found
     * @see GetAllCitiesUseCaseImpl#getAllCities()
     */
    @Test
    void getAllCities_shouldThrowUserNotFoundExceptionWhenUserIsNotFound() {
        //Arrange
        when(requestAccessTokenMock.getId()).thenReturn(1L);
        when(userRepositoryMock.findById(requestAccessTokenMock.getId())).thenReturn(Optional.empty());

        //Act + Assert
        assertThrows(UserNotFoundException.class,()->getAllCitiesUseCase.getAllCities());
    }

    /**
     * @verifies return an empty list when there is no city
     * @see GetAllCitiesUseCaseImpl#getAllCities()
     */
    @Test
    void getAllCities_shouldReturnAnEmptyListWhenThereIsNoCity() {
        //Arrange
        UserEntity user=UserEntity.builder()
                .id(1L)
                .role(UserRole.ADMIN)
                .build();
        when(requestAccessTokenMock.getId()).thenReturn(1L);
        when(userRepositoryMock.findById(requestAccessTokenMock.getId())).thenReturn(Optional.of(user));

        when(cityRepositoryMock.findAll()).thenReturn(List.of());

        GetAllCitiesResponse expectedResponses=GetAllCitiesResponse.builder()
                .cities(List.of())
                .build();

        //Act
        GetAllCitiesResponse actualResponse=getAllCitiesUseCase.getAllCities();

        //Assert
        assertEquals(expectedResponses,actualResponse);
    }

    /**
     * @verifies return list of cities when cities found according to user's role
     * @see GetAllCitiesUseCaseImpl#getAllCities()
     */
    @ParameterizedTest
    @ValueSource(strings = {"ADMIN","OWNER","CUSTOMER"})
    void getAllCities_shouldReturnListOfCitiesWhenCitiesFoundAccordingToUsersRole(UserRole role) {
        //Arrange
        UserEntity user=UserEntity.builder()
                .id(1L)
                .role(role)
                .build();

        CityEntity city1=CityEntity.builder()
                .id(1L)
                .name("city1")
                .build();

        CityEntity city2=CityEntity.builder()
                .id(2L)
                .name("city2")
                .build();
        when(requestAccessTokenMock.getId()).thenReturn(1L);
        when(userRepositoryMock.findById(requestAccessTokenMock.getId())).thenReturn(Optional.of(user));

        List<CityEntity>cities=new ArrayList<>();
        switch (role){
            case ADMIN -> {
                cities.addAll(List.of(city1,city2));
                when(cityRepositoryMock.findAll()).thenReturn(cities);
            }
            case OWNER -> {
                cities.add(city1);
                when(cityRepositoryMock.findByOwnerId(user.getId())).thenReturn(cities);
            }
            case CUSTOMER -> {
                cities.add(city2);
                when(cityRepositoryMock.findCityByEndRentIsNull()).thenReturn(cities);
            }
        }

        GetAllCitiesResponse expectedResponses=GetAllCitiesResponse.builder()
                .cities(cities)
                .build();

        //Act
        GetAllCitiesResponse actualResponse=getAllCitiesUseCase.getAllCities();

        //Assert
        assertEquals(expectedResponses,actualResponse);
    }
}
