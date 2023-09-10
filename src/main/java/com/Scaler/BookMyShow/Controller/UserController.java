package com.Scaler.BookMyShow.Controller;

import com.Scaler.BookMyShow.DTOs.UserSignUpDTO;
import com.Scaler.BookMyShow.DTOs.UserSignUpResponseDTO;
import com.Scaler.BookMyShow.Models.User;
import com.Scaler.BookMyShow.Service.UserService;
import com.Scaler.BookMyShow.utils.UserControllerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    @Autowired
    public UserService userService; //interface

    public UserSignUpResponseDTO signUp(UserSignUpDTO userSignUpDTO){
        User user;
        UserSignUpResponseDTO responseDTO = new UserSignUpResponseDTO();
        try{
            UserControllerUtil.validateUserSignUpRequestDTO(userSignUpDTO);
            user = userService.signup(userSignUpDTO.getName(), userSignUpDTO.getEmail(), userSignUpDTO.getPassword());
            responseDTO.setId(user.getId());
            responseDTO.setName(user.getName());
            responseDTO.setEmail(user.getEmail());
            responseDTO.setTickets(user.getTickets());
            responseDTO.setResponseCode(200);
            responseDTO.setResponseMessage("Success");
            return responseDTO;
        }catch (Exception e) {
            responseDTO.setResponseCode(500);
            responseDTO.setResponseMessage("Internal Server Error");
            return responseDTO;
        }
    }
}
