package com.example.gimnasioBack.backEndGimnasioF.DTO;

public class LoginRequest {
    private String email;
    private String passwd;

    public LoginRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
