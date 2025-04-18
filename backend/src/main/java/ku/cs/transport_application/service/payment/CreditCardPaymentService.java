package ku.cs.transport_application.service.payment;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import ku.cs.transport_application.DTO.PaymentResponse;
import ku.cs.transport_application.entity.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("creditCardService")
public class CreditCardPaymentService implements PaymentService {

    @Value("${stripe.api.key.test}")
    private String stripeSecretKey;

    @Override
    public PaymentResponse createPaymentLink(Order order) throws StripeException {
        if (order == null || order.getTotal() <= 0) {
            throw new IllegalArgumentException("Invalid order: Order must not be null and total must be greater than zero.");
        }

        Stripe.apiKey = stripeSecretKey;


        double total = order.getTotal() * 100;

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("https://g1-472.jgogo01.in.th/payment/success?id=" + order.getId())
                .setCancelUrl("https://g1-472.jgogo01.in.th/payment/fail")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("thb")
                                .setUnitAmount((long) total)
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("Order price")
                                        .build())
                                .build())
                        .build())
                .setPaymentIntentData(
                        SessionCreateParams.PaymentIntentData.builder()
                                .putMetadata("order_id", order.getId().toString())
                                .build()
                )
                .build();


        Session session = Session.create(params);

        PaymentResponse response = new PaymentResponse();
        response.setPaymentLink(session.getUrl());

        return response;
    }
}
