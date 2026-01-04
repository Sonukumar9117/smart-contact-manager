package com.scm.scm.security;

import com.scm.scm.entity.Users;
import com.scm.scm.respository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPrincipleService implements UserDetailsService {
    private final UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user= userRepo.findByEmail(email);
        if(user==null)throw UsernameNotFoundException.fromUsername(email);
        return new UserPrincipal(user);
    }
}
