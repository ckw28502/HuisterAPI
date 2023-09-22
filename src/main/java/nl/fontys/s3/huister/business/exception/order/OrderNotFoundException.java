package nl.fontys.s3.huister.business.exception.order;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class OrderNotFoundException extends ResponseStatusException {
    public OrderNotFoundException() {
        super(HttpStatus.BAD_REQUEST,"ORDER_NOT_FOUND");
    }
}
