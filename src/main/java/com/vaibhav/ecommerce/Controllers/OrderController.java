package com.vaibhav.ecommerce.Controllers;

import com.vaibhav.ecommerce.Service.OrderService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.vaibhav.ecommerce.dto.CheckoutItemDto;
import com.vaibhav.ecommerce.dto.StripeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    //stripe create session API
    @PostMapping("/create-checkout-session")
    public ResponseEntity<StripeResponse> checkoutList(@RequestBody List<CheckoutItemDto> checkoutItemDtoList) throws StripeException{
        //create the stripe session
        Session session = orderService.createSession(checkoutItemDtoList);
        StripeResponse stripeResponse = new StripeResponse(session.getId());
        //send the stripe id in response
        return new ResponseEntity<StripeResponse>(stripeResponse, HttpStatus.OK);
    }
}
