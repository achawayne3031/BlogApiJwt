package com.BlogApiJwt.service.impl;

import com.BlogApiJwt.dao.BlogDao;
import com.BlogApiJwt.dao.UserDao;
import com.BlogApiJwt.entity.Blog;
import com.BlogApiJwt.entity.User;
import com.BlogApiJwt.exception.CustomException;
import com.BlogApiJwt.repository.BlogRepository;
import com.BlogApiJwt.security.SecurityConfig;
import com.BlogApiJwt.service.BlogService;
import com.BlogApiJwt.validation.AddBlogValidation;
import com.BlogApiJwt.validation.UpdateBlogValidation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
public class BlogServiceImpl implements BlogService {

    private final BlogDao blogDao;
    private final UserDao userDao;
    private final BlogRepository blogRepository;

    public BlogServiceImpl(BlogDao blogDao, UserDao userDao, BlogRepository blogRepository) {
        this.blogDao = blogDao;
        this.userDao = userDao;
        this.blogRepository = blogRepository;
    }

    @Override
    public void save(AddBlogValidation addBlogValidation, MultipartFile file) {

        Blog blog = new Blog();
        blog.setContent(addBlogValidation.getContent());
        blog.setEnabled(true);
        blog.setTitle(addBlogValidation.getTitle());


        /// Set user ////
        String userEmail = SecurityConfig.getAuthenticatedUserEmail();
        User user = userDao.findUserByEmail(userEmail);
        blog.setUser(user);

        /// Handle file Uploaded /////
        try{
            /// convert to String of bytes /////
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());

            blog.setImageType(file.getContentType());
            blog.setImage(file.getBytes());

        }catch (IOException e){
            throw new CustomException(e.getMessage());
        }


      ///  FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes());

         blogDao.save(blog);
    }

    @Override
    public Blog findById(int id) {
        return blogDao.findById(id);
    }

    @Override
    public Blog findByTitle(String title) {
        return blogDao.findByTitle(title);
    }

    @Override
    public List<Blog> allBlog() {
        return blogDao.allBlog();
    }

    @Override
    public void deleteById(int id) {
        blogDao.deleteById(id);
    }

    @Override
    public Page<Blog> paginatedBlog(int page, int size) {
        Pageable pager = PageRequest.of(page, size);

        return blogRepository.findAll(pager);
    }

    @Override
    public boolean existByTitle(String title) {
        return blogDao.existByTitle(title);
    }

    @Override
    public void update(UpdateBlogValidation updateBlogValidation, MultipartFile file) {

        Blog blog = blogDao.findById(updateBlogValidation.getId());
        blog.setTitle(updateBlogValidation.getTitle() != null ? updateBlogValidation.getTitle() : blog.getTitle());
        blog.setContent(updateBlogValidation.getContent() != null ? updateBlogValidation.getContent() : blog.getContent());

        if(!file.isEmpty()){
            /// Handle file Uploaded /////
            try{
                /// convert to String of bytes /////
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                blog.setImageType(file.getContentType());
                blog.setImage(file.getBytes());
            }catch (IOException e){
                throw new CustomException(e.getMessage());
            }
        }
        blogDao.update(blog);
    }
}
