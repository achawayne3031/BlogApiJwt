package com.BlogApiJwt.controller;


import com.BlogApiJwt.config.ApiResponse;
import com.BlogApiJwt.entity.Blog;
import com.BlogApiJwt.service.BlogService;
import com.BlogApiJwt.validation.AddBlogValidation;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/user/blog")
public class BlogController {



    private BlogService blogService;

    private List<String> imageFormat =  List.of("image/jpeg", "image/jpg", "image/png");

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
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


}
