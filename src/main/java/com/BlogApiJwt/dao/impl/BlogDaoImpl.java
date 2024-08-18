package com.BlogApiJwt.dao.impl;

import com.BlogApiJwt.dao.BlogDao;
import com.BlogApiJwt.entity.Blog;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class BlogDaoImpl implements BlogDao {

    private EntityManager entityManager;

    public BlogDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Blog findById(int id) {

        TypedQuery<Blog> query = entityManager.createQuery("from Blog where id = :id", Blog.class);
        query.setParameter("id", id);
        Blog blog = null;

        try{
            blog = query.getSingleResult();
        }catch(Exception e){
            blog = null;
        }
        return blog;
    }

    @Override
    public Blog findByTitle(String title) {

        TypedQuery<Blog> query = entityManager.createQuery("from Blog where title = :title", Blog.class);
        query.setParameter("title", title);
        Blog blog = null;

        try{
            blog = query.getSingleResult();
        }catch (Exception e){
            blog = null;
        }
        return blog;
    }

    @Override
    @Transactional
    public void save(Blog blog) {
        entityManager.merge(blog);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        Blog blog = entityManager.find(Blog.class, id);
        entityManager.remove(blog);
    }

    @Override
    public List<Blog> allBlog() {
        TypedQuery<Blog> query = entityManager.createQuery("select b from Blog b JOIN FETCH b.user", Blog.class);
        List<Blog> blogs = null;

        try{
            blogs = query.getResultList();
        }catch (Exception e){
            blogs = null;
        }
        return blogs;
    }
}
