package com.BlogApiJwt.dao.impl;

import com.BlogApiJwt.dao.UserDao;
import com.BlogApiJwt.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class UserDaoImpl implements UserDao {


    private EntityManager entityManager;


    public UserDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User findUserByEmail(String email) {
        TypedQuery<User> query = entityManager.createQuery("from User where email = :email", User.class);
        query.setParameter("email", email);
        User user = null;
        try{
            user = query.getSingleResult();
        }catch (Exception e){
            user = null;
        }

        return user;
    }

    @Override
    public User findUserByFullName(String fullName) {
        TypedQuery<User> query = entityManager.createQuery("from User where full_name = :fullName", User.class);
        query.setParameter("fullName", fullName);
        User user = null;

        try{
            user = query.getSingleResult();
        }catch (Exception e){
            user = null;
        }

        return user;
    }

    @Override
    @Transactional
    public void save(User user) {
        entityManager.merge(user);
    }
}
