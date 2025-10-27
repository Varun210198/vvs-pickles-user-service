package org.vvspickles.userservice.Controllers;

import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vvspickles.userservice.Dtos.*;
import org.vvspickles.userservice.Models.Token;
import org.vvspickles.userservice.Models.User;
import org.vvspickles.userservice.Services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        System.out.println("Testing 1");
        User user = userService.signUp(signUpRequestDto.getName(), signUpRequestDto.getEmail(), signUpRequestDto.getPassword());
        System.out.println("Testing 4  :"+user.getUsername());
        return new ResponseEntity<>(new SignUpResponseDto(user.getUsername(), user.getEmail()), HttpStatus.OK);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDto loginRequestDto) {
        return userService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
    }

    @PostMapping("/validate")
    public Boolean validate(@RequestHeader("Authorization") String token) {
        return userService.validate(token);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        userService.logout(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
