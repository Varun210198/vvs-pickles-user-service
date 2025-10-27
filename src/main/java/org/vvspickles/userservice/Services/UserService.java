package org.vvspickles.userservice.Services;

import org.vvspickles.userservice.Models.Token;
import org.vvspickles.userservice.Models.User;


public interface UserService {
     public User signUp(String name, String email, String password);
     public String login(String email, String password);
     public Boolean validate(String token);
     public void logout(String token);
}
