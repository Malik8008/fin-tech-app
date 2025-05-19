package com.safarov.techapp.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserUtil {
    public UserDetails getCurrentUser() {
        return (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
