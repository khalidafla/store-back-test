package ma.alten.store.advice;

import lombok.extern.slf4j.Slf4j;
import ma.alten.store.exceptions.InvalidInputException;
import ma.alten.store.exceptions.ResourceNotFoundException;
import ma.alten.store.exceptions.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ProductNotFoundAdvice {

    @ExceptionHandler({InvalidInputException.class, HttpMessageNotReadableException.class, UserException.class})
    public ResponseEntity<ProblemDetail> handleInvalidInput(InvalidInputException ex, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Invalid input data"
        );

        problemDetail.setProperty("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));

        Map<String, String> errorDetail = new HashMap<>();
        errorDetail.put("message", ex.getMessage());

        problemDetail.setProperty("errors", List.of(errorDetail));

        return ResponseEntity.badRequest().body(problemDetail);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                "Resource not found"
        );

        problemDetail.setProperty("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));

        Map<String, String> errorDetail = new HashMap<>();
        errorDetail.put("message", ex.getMessage());

        problemDetail.setProperty("errors", List.of(errorDetail));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Validation error occurred"
        );

        problemDetail.setProperty("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));

        List<Map<String, String>> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> Map.of(
                        "field", error.getField(),
                        "message", error.getDefaultMessage()
                ))
                .collect(Collectors.toList());

        problemDetail.setProperty("errors", errors);
        return ResponseEntity.badRequest().body(problemDetail);
    }
}
