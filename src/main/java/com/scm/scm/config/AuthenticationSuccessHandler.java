package com.scm.scm.config;

import com.scm.scm.entity.Provider;
import com.scm.scm.entity.Users;
import com.scm.scm.helper.PhoneNumberGenerator;
import com.scm.scm.respository.UserRepo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationSuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {
    private final UserRepo userRepo;
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        DefaultOAuth2User user=(DefaultOAuth2User)authentication.getPrincipal();
        assert user != null;
        String email= Objects.requireNonNull(user.getAttribute("email")).toString();
        String name=Objects.requireNonNull(user.getAttribute("name")).toString();
        String pic=Objects.requireNonNull(user.getAttribute("picture")).toString();
        //Create USer and save to database
        Users users=new Users();
        users.setEmail(email);
        users.setUserName(name);
        users.setProfilePic(pic);
        users.setPassword(new UUID(2,4).toString());
        users.setProvider(Provider.Google);

        while(true){
            String phoneNumber= PhoneNumberGenerator.generateUnique10DigitNumber();
            if(userRepo.findByPhoneNumber(phoneNumber)==null){
                users.setPhoneNumber(phoneNumber);
                break;
            }
        }
        if(userRepo.findByEmail(users.getEmail())==null){
            userRepo.save(users);
        }
        System.out.println(user.getAttributes().toString()+"===========");

         new DefaultRedirectStrategy().sendRedirect(request,response,"/home");
    }
}
