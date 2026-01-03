package com.scm.scm.form;

import org.springframework.format.annotation.NumberFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UserForm {
    @NotBlank(message="Name is mandatory")
    @Size(min=3, message="Name must be at least 3 characters long")
    private String name;
    @NotBlank(message="Email is mandatory")
    @Email(message="Email should be valid")
    private String email;
    @NotBlank(message="Password is mandatory")
    @jakarta.validation.constraints.Size(min=8, message="Password must be at least 8 characters long")
    private String password;
    @NotBlank(message="Phone number is mandatory")
    @Size(min=10, max=15, message="Phone number must be between 10 and 15 characters")
    @NumberFormat(pattern="Phone number must contain only digits")
    private String phone;
}
