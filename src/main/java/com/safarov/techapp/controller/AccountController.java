package com.safarov.techapp.controller;


import com.safarov.techapp.service.AccountService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v1")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AccountController {
    AccountService accountService;

    @GetMapping("/accounts")
    public ResponseEntity<?> getAccounts(){
        return new  ResponseEntity<>(accountService.getAccountsForUser(), HttpStatus.OK);
    }
}
