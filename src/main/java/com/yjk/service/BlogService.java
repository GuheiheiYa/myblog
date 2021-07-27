package com.yjk.service;

import com.yjk.pojo.Blog;
import com.yjk.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {

    Blog getBlog(Long id);

    Blog getAndConvert(Long id);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Page<Blog> listBlog(Pageable pageable);

    Page<Blog> listBlog(String query,Pageable pageable);

    List<Blog> listBlogTop(Integer size);

    Map<String,List<Blog>> archiveBlog();

    public Long countBlog();

    Blog saveBlog(Blog blog);

    Blog update(Long id,Blog blog);

    void delete(Long id);
}
