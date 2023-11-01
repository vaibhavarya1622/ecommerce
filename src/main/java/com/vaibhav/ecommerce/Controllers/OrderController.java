package com.vaibhav.ecommerce.Controllers;

import com.vaibhav.ecommerce.Model.Order;
import com.vaibhav.ecommerce.Model.User;
import com.vaibhav.ecommerce.Service.AuthenticationService;
import com.vaibhav.ecommerce.Service.OrderService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.vaibhav.ecommerce.config.ApiResponse;
import com.vaibhav.ecommerce.dto.CheckoutItemDto;
import com.vaibhav.ecommerce.dto.StripeResponse;
import com.vaibhav.ecommerce.exceptions.AuthenticationFailException;
import com.vaibhav.ecommerce.exceptions.OrderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    AuthenticationService authenticationService;

    //stripe create session API
    @PostMapping("/create-checkout-session")
    public ResponseEntity<StripeResponse> checkoutList(@RequestBody List<CheckoutItemDto> checkoutItemDtoList) throws StripeException{
        //create the stripe session
        Session session = orderService.createSession(checkoutItemDtoList);
        StripeResponse stripeResponse = new StripeResponse(session.getId());
        //send the stripe id in response
        return new ResponseEntity<StripeResponse>(stripeResponse, HttpStatus.OK);
    }
    //place order after checkout
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> placeOrder(@RequestParam("token") String token, @RequestParam("sessionId") String sessionId)
    throws AuthenticationFailException {
        //validate token
        authenticationService.authenticate(token);
        //retrieve User
        User user = authenticationService.getUser(token);
        //place the order
        orderService.placeOrder(user,sessionId);
        return new ResponseEntity<>(new ApiResponse(true,"Order has been placed"),HttpStatus.CREATED);
    }
    @GetMapping("/")
    public ResponseEntity<List<Order>> getAllOrders(@RequestParam("token") String token) throws AuthenticationFailException{
        //validate token
        authenticationService.authenticate(token);
        //retrieve user
        User user = authenticationService.getUser(token);
        //get orders
        List<Order> orderDtoList = orderService.listOrders(user);
        return new ResponseEntity<>(orderDtoList,HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOrderById(@PathVariable("id") Integer id, @RequestParam("token") String token)
    throws AuthenticationFailException, OrderNotFoundException {
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);
        Order order = orderService.getOrder(id,user);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
