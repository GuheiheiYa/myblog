package com.yjk.web;

import com.yjk.handler.NotFoundException;
import com.yjk.pojo.Blog;
import com.yjk.service.BlogService;
import com.yjk.service.TypeService;
import com.yjk.vo.BlogQuery;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class indexController {

    @Autowired
    BlogService blogService;

    @Autowired
    TypeService typeService;

    @GetMapping(path = "/")
    public String index(@PageableDefault(size = 3,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("recommendBlogs",blogService.listBlogTop(8));
        return "index";
    }


    @PostMapping("/search")
    public String searchBlog(@PageableDefault(size = 3,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                             @RequestParam String query,
                             Model model){
        Page<Blog> blogsSearch = blogService.listBlog("%"+query+"%", pageable);
        model.addAttribute("page",blogsSearch);
        model.addAttribute("query",query);
        return "search";
    }

    @GetMapping(path = "/blog/{id}")
    public String blog(@PathVariable Long id,Model model){
        model.addAttribute("blog",blogService.getAndConvert(id));
        return "blog";
    }

    @GetMapping("/footer/newBlog")
    public String newBlogs(Model model){
        model.addAttribute("newBlogList",blogService.listBlogTop(3));
        return "_fragments :: newBlogList";
    }

}
