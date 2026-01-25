package com.scm.scm.config;

import com.nimbusds.jose.proc.SecurityContext;
import com.scm.scm.entity.Provider;
import com.scm.scm.entity.Users;
import com.scm.scm.helper.PhoneNumberGenerator;
import com.scm.scm.respository.UserRepo;
import com.scm.scm.security.UserPrincipal;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
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
        if(authentication instanceof OAuth2AuthenticationToken oAuth2AuthenticationToken){
            String providerString= oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
            DefaultOAuth2User user=(DefaultOAuth2User) oAuth2AuthenticationToken.getPrincipal();
            System.out.println(providerString+"Login via "+user);
            Users newUser=new Users();
            if(Objects.equals(providerString, "google")){
                assert user != null;
                var userObject=user.getAttributes();
                newUser.setEmail(userObject.get("email").toString());
                if(userRepo.findByEmail(userObject.get("email").toString())==null){
                    newUser.setUserName(userObject.get("name").toString());
                    newUser.setEmail(userObject.get("email").toString());
                    newUser.setProfilePic(userObject.get("picture").toString());
                    newUser.setProvider(Provider.Google);
                    newUser.setEmailVerified((boolean)userObject.get("email_verified"));
                    newUser.setPassword(String.valueOf(new UUID(10,10)));
                    while(true){
                        String phoneNumber= PhoneNumberGenerator.generateUnique10DigitNumber();
                        if(userRepo.findByPhoneNumber(phoneNumber)==null){
                            newUser.setPhoneNumber(phoneNumber);
                            break;
                        }
                    }
                    userRepo.save(newUser);
                }
            }
            else if(Objects.equals(providerString,"github")) {
                assert user != null;

                var userObject=user.getAttributes();
                newUser.setEmail(userObject.get("login").toString()+"@github.com");
                System.out.println(userObject+"User Object login via Github");
                if(userRepo.findByEmail(userObject.get("login").toString()+"@github.com")==null){
                    System.out.println(userObject.get("login"));
                    System.out.println(userObject.get("name"));
                    newUser.setUserName(userObject.get("name").toString());
                    newUser.setEmail(userObject.get("login").toString()+"@github.com");
                    newUser.setProfilePic(userObject.get("avatar_url").toString());
                    newUser.setProvider(Provider.GitHub);
                    newUser.setEmailVerified(true);
                    newUser.setPassword(String.valueOf(new UUID(10,10)));
                    while(true){
                        String phoneNumber= PhoneNumberGenerator.generateUnique10DigitNumber();
                        if(userRepo.findByPhoneNumber(phoneNumber)==null){
                            newUser.setPhoneNumber(phoneNumber);
                            break;
                        }
                    }
                    userRepo.save(newUser);
                }

            }
            UserPrincipal userPrincipal=new UserPrincipal(userRepo.findByEmail(newUser.getEmail()));
            Authentication newAuth=new UsernamePasswordAuthenticationToken(
                    userPrincipal,
                    null,
                    userPrincipal.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(newAuth);
        }
        else if(authentication instanceof UsernamePasswordAuthenticationToken){
            var user=authentication.getPrincipal();
            assert user != null;
            System.out.println("Login Via Self" + user.toString());
        }
         new DefaultRedirectStrategy().sendRedirect(request,response,"/home");
    }
}
