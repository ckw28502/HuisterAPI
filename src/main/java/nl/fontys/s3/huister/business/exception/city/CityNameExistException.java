package nl.fontys.s3.huister.business.exception.city;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CityNameExistException extends ResponseStatusException {
    public CityNameExistException() {
        super(HttpStatus.BAD_REQUEST,"CITY_NAME_ALREADY_EXISTS");
    }
}
