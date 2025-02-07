package com.arishem.hrms.service;

import com.arishem.hrms.model.ApiResponse;
import com.arishem.hrms.model.UserRequest;
import com.arishem.hrms.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ApiResponse<Map<String, Object>> getUserList(String userId) {
        try {
            List<Object[]> results = userRepository.getUserList(userId);
            List<Map<String, Object>> responseData = new ArrayList<>();

            for (Object[] row : results) {
                Map<String, Object> jsonObject = new HashMap<>();
                jsonObject.put("name", row[0]);        // Assuming 1st column is Name
                jsonObject.put("usercode", row[1]);    // Assuming 2nd column is User Code
                jsonObject.put("address", row[2]);     // Assuming 3rd column is Address
                jsonObject.put("city", row[3]);        // Assuming 4th column is City
                responseData.add(jsonObject);
            }

            return new ApiResponse<>(1, "", "success", responseData);
        } catch (Exception e) {
            return new ApiResponse<>(0, "DB_ERROR", e.getMessage(), new ArrayList<>());
        }
    }

    public ApiResponse<Map<String, Object>> createUser(UserRequest userRequest) {
        try {
            Map<String, Object> result = userRepository.createUser(userRequest);

            int status = (int) result.get("status");
            String message = (String) result.get("message");

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("username", userRequest.getUsername());
            responseData.put("message", message);

            return new ApiResponse<>(status, status == 1 ? "" : "USER_CREATION_FAILED", message, Collections.singletonList(responseData));
        } catch (Exception e) {
            return new ApiResponse<>(0, "DB_ERROR", e.getMessage(), new ArrayList<>());
        }
    }


    public ApiResponse<Map<String, Object>> validateLogin(String username, String password) {
        try {
            Map<String, Object> result = userRepository.validateLogin(username, password);

            int status = (int) result.get("status");
            String message = (String) result.get("message");

            return new ApiResponse<>(status, status == 1 ? "" : "LOGIN_FAILED", message, Collections.singletonList(result));
        } catch (Exception e) {
            return new ApiResponse<>(0, "DB_ERROR", e.getMessage(), Collections.emptyList());
        }
    }

}
