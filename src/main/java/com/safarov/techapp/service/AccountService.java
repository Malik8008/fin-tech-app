package com.safarov.techapp.service;

import com.safarov.techapp.dto.response.AccountResponseDTOList;
import com.safarov.techapp.dto.response.CommonResponseDTO;
import com.safarov.techapp.dto.response.Status;
import com.safarov.techapp.dto.response.StatusCode;
import com.safarov.techapp.entity.TechUser;
import com.safarov.techapp.repositories.UserRepository;
import com.safarov.techapp.util.CurrentUserUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountService {
    UserRepository userRepository;
    CurrentUserUtil currentUserUtil;

    public CommonResponseDTO<?> getAccountsForUser() {
        Optional<TechUser> user = userRepository.findByPin(currentUserUtil.getCurrentUser().getUsername());
        return CommonResponseDTO.builder()
                .status(Status.builder()
                        .statusCode(StatusCode.SUCCESS)
                        .message("Account(s) for user: " +
                                currentUserUtil.getCurrentUser().getUsername() + " was successfully fetched.")
                        .build()).data(AccountResponseDTOList.entityToDTO(user.get().getAccountList())).build();
    }
}
