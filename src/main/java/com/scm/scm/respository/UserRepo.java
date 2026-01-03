package com.scm.scm.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scm.scm.entity.Users;

public interface UserRepo extends JpaRepository<Users,String>{
    
}
