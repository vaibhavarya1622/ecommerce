package com.vaibhav.ecommerce.Repository;

import com.vaibhav.ecommerce.Model.AuthenticationToken;
import com.vaibhav.ecommerce.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<AuthenticationToken,Integer> {
    AuthenticationToken findTokenByUser(User user);
    AuthenticationToken findTokenByToken(String token);
}
