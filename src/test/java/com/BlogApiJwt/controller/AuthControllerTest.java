package com.BlogApiJwt.controller;


import com.BlogApiJwt.entity.User;
import com.BlogApiJwt.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.http.MediaType;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    User user;


    @BeforeEach
    public void setup(){
        user = User.builder()
                .fullName("John Doe")
                .password("123456789")
                .email("john@gmail.com")
                .build();

    }

    @Test
    @Order(1)
    public void saveEmployeeTest() throws Exception{
        // precondition
        given(userService.save(any(com.BlogApiJwt.validation.UserValidation.class))).willReturn(user);

        // action
        ResultActions response = mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));

        // verify
        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.fullName",
                        is(user.getFullName())))
                .andExpect(jsonPath("$.email",
                        is(user.getEmail())))
                .andExpect(jsonPath("$.password",
                        is(user.getPassword())));
    }




}
