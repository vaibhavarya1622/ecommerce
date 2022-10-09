package com.vaibhav.ecommerce.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.vaibhav.ecommerce.dto.CheckoutItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderService {
    @Autowired
    private CartService cartService;

    @Value("${BASE_URL}")
    private String BaseURL;

    @Value("${STRIPE_SECRET_KEY}")
    private String apiKey;

    //create total price and send product name as input
    SessionCreateParams.LineItem.PriceData createPriceData(CheckoutItemDto checkoutItemDto){
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("usd")
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
}
