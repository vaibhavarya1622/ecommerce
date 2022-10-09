package com.vaibhav.ecommerce.Service;

import com.vaibhav.ecommerce.Model.AuthenticationToken;
import com.vaibhav.ecommerce.Model.User;
import com.vaibhav.ecommerce.Repository.TokenRepository;
import com.vaibhav.ecommerce.config.MessageStrings;
import com.vaibhav.ecommerce.exceptions.AuthenticationFailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticationService {
    @Autowired
    TokenRepository repository;

    //save the confirmation token
    public void saveConfirmationToken(AuthenticationToken authenticationToken){
        repository.save(authenticationToken);
    }

    //get token of the User
    public AuthenticationToken getToken(User user){
        return repository.findTokenByUser(user);
    }

    //get User from the token
    public User getUser(String token){
        AuthenticationToken authenticationToken = repository.findTokenByToken(token);
        if(Objects.nonNull(authenticationToken)){
            if(Objects.nonNull(authenticationToken.getUser())){
                return authenticationToken.getUser();
            }
        }
        return null;
    }

    //check if the token is valid
    public void authenticate(String token) throws AuthenticationFailException{
        if(!Objects.nonNull(token)){
            throw new AuthenticationFailException(MessageStrings.AUTH_TOKEN_NOT_PRESENT);
        }
        if(!Objects.nonNull(getUser(token))){
            throw new AuthenticationFailException(MessageStrings.AUTH_TOKEN_NOT_VALID);
        }
    }
}
