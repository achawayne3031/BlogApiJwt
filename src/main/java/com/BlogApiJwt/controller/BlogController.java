package com.BlogApiJwt.controller;


import com.BlogApiJwt.config.ApiResponse;
import com.BlogApiJwt.dao.UserDao;
import com.BlogApiJwt.entity.Blog;
import com.BlogApiJwt.entity.User;
import com.BlogApiJwt.exception.CustomException;
import com.BlogApiJwt.security.SecurityConfig;
import com.BlogApiJwt.service.BlogService;
import com.BlogApiJwt.validation.AddBlogValidation;
import com.BlogApiJwt.validation.UpdateBlogValidation;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/user/blog")
public class BlogController {

    private BlogService blogService;

    private List<String> imageFormat =  List.of("image/jpeg", "image/jpg", "image/png");

    private UserDao userDao;


    public BlogController(BlogService blogService, UserDao userDao) {
        this.blogService = blogService;
        this.userDao = userDao;
    }

    @GetMapping("/")
    public ResponseEntity allBlog(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ){

        Page<Blog> blogs = blogService.paginatedBlog(page, size);
        return new ResponseEntity<>(new ApiResponse<Object>("All Blog fetched successfully", true, blogs), HttpStatus.OK);


    }


    @PostMapping(value = "/add", consumes = { "multipart/form-data" })
    public ResponseEntity addBlog(
            @RequestPart(value = "image", required = true) MultipartFile file,
            @RequestPart(value = "title", required = true) String title,
            @RequestPart(value = "content", required =true) String content
            ){

            AddBlogValidation addBlogValidation = new AddBlogValidation(title, content);

            if(blogService.existByTitle(title)){
                throw new CustomException("Title has been taken already");
            }

        try {
            // Check if the file's content type is acceptable
            if(file.isEmpty()){
                return new ResponseEntity<>(new ApiResponse<Object>("Image is required", false), HttpStatus.BAD_REQUEST);
            }

            if(!imageFormat.contains(file.getContentType())){
                return new ResponseEntity<>(new ApiResponse<Object>("Invalid file type", false), HttpStatus.BAD_REQUEST);
            }

            blogService.save(addBlogValidation, file);

            return new ResponseEntity<>(new ApiResponse<Object>("Blog added successfully", true), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not upload the file: " + e.getMessage());
        }
    }


    @PostMapping(value = "/update", consumes = { "multipart/form-data" })
    public ResponseEntity updateBlog(
            @RequestPart(value = "image", required = false) MultipartFile file,
            @RequestPart(value = "title", required = false) String title,
            @RequestPart(value = "content", required = false) String content,
            @RequestPart(value = "id", required = true) int id
    ){

        Blog currentBlog = blogService.findById(id);

        if(currentBlog == null){
            throw new CustomException("Blog not found");
        }

        String userEmail = SecurityConfig.getAuthenticatedUserEmail();
        User user = userDao.findUserByEmail(userEmail);
        if(currentBlog.getUser() != user){
            throw new CustomException("Blog not in your collection");
        }

        UpdateBlogValidation updateBlogValidation = new UpdateBlogValidation(id, title, content);
        try {
            if(!file.isEmpty()){
                System.out.println("We are here");
                if(!imageFormat.contains(file.getContentType())){
                    return new ResponseEntity<>(new ApiResponse<Object>("Invalid file type", false), HttpStatus.BAD_REQUEST);
                }
            }

            blogService.update(updateBlogValidation, file);
            return new ResponseEntity<>(new ApiResponse<Object>("Blog updated successfully", true), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not upload the file: " + e.getMessage());
        }
    }



    @GetMapping("/delete")
    public ResponseEntity deleteBlog(
            @RequestParam("blogId") int blogId
            ){

        Blog currentBlog = blogService.findById(blogId);

        if(currentBlog == null){
            throw new CustomException("Blog not found");
        }

        String userEmail = SecurityConfig.getAuthenticatedUserEmail();
        User user = userDao.findUserByEmail(userEmail);
        if(currentBlog.getUser() != user){
            throw new CustomException("Blog not in your collection");
        }

        blogService.deleteById(blogId);
        return new ResponseEntity<>(new ApiResponse<Object>("Blog deleted successfully", true), HttpStatus.OK);
    }



}
