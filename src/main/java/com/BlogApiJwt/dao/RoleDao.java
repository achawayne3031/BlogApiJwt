package com.BlogApiJwt.dao;

import com.BlogApiJwt.entity.Role;

public interface RoleDao {

    Role findByName(String name);
}
