package com.safarov.techapp.service;

import com.safarov.techapp.dto.request.AccountRequestDTO;
import com.safarov.techapp.dto.request.UserRequestDTO;
import com.safarov.techapp.dto.response.CommonResponseDTO;
import com.safarov.techapp.dto.response.Status;
import com.safarov.techapp.dto.response.StatusCode;
import com.safarov.techapp.dto.response.UserResponseDTO;
import com.safarov.techapp.entity.Account;
import com.safarov.techapp.entity.TechUser;
import com.safarov.techapp.exception.AccountAlreadyExistException;
import com.safarov.techapp.exception.UserAlreadyExistException;
import com.safarov.techapp.repositories.AccountRepository;
import com.safarov.techapp.repositories.UserRepository;
import com.safarov.techapp.util.DTOCheckUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserRegisterService {

    AccountRepository accountRepository;
    PasswordEncoder passwordEncoder;
    UserRepository userRepository;
    DTOCheckUtil dtoCheckUtil;

    public CommonResponseDTO<?> saveUser(UserRequestDTO userRequestDTO) {
        dtoCheckUtil.isValid(userRequestDTO);
        checkUserExist(userRequestDTO);
        checkAccountExist(userRequestDTO);
        TechUser user = createUserEntity(userRequestDTO);
        TechUser savedUser = userRepository.save(user);

        return CommonResponseDTO.builder().status(Status.builder()
                .statusCode(StatusCode.SUCCESS)
                .message("Registration has been successful")
                .build()).data(UserResponseDTO.entityResponse(savedUser)).build();
    }

    private void checkUserExist(UserRequestDTO userRequestDTO) {
        Optional<TechUser> userRepositoryByPin = userRepository.findByPin(userRequestDTO.getPin());
        if (userRepositoryByPin.isPresent()) {
            throw UserAlreadyExistException.builder().responseDTO(CommonResponseDTO.builder()
                    .status(Status.builder()
                            .statusCode(StatusCode.AlREADY_EXIST)
                            .message("User with pin: " + userRequestDTO.getPin() +
                                    "is exist. Please enter a pin that not bean register before")
                            .build()).build()).build();
        }
    }

    private TechUser createUserEntity(UserRequestDTO userRequestDTO) {
        TechUser user = TechUser.builder()
                .name(userRequestDTO.getName())
                .surname(userRequestDTO.getSurname())
                .password(passwordEncoder.encode(userRequestDTO.getPassword()))
                .pin(userRequestDTO.getPin())
                .role("USER_ROLE").build();
        user.addAccountToUser(userRequestDTO.getAccountRequestDTOList());
        return user;
    }

    private void checkAccountExist(UserRequestDTO userRequestDTO) {
        List<Integer> accountNoList = accountRepository.findAll()
                .stream()
                .map(Account::getAccountNo)
                .collect(Collectors.toList());
        userRequestDTO.getAccountRequestDTOList()
                .stream()
                .map(AccountRequestDTO::getAccountNo)
                .filter(accountNoList::contains)
                .findFirst()
                .ifPresent(duplicateAccountNo -> {
                    throw AccountAlreadyExistException.builder().responseDTO(CommonResponseDTO.builder()
                            .status(Status.builder()
                                    .statusCode(StatusCode.AlREADY_EXIST)
                                    .message("Duplicate account no: " + duplicateAccountNo)
                                    .build()).build()).build();
                });
    }
}
