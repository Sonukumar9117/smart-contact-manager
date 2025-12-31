package com.scm.scm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Home {
    
    @RequestMapping("/home")
    public String home(Model model){
        model.addAttribute("title", "Smart Contact Manager");
        model.addAttribute("message", "Welcome to Smart Contact Manager - Manage your contacts efficiently!");
        model.addAttribute("name", "Sonu Kumar");
        model.addAttribute("linkedin","https://www.linkedin.com/in/sonu-kumar-81284b230/");
        return "home";
    }
    @RequestMapping("/about")
    public String about(Model model){
        model.addAttribute("title", "About Smart Contact Manager");
        return "about";
    }
    @RequestMapping("/service")
    public String services(Model model){
        model.addAttribute("title", "Services");
        model.addAttribute("isLogin",true);
        return "service";
    }
}
