package com.scm.scm.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.scm.form.UserForm;
import com.scm.scm.services.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class Home {
    private final  UserService userService;
    @RequestMapping("/home")
    public String home(Model model){
        model.addAttribute("title", "Smart Contact Manager");
        
        model.addAttribute("name", "Sonu Kumar");
        model.addAttribute("linkedin","https://www.linkedin.com/in/sonu-kumar-81284b230/");
        return "home";
    }
    @RequestMapping("/about")
    public String about(Model model){
        model.addAttribute("title", "About Smart Contact Manager");
        return "about";
    }
    
    @RequestMapping("/")
    public String index(Model model){
        return "redirect:/home";
    }
    
    @RequestMapping("service")
    public String services(Model model){
        model.addAttribute("title", "Services");
        model.addAttribute("isLogin",true);
        return "service";
    }

    @RequestMapping(value="/forrm-register",method=RequestMethod.POST)
    public String formRegister(@Valid @ModelAttribute UserForm userForm,BindingResult bindingResult , Model model){
        System.out.println(userForm);
        if(bindingResult.hasErrors()){
            System.out.println("Errors :"+bindingResult.toString());
            model.addAttribute("showNavbar", false);
        
            return "register";
        }
        userService.registerUser(userForm);
        
        return "redirect:/register";
    }
    
    @RequestMapping("/register")
    public String register(Model model){
        model.addAttribute("userForm", new UserForm("","","",""));
        model.addAttribute("showNavbar", false);
        model.addAttribute("title", "Register - Smart Contact Manager");
        return "register";
    }
    @RequestMapping("/login")
    public String login(Model model){
        model.addAttribute("message", "Welcome to Smart Contact Manager - Manage your contacts efficiently!");
        model.addAttribute("showNavbar", false);
        model.addAttribute("title", "Login - Smart Contact Manager");   
        return "login";
    }
}
