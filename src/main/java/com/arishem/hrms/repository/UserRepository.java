package com.arishem.hrms.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Repository;
import java.util.List;

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
}
