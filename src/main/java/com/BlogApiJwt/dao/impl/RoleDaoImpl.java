package com.BlogApiJwt.dao.impl;

import com.BlogApiJwt.dao.RoleDao;
import com.BlogApiJwt.entity.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;


@Repository
public class RoleDaoImpl implements RoleDao {

   private EntityManager entityManager;

    public RoleDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Role findByName(String name) {
        TypedQuery<Role> query = entityManager.createQuery("from Role where name = :name", Role.class);
        query.setParameter("name", name);
        Role role = null;

        try{
            role = query.getSingleResult();
        }catch (Exception e){
            role = null;
        }
        return role;
    }
}
