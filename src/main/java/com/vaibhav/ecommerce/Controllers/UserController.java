package com.vaibhav.ecommerce.Controllers;

import com.vaibhav.ecommerce.Service.UserService;
import com.vaibhav.ecommerce.dto.users.SignInDto;
import com.vaibhav.ecommerce.dto.users.SignInResponseDto;
import com.vaibhav.ecommerce.dto.users.SignUpResponseDto;
import com.vaibhav.ecommerce.dto.users.SignupDto;
import com.vaibhav.ecommerce.exceptions.AuthenticationFailException;
import com.vaibhav.ecommerce.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public SignUpResponseDto SignUp(@RequestBody SignupDto signupDto) throws CustomException{
        return userService.signUp(signupDto);
    }

    @PostMapping("/signin")
    public SignInResponseDto SignIn(@RequestBody SignInDto signInDto) throws CustomException, AuthenticationFailException {
        return userService.signIn(signInDto);
    }
}
