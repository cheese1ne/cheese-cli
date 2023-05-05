package com.cheese.boot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sobann
 */
@RestController
@RequestMapping("/api")
public class CheeseController {

    @GetMapping("/user")
    public String getUserName(@RequestParam("username") String username) {
        return "hello " + username;
    }

}
