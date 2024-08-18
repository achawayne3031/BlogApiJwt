package com.BlogApiJwt.service;

import com.BlogApiJwt.entity.Blog;
import com.BlogApiJwt.validation.AddBlogValidation;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BlogService {

    void save(AddBlogValidation addBlogValidation, MultipartFile file);

    Blog findById(int id);

    Blog findByTitle(String title);

    List<Blog> allBlog();

    void deleteById(int id);

    Page<Blog> paginatedBlog(int page, int size );
}
