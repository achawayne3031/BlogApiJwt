package com.BlogApiJwt.dao;

import com.BlogApiJwt.entity.User;

public interface UserDao {

    User findUserByEmail(String email);

    User findUserByFullName(String fullName);

    void save(User user);
}
