package com.as.spring.asmenu.model;


import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    private String firstName;

    private String lastName;

    private String email;






    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
