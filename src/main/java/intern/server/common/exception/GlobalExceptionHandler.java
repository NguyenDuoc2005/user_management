package intern.server.common.exception;

import intern.server.common.base.ResponseObject;
import jakarta.validation.ConstraintViolationException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    public ResponseEntity<ResponseObject<?>> handleBadRequestException(BadRequestException ex) {
        ResponseObject<String> response = new ResponseObject<>();
        response.setSuccess(false);
        response.setStatus(HttpStatus.BAD_REQUEST);
        response.setMessage(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public ResponseEntity<ResponseObject<?>> handleUnauthorizedException(UnauthorizedException ex) {
        ResponseObject<String> response = new ResponseObject<>();
        response.setSuccess(false);
        response.setStatus(HttpStatus.UNAUTHORIZED);
        response.setMessage(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseBody
    public ResponseEntity<ResponseObject<?>> handleInternalServerErrorException(InternalServerErrorException ex) {
        ResponseObject<String> response = new ResponseObject<>();
        response.setSuccess(false);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        response.setMessage(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ResponseObject<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        Map<String, String> errorDetails = fieldErrors.stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage
                ));

        ResponseObject<Map<String, String>> response = new ResponseObject<>();
        response.setSuccess(false);
        response.setStatus(HttpStatus.BAD_REQUEST);
        response.setMessage("Dữ liệu không hợp lệ");

        response.setData(errorDetails);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<ResponseObject<?>> handleConstraintViolationException(ConstraintViolationException ex) {
        String errorMessage = ex.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining(", "));

        ResponseObject<String> response = new ResponseObject<>();
        response.setSuccess(false);
        response.setStatus(HttpStatus.BAD_REQUEST);
        response.setMessage("Lỗi validation: " + errorMessage);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ResponseObject<?>> handleException(Exception ex) {
        ResponseObject<String> response = new ResponseObject<>();
        response.setSuccess(false);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        response.setMessage("Đã xảy ra lỗi: " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
