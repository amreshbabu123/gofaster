package com.quickmove.GoFaster.security;

public class LoginRequest {

    private Long mobileno;
    private String password;

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
}
