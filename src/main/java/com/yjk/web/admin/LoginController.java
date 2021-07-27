package com.yjk.web.admin;


import com.yjk.pojo.User;
import com.yjk.service.UserService;
import com.yjk.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(path = "/admin")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String loginPage(){

        return "admin/login";
    }

    @PostMapping(path = "/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes redirectAttributes,
                        ModelAndView modelAndView){

        User user = userService.checkUser(username, password);
        if (user != null){
            user.setPassword("null");
            session.setAttribute("user",user);
            return "admin/index";
        }else {
            modelAndView.addObject("message","用户名或密码错误");

            return "redirect:/admin";
        }
    }

    @GetMapping(path = "/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }

}
