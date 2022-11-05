package com.vaibhav.ecommerce.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.vaibhav.ecommerce.Model.Order;
import com.vaibhav.ecommerce.Model.OrderItem;
import com.vaibhav.ecommerce.Model.User;
import com.vaibhav.ecommerce.Repository.OrderItemRepository;
import com.vaibhav.ecommerce.Repository.OrderRepository;
import com.vaibhav.ecommerce.dto.CartDto;
import com.vaibhav.ecommerce.dto.CartItemDto;
import com.vaibhav.ecommerce.dto.CheckoutItemDto;
import com.vaibhav.ecommerce.exceptions.OrderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {
    @Autowired
    private CartService cartService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Value("${BASE_URL}")
    private String BaseURL;

    @Value("${STRIPE_SECRET_KEY}")
    private String apiKey;

    //create total price and send product name as input
    SessionCreateParams.LineItem.PriceData createPriceData(CheckoutItemDto checkoutItemDto){
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("inr")
                .setUnitAmount(((long)checkoutItemDto.getPrice())*100)
                .setProductData(
                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                .setName(checkoutItemDto.getProductName())
                                .build())
                .build();
    }
    //build each product in the stripe checkout page
    SessionCreateParams.LineItem createSessionLineItem(CheckoutItemDto checkoutItemDto){
        return SessionCreateParams.LineItem.builder()
                //set price for each product
                .setPriceData(createPriceData(checkoutItemDto))
                //set quantity for each product
                .setQuantity(Long.parseLong(String.valueOf(checkoutItemDto.getQuantity())))
                .build();
    }
    //create session from list of checkout items
    public Session createSession(List<CheckoutItemDto> checkoutItemDtoList) throws StripeException{
        //supply success and failure url for stripe
        String successURL = BaseURL + "payment/success";
        String failedURL = BaseURL + "payment/failed";

        //set the private key
        Stripe.apiKey = apiKey;

        List<SessionCreateParams.LineItem> sessionItemsList = new ArrayList<>();

        //for each product compute SessionCreateParams.LineItem
        for(CheckoutItemDto checkoutItemDto:checkoutItemDtoList){
            sessionItemsList.add(createSessionLineItem(checkoutItemDto));
        }

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setCancelUrl(failedURL)
                .addAllLineItem(sessionItemsList)
                .setSuccessUrl(successURL)
                .build();
        return Session.create(params);
    }
    public void placeOrder(User user, String sessionId){
        //first let get cart items for the user
        CartDto cartDto = cartService.listCartDtoItems(user);
        List<CartItemDto> cartItemDtoList = cartDto.getCartItems();

        //create the order and save it
        Order newOrder = new Order();
        newOrder.setCreatedDate(new Date());
        newOrder.setSessionId(sessionId);
        newOrder.setUser(user);
        newOrder.setTotalPrice(cartDto.getTotalCost());
        orderRepository.save(newOrder);

        for(CartItemDto cartItemDto:cartItemDtoList){
            //create orderItem and save each one
            OrderItem orderItem = new OrderItem();
            orderItem.setCreatedDate(new Date());
            orderItem.setPrice(cartItemDto.getProduct().getPrice());
            orderItem.setProduct(cartItemDto.getProduct());
            orderItem.setQuantity(cartItemDto.getQuantity());
            orderItem.setOrder(newOrder);
            //add to order item list
            orderItemRepository.save(orderItem);
        }
    }
    public List<Order> listOrders(User user){
        return orderRepository.findAllByUserOrderByCreatedDateDesc(user);
    }

    public Order getOrder(Integer orderId, User user) throws OrderNotFoundException {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if(optionalOrder.isEmpty()){
            throw new OrderNotFoundException("Order id is not valid");
        }
        Order order = optionalOrder.get();
        if(order.getUser() != user){
            throw new OrderNotFoundException("Order does not belong to the user");
        }
        return order;
    }
}
