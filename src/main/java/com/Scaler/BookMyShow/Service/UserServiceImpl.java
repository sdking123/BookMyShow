package com.Scaler.BookMyShow.Service;

import com.Scaler.BookMyShow.Exception.InvalidCredentialException;
import com.Scaler.BookMyShow.Exception.UserExistException;
import com.Scaler.BookMyShow.Exception.UserNotFound;
import com.Scaler.BookMyShow.Models.User;
import com.Scaler.BookMyShow.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class UserServiceImpl implements UserService{
    @Autowired

    private UserRepository userRepository;
    @Override
    public User login(String email, String password){
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            throw new UserNotFound("User with given Email does not exist" + email);
        }
        User user = userOptional.get();
        if(user.getPassword().equals(password)){
            return user;
        }else{
            throw new InvalidCredentialException("Invalid Credential provided");
        }
    }

    public User signup(String name, String email, String password){
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isPresent()){
            throw new UserExistException("User already exist");
        }

        User newUser = new User();
        newUser.setPassword(password);
        newUser.setName(name);
        newUser.setEmail(email);

        return userRepository.save(newUser);

    }
}
