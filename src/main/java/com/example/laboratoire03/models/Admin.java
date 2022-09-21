package com.example.laboratoire03.models;

import lombok.*;

import javax.validation.constraints.Pattern;

@Getter
@Setter
public class Admin {

    @Pattern(regexp = "admin", message = "Le nom d'utilisateur et invalid")
    private String username;
    @Pattern(regexp = "password", message = "Le mot de passe et invalid")
    private String password;
    public Admin() {}
    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
