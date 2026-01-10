package com.scm.scm.entity;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "users")
@Entity(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Users{
    @Id
    @GeneratedValue(  strategy = GenerationType.UUID)
    private String userId;
    @Column( nullable = false)
    private String userName;
    @Column(unique = true , nullable = false)
    private String email;
    @Column(unique = true)
    private String phoneNumber;
    private String profilePic;
    private String password;
    private String about;
    //info
    @Builder.Default()
    private boolean enabled=true;
    @Builder.Default()
    private boolean emailVerified=false;
    @Builder.Default()
    private boolean phoneNumberVerified=false;

    //oauth2
    @Builder.Default
    @Enumerated(jakarta.persistence.EnumType.STRING)
    private Provider provider=Provider.Self;
    @Enumerated(jakarta.persistence.EnumType.ORDINAL)
    @Builder.Default
    private Provider providerId=Provider.Self;
    
    //List of contacts
    @OneToMany(mappedBy = "user",cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
    @lombok.Builder.Default()
    private List<Contact> contacts=new ArrayList<>();

}