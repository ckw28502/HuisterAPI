package nl.fontys.s3.huister.configuration.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
@Slf4j
public class RestCustomExceptionHandler extends ResponseEntityExceptionHandler {

    private static final URI VALIDATION_ERROR_TYPE = URI.create("/validation-error");


    @ExceptionHandler(value = {ResponseStatusException.class})
    public ProblemDetail handleResponseStatusException(final ResponseStatusException error) {
        log.error("ResponseStatusException with status {} occurred.", error.getStatusCode(), error);
        final List<ValidationErrorDTO> errors = error.getReason() != null ?
                List.of(new ValidationErrorDTO(null, error.getReason()))
                : Collections.emptyList();
        return convertToProblemDetail(error.getStatusCode(), errors);
    }

    private ProblemDetail convertToProblemDetail(HttpStatusCode statusCode, List<ValidationErrorDTO> errors) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(statusCode);
        problemDetail.setDetail("Invalid request");
        problemDetail.setProperty("errors", errors);
        problemDetail.setType(VALIDATION_ERROR_TYPE);
        return problemDetail;
    }
    private record ValidationErrorDTO(String field, String error) {
    }
}
