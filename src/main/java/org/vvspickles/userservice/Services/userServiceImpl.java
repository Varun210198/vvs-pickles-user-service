package org.vvspickles.userservice.Services;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.vvspickles.userservice.Exceptions.InvalidRequestException;
import org.vvspickles.userservice.Exceptions.InvalidUserNameOrPassword;
import org.vvspickles.userservice.Exceptions.UserAlreadyExistsException;
import org.vvspickles.userservice.Exceptions.UserNotFoundException;
import org.vvspickles.userservice.Models.Role;
import org.vvspickles.userservice.Models.Token;
import org.vvspickles.userservice.Models.User;
import org.vvspickles.userservice.Repositories.TokenRepository;
import org.vvspickles.userservice.Repositories.UserRepository;
import org.vvspickles.userservice.Security.JwtUtil;

import java.util.*;

@Service
public class userServiceImpl implements UserService {
    UserRepository userRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;
    TokenRepository tokenRepository;
    JwtUtil jwtUtil;

    public userServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           TokenRepository tokenRepository,
                           JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public User signUp(String name, String email, String password) throws UserAlreadyExistsException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(!userOptional.isEmpty()) {
            throw new UserAlreadyExistsException("User with email " + email + " already exists");
        }
        User user = new User();
        user.setEmail(email);
        user.setUsername(name);
        user.setHashedPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(user);
        return user;
    }

    @Override
    public String login(String email, String password)  {

        // this allows multiple logins from multiple devices
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()) {
            throw new UserNotFoundException("User with email :"+email+" not found...!");
        }
        User user = userOptional.get();
        if(bCryptPasswordEncoder
                .matches(password, user.getHashedPassword())) {

            // Creating token manually
            // Token token = createToken(user);

            // Creating JWT token
            return createJwtToken(user);

            //tokenRepository.save(token);
            //return token;

        }
        throw new InvalidUserNameOrPassword("Invalid username or password");
    }

    @Override
    public Boolean validate(String token) {
        //Optional<Token> optionalToken = tokenRepository.findByValueAndDeletedAtAndExpiryAtGreaterThan(token, false, new Date());
//
//        if(optionalToken.isEmpty()) {
//            throw new InvalidRequestException("Your access token is invalid/expired, please login again.");
//        }
//        return optionalToken.get().getUser();
        return jwtUtil.validateToken(token);
    }

    @Override
    public void logout(String token) {
        // todo check if token already exists and not deleted
        Optional<Token> optionalToken = tokenRepository.findByValueAndDeletedAt(token, false);
        // todo if exists update it as deleted
        if(optionalToken.isEmpty()) {
            throw new InvalidRequestException("Your access token is invalid/expired, please login again.");
        }
        Token token1 = optionalToken.get();
        token1.setDeletedAt(true);
        tokenRepository.save(token1);
    }

    private String createJwtToken(User user){
        String userName = user.getUsername();
        List<String> roles = new ArrayList<>();
        for(Role role : user.getRoles()){
            roles.add(role.getName());
        }
        String token =  jwtUtil.generateToken(userName, roles);
        return token;
    }

    private Token createToken(User user){
        Token token = new Token();
        token.setUser(user);
        token.setValue(RandomStringUtils.randomAlphanumeric(123));

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 30);
        Date dayAfter30Days = calendar.getTime();
        token.setExpiryAt(dayAfter30Days);
        token.setDeletedAt(false);

        return token;
    }


}
