package com.arishem.hrms.controller;

import com.arishem.hrms.model.ApiResponse;
import com.arishem.hrms.model.UserRequest;
import com.arishem.hrms.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("GetUsers/{userId}")
    public ApiResponse<Map<String, Object>> getUserList(@PathVariable String userId) {
        return userService.getUserList(userId);
    }

    @PostMapping("/create")
    public ApiResponse<Map<String, Object>> createUser(@RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @GetMapping("/validateLogin")
    public ApiResponse<Map<String, Object>> validateLogin(
            @RequestParam String username,
            @RequestParam String password
    ) {
        return userService.validateLogin(username, password);
    }
}
