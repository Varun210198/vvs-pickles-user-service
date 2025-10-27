package org.vvspickles.userservice.Dtos;

import org.vvspickles.userservice.Models.Role;
import org.vvspickles.userservice.Models.User;

import java.util.List;

public class UserDto {
    private String username;
    private String email;

    public UserDto fromUser(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        return this;
    }

    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }

}
