package com.BlogApiJwt.repository;

import com.BlogApiJwt.entity.User;
import org.junit.jupiter.api.*;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;


@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;


    @Test
    @DisplayName("Test 1:Save user Test")
    @Order(1)
    @Rollback(value = true)
    public void saveUserTest(){

        //Action
        User user = User.builder()
                .fullName("Sam")
                .password("123456789")
                .email("sam@gmail.com")
                .build();


        userRepository.save(user);

        //Verify
        System.out.println(user);
        Assertions.assertThat(user.getId()).isGreaterThan(0);
    }
}
