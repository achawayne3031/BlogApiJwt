package com.BlogApiJwt.service;

import com.BlogApiJwt.entity.User;
import com.BlogApiJwt.validation.ResetPasswordValidation;
import com.BlogApiJwt.validation.UpdateUserValidation;
import com.BlogApiJwt.validation.UserLoginValidation;
import com.BlogApiJwt.validation.UserValidation;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    User findByEmail(String email);

    User findByFullName(String fullName);

    User findByUserName(String username);

    User save(UserValidation validation);

    User loginUser(UserLoginValidation userLoginValidation);

    void update(UpdateUserValidation updateUserValidation);

    void resetPassword(ResetPasswordValidation resetPasswordValidation);
}
