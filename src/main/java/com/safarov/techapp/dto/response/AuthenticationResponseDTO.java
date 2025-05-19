package com.safarov.techapp.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    String token;
}
