package com.kmarinos.businessemaildemo;

import com.kmarinos.businessemaildemo.mail.demo.offer.OfferContext;
import com.kmarinos.businessemaildemo.core.EmailTextBuilder;
import com.kmarinos.businessemaildemo.mail.demo.offer.fallback.OfferFallbackTextProvider;
import com.kmarinos.businessemaildemo.mail.demo.offer.kfz.OfferKfzTextProvider;
import com.kmarinos.businessemaildemo.mail.demo.offer.kfzAndKps.OfferKfzAndKpsTextProvider;
import com.kmarinos.businessemaildemo.mail.demo.offer.kps.OfferKpsTextProvider;
import com.kmarinos.businessemaildemo.mail.demo.offer.simple.SimpleTextProvider;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BusinessEmailDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusinessEmailDemoApplication.class, args);
    }

    public EmailTextBuilder<OfferContext> registerTextProviders(OfferContext context) {

        var emailText = new EmailTextBuilder<OfferContext>();

        emailText.registerTextProvider(100, () -> new OfferKfzTextProvider(context))
                .registerTextProvider(200, () -> new OfferKpsTextProvider(context))
                .registerTextProvider(50, () -> new OfferKfzAndKpsTextProvider(context))
                .registerTextProvider(Integer.MAX_VALUE, () -> new SimpleTextProvider(context))
                .registerFallbackTextProvider(() -> new OfferFallbackTextProvider(context));

        return emailText;
    }

    @Bean
    ApplicationRunner runMeHighOverview() {
        return args -> {
            var context = new OfferContext();
            var emailText = registerTextProviders(context);
            context.setKfz(true);
            context.setKps(true);

            System.out.println(emailText.getText());
            System.out.println("=======================");
            context.setKfz(false);
            context.setKps(true);

            System.out.println(emailText.getText());
        };
    }

}
