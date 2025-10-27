package org.vvspickles.userservice.Dtos;

import org.vvspickles.userservice.Models.Role;
import org.vvspickles.userservice.Models.Token;

import java.util.List;

public class LoginResponseDto {
    private String token;
    private String username;
    private String email;
    private List<Role> roles;

    public LoginResponseDto fromToken(Token token) {
        this.token = token.getValue();
        this.username = token.getUser().getUsername();
        this.email = token.getUser().getEmail();
        // todo work on sending roles later
        //this.roles = token.getUser().getRoles
        return this;
    }
    public String getToken() {
        return token;
    }
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
}
