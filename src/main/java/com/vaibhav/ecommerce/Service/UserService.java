package com.vaibhav.ecommerce.Service;

import com.vaibhav.ecommerce.Model.AuthenticationToken;
import com.vaibhav.ecommerce.Model.User;
import com.vaibhav.ecommerce.Repository.UserRepository;
import com.vaibhav.ecommerce.config.MessageStrings;
import com.vaibhav.ecommerce.dto.users.SignInDto;
import com.vaibhav.ecommerce.dto.users.SignInResponseDto;
import com.vaibhav.ecommerce.dto.users.SignUpResponseDto;
import com.vaibhav.ecommerce.dto.users.SignupDto;
import com.vaibhav.ecommerce.exceptions.AuthenticationFailException;
import com.vaibhav.ecommerce.exceptions.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;


@Service
public class UserService{
    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationService authenticationService;

    Logger logger = LoggerFactory.getLogger(UserService.class);

    public SignUpResponseDto signUp(SignupDto signupDto) throws CustomException {
        //Checks to see if current email address is already registered
        if(Objects.nonNull(userRepository.findByEmail(signupDto.getEmail()))){
            throw new CustomException("User already Exists");
        }
        //first Encrypt the password;
        String encryptedPassword = signupDto.getPassword();
        Logger logger = LoggerFactory.getLogger(UserService.class);
        try{
            encryptedPassword = hashPassword(signupDto.getPassword());
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            logger.error("hashing password failed {}",e.getMessage());
        }

        User user = new User(signupDto.getFirstName(),signupDto.getLastName(),signupDto.getEmail(),encryptedPassword);
        try{
            //save the user
            userRepository.save(user);

            //success in creating
            //generate token for user
            final AuthenticationToken authenticationToken = new AuthenticationToken(user);
            authenticationService.saveConfirmationToken(authenticationToken);
            return new SignUpResponseDto("success","user created successfully!!");
        }
        catch (Exception e){
            //handle signup error
            throw new CustomException(e.getMessage());
        }
    }

    public SignInResponseDto signIn(SignInDto signInDto) throws AuthenticationFailException, CustomException{
        // first find User by email
        User user = userRepository.findByEmail(signInDto.getEmail());
        if(!Objects.nonNull(user)){
            throw new AuthenticationFailException("user not present");
        }
        try {
            // check if password is right
            if (!user.getPassword().equals(hashPassword(signInDto.getPassword()))){
                // passwords do not match
                throw  new AuthenticationFailException(MessageStrings.WRONG_PASSWORD);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            logger.error("hashing password failed {}", e.getMessage());
            throw new CustomException(e.getMessage());
        }

        AuthenticationToken token = authenticationService.getToken(user);

        if(!Objects.nonNull(token)) {
            // token not present
            throw new CustomException(MessageStrings.AUTH_TOKEN_NOT_PRESENT);
        }

        return new SignInResponseDto ("success", token.getToken());
    }

    String hashPassword(String password) throws NoSuchAlgorithmException{
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
        return myHash;
    }
}
