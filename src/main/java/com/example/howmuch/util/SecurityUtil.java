package com.example.howmuch.util;

import com.example.howmuch.config.security.UserAuthentication;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
@NoArgsConstructor
public class SecurityUtil {

    public static Long getCurrentUserId() {
        UserAuthentication authentication
                = (UserAuthentication) SecurityContextHolder.getContext().getAuthentication();
        return authentication.getUserId();
    }
}
