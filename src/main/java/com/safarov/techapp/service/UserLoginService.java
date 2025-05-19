package com.safarov.techapp.service;

import com.safarov.techapp.config.security.JWTUtil;
import com.safarov.techapp.dto.request.AuthenticationRequestDTO;
import com.safarov.techapp.dto.response.AuthenticationResponseDTO;
import com.safarov.techapp.dto.response.CommonResponseDTO;
import com.safarov.techapp.dto.response.Status;
import com.safarov.techapp.dto.response.StatusCode;
import com.safarov.techapp.exception.BadInputCredentialException;
import com.safarov.techapp.util.DTOCheckUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserLoginService {
    AuthenticationManager authenticationManager;
    UserDetailsService userDetailsService;
    DTOCheckUtil dtoCheckUtil;
    JWTUtil jwtUtil;

    public CommonResponseDTO<?> loginUser(AuthenticationRequestDTO authenticationRequestDTO) {
        try {
            dtoCheckUtil.isValid(authenticationRequestDTO);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequestDTO.getPin(),
                    authenticationRequestDTO.getPassword()
            ));
        } catch (Exception e) {
            e.printStackTrace();
            throw BadInputCredentialException.builder().responseDTO(CommonResponseDTO.builder()
                    .status(Status.builder()
                            .statusCode(StatusCode.BAD_CREDENTIALS)
                            .message("The credentials are incorrect. Pin: "
                                    + authenticationRequestDTO.getPin() +
                                    " or password: " + authenticationRequestDTO.getPassword() + " is wrong")
                            .build()).build()).build();
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequestDTO.getPin());
        return CommonResponseDTO.builder().status(Status.builder()
                .statusCode(StatusCode.SUCCESS)
                .message("Welcome to the FinTech application. Token was generated successfully")
                .build()).data(AuthenticationResponseDTO.builder()
                .token(jwtUtil.createToken(userDetails)).build()).build();
    }
}
