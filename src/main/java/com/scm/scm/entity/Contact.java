package com.scm.scm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "contacts")
@Table(name = "contacts")
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String contactId; 
    @Column(nullable = false)
    private String name;
    @Column(nullable = false,unique = true)
    private String phoneNumber;
    private String profilePic;
    @ManyToOne
    private Users user;
}
