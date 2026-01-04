package com.scm.scm.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scm.scm.entity.Users;
import com.scm.scm.form.UserForm;
import com.scm.scm.respository.UserRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    final UserRepo userRepo;

    @Override
    public void registerUser(UserForm userForm) {
        Users user=Users.builder().
                userName(userForm.getName())
                .email(userForm.getEmail())
                .phoneNumber(userForm.getPhone())
                .password(new BCryptPasswordEncoder(12).encode(userForm.getPassword()))
                .build();
        var d=userRepo.save(user);
        System.out.println(d.toString());
    }
    
}
