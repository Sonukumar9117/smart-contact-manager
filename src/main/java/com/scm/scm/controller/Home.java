package com.scm.scm.controller;


import com.scm.scm.entity.Users;
import com.scm.scm.security.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
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
    public String home(Model model, HttpServletRequest request, HttpServletResponse response, Authentication authentication){
//        String providerString="self";
//        System.out.println(request+"============Response=============="+authentication.getPrincipal());
//        if(authentication instanceof OAuth2AuthenticationToken oAuth2AuthenticationToken){
//            providerString= oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
//            DefaultOAuth2User user=(DefaultOAuth2User) oAuth2AuthenticationToken.getPrincipal();
//            if(Objects.equals(providerString, "google")){
//                assert user != null;
//                var userObject=user.getAttributes();
//                    model.addAttribute("name",userObject.get("name").toString());
//                    model.addAttribute("email",userObject.get("email").toString());
//                    model.addAttribute("profilePic",userObject.get("picture").toString());
//                }
//            else if(Objects.equals(providerString,"github")) {
//                assert user != null;
//                var userObject=user.getAttributes();
//                    model.addAttribute("name",userObject.get("name").toString());
//                    model.addAttribute("email",userObject.get("login").toString()+"@github.com");
//                    model.addAttribute("profilePic",userObject.get("avatar_url").toString());
//            }
//        }
//        else if(authentication instanceof UsernamePasswordAuthenticationToken){
//            var user=authentication.getPrincipal();
//            assert user != null;
//            System.out.println("Login Via Self" + user.toString());
//        }
        UserPrincipal userPrincipal=(UserPrincipal) authentication.getPrincipal();
        assert userPrincipal != null;
        Users user=userPrincipal.getUser();
        model.addAttribute("title", "Smart Contact Manager");
        model.addAttribute("name", user.getUserName());
        model.addAttribute("email",user.getEmail());
        model.addAttribute("profilePic",user.getProfilePic());
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
        if(bindingResult.hasErrors()){
            model.addAttribute("showNavbar", false);
            return "register";
        }
        userService.registerUser(userForm);
        return "redirect:/login";
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
