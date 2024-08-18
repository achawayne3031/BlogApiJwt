package com.BlogApiJwt.dao;

import com.BlogApiJwt.entity.Blog;

import java.util.List;

public interface BlogDao {

    Blog findById(int id);

    Blog findByTitle(String title);

    void save(Blog blog);

    void deleteById(int id);

    List<Blog> allBlog();
}
