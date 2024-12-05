package com.BlogApiJwt.controller;


import com.BlogApiJwt.dao.UserDao;
import com.BlogApiJwt.entity.Blog;
import com.BlogApiJwt.entity.User;
import com.BlogApiJwt.service.BlogService;
import com.BlogApiJwt.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BlogController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BlogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BlogService blogService;

    @Autowired
    private ObjectMapper objectMapper;

    Blog blog;

    @MockBean
    private UserDao userDao;


    //Get Controller
    @Test
    @DisplayName("Test 1: Get All Blogs Test")
    @Order(1)
    public void getBlogTest() throws Exception{

        // action
        ResultActions response = mockMvc.perform(get("/api/user/blog"));

        // verify the output
        response.andExpect(status().isOk())
                .andDo(print());

    }


}
