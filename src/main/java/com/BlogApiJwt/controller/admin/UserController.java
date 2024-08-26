package com.BlogApiJwt.controller.admin;


import com.BlogApiJwt.config.ApiResponse;
import com.BlogApiJwt.dao.UserDao;
import com.BlogApiJwt.entity.Blog;
import com.BlogApiJwt.entity.User;
import com.BlogApiJwt.service.BlogService;
import com.BlogApiJwt.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/users")
public class UserController {

    private UserDao userDao;

    private BlogService blogService;

    private UserService userService;

    public UserController(UserDao userDao, BlogService blogService, UserService userService) {
        this.userDao = userDao;
        this.blogService = blogService;
        this.userService = userService;
    }



    @GetMapping("/")
    public ResponseEntity allUsers(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ){
        Page<User> users = userService.paginatedUsers(page, size);
        return new ResponseEntity<>(new ApiResponse<Object>("All user fetched successfully", true, users), HttpStatus.OK);
    }

}
