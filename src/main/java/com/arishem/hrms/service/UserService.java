package com.arishem.hrms.service;

import com.arishem.hrms.model.ApiResponse;
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
}
