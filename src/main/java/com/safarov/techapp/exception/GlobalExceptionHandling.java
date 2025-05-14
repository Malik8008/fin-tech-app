package com.safarov.techapp.exception;

import com.safarov.techapp.dto.response.CommonResponseDTO;
import com.safarov.techapp.dto.response.Status;
import com.safarov.techapp.dto.response.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandling {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> internalError(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<>
                (
                        CommonResponseDTO.builder().status(Status.builder()
                                .statusCode(StatusCode.INTERNAL_ERROR)
                                .message("INTERNAL ERROR")
                                .build()).build(),HttpStatus.INTERNAL_SERVER_ERROR
                );
    }

    @ExceptionHandler(value = InvalidDTOException.class)
    public ResponseEntity<?> invalidDTO(InvalidDTOException invalidDTOException) {
        return new ResponseEntity<>(invalidDTOException.getResponseDTO(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UserAlreadyExistException.class)
    public ResponseEntity<?> userExist(UserAlreadyExistException userAlreadyExistException) {
        return new ResponseEntity<>(userAlreadyExistException.getResponseDTO(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = AccountAlreadyExistException.class)
    public ResponseEntity<?> accountExist(AccountAlreadyExistException accountAlreadyExistException) {
        return new ResponseEntity<>(accountAlreadyExistException.getResponseDTO(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = NoSuchUserExistException.class)
    public ResponseEntity<?> userNotExist(NoSuchUserExistException noSuchUserExistException) {
        return new ResponseEntity<>(noSuchUserExistException.getResponseDTO(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = BadInputCredentialException.class)
    public ResponseEntity<?> badCredentials(BadInputCredentialException badInputCredentialException) {
        return new ResponseEntity<>(badInputCredentialException.getResponseDTO(), HttpStatus.UNAUTHORIZED);
    }
}
