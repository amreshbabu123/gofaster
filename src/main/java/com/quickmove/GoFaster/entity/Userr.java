package com.quickmove.GoFaster.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class Userr {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mobileno", unique = true, nullable = false)
    private Long mobileno;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role; // USER or DRIVER

    // ✅ Constructors
    public Userr() {}

    public Userr(Long mobileno, String password, String role) {
        this.mobileno = mobileno;
        this.password = password;
        this.role = role;
    }

    // ✅ Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMobileno() {
        return mobileno;
    }

    public void setMobileno(Long mobileno) {
        this.mobileno = mobileno;
    }

    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }

    // ✅ THIS IS IMPORTANT FOR SPRING SECURITY
    public String getUsername() {
        return String.valueOf(this.mobileno);
    }
}
