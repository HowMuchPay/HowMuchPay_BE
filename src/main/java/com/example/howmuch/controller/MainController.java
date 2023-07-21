package com.example.howmuch.controller;

import com.example.howmuch.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/main")
@RestController
public class MainController {

    private final UserService userService;

    public ResponseEntity<?> showMain() {
        return new ResponseEntity<>(this.userService.showMain(), HttpStatus.OK);
    }
}
