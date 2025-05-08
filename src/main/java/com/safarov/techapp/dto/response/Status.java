package com.safarov.techapp.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class Status implements Serializable {
    private static final long serialVersionUID = 1L;

    StatusCode statusCode;
    String message;
}
