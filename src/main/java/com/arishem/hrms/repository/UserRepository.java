package com.arishem.hrms.repository;

import com.arishem.hrms.model.UserLoginResponse;
import com.arishem.hrms.model.UserRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.Query;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {

    private final EntityManager entityManager;

    public UserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Object[]> getUserList(String userId) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("dbo.getuserlist");
        query.registerStoredProcedureParameter(1, String.class, jakarta.persistence.ParameterMode.IN);
        query.setParameter(1, userId);
        query.execute();
        return query.getResultList();
    }
//    create users

    public Map<String, Object> createUser(UserRequest userRequest) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("dbo.CreateNewUser");

        // ✅ Register Input Parameters
        query.registerStoredProcedureParameter("prmusername", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("prmpassword", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("prmfname", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("prmlname", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("prmemail", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("prmdob", Timestamp.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("prmgendor", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("prmroletype", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("prmmobno", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("prmimg", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("prmstate", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("prmdistrict", String.class, ParameterMode.IN);

        // ✅ Register OUTPUT Parameters
        query.registerStoredProcedureParameter("commandstatus", Integer.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("commandmessage", String.class, ParameterMode.OUT);

        // ✅ Set Input Parameters
        query.setParameter("prmusername", userRequest.getUsername());
        query.setParameter("prmpassword", userRequest.getPassword());
        query.setParameter("prmfname", userRequest.getFname());
        query.setParameter("prmlname", userRequest.getLname() == null ? null : userRequest.getLname());
        query.setParameter("prmemail", userRequest.getEmail());

        // ✅ Convert String to Timestamp for DOB
        LocalDateTime localDateTime = LocalDateTime.parse(userRequest.getDob());
        query.setParameter("prmdob", Timestamp.valueOf(localDateTime));

        query.setParameter("prmgendor", userRequest.getGender());
        query.setParameter("prmroletype", userRequest.getRoleType());
        query.setParameter("prmmobno", userRequest.getMobile());
        query.setParameter("prmimg", userRequest.getImg());
        query.setParameter("prmstate", userRequest.getState());
        query.setParameter("prmdistrict", userRequest.getDistrict());

        // ✅ Execute the Stored Procedure
        query.execute();

        // ✅ Retrieve Output Parameters
        Integer status = (Integer) query.getOutputParameterValue("commandstatus");
        String message = (String) query.getOutputParameterValue("commandmessage");

        // ✅ Handle null values to prevent NullPointerException
        if (status == null) {
            status = 0;  // Default failure status
        }
        if (message == null) {
            message = "Unknown error occurred.";
        }

        // ✅ Return response as a Map
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put("message", message);
        return response;
    }

    public Map<String, Object> validateLogin(String username, String password) {
        // ✅ Call Stored Procedure using Native Query
        Query query = entityManager.createNativeQuery("EXEC dbo.validateLogin ?, ?")
                .setParameter(1, username)
                .setParameter(2, password);

        // ✅ Get Result
        List<Object[]> resultList = query.getResultList();

        // ✅ Prepare Response
        Map<String, Object> response = new HashMap<>();

        if (!resultList.isEmpty()) {
            Object[] row = resultList.get(0); // Get first row

            response.put("status", row[0]);  // commandstatus
            response.put("message", row[1]); // commandmessage
            response.put("userid", row[2]);  // userid
            response.put("displayname", row[3]); // displayname
            response.put("imagepath", row[4]); // imagepath
        } else {
            response.put("status", -2);
            response.put("message", "User not found");
        }

        return response;
    }

}
