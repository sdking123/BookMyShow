package com.Scaler.BookMyShow.Service;

import com.Scaler.BookMyShow.Models.User;
import org.springframework.beans.factory.annotation.Autowired;

public interface UserService {

     User login(String email, String password);

    User signup(String name, String email, String password);

}
