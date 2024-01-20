package com.kmarinos.businessemaildemo.mail.demo.offer.fallback;

import com.kmarinos.businessemaildemo.mail.demo.offer.OfferContext;
import com.kmarinos.businessemaildemo.mail.business.DefaultMailContext;
import com.kmarinos.businessemaildemo.mail.demo.offer.OfferTextProvider;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class OfferFallbackTextProvider extends OfferTextProvider<OfferContext, DefaultMailContext> {

    @Override
    public Predicate<OfferContext> getCondition() {
        return c -> true;
    }

    public OfferFallbackTextProvider(OfferContext offerContext) {
        super(offerContext);
    }

    @Override
    public String header(DefaultMailContext mailContext) {
        return "fallback header";
    }

    @Override
    public String mainInfo(DefaultMailContext mailContext) {
        return "fallback mainInfo";
    }

    @Override
    public String secondaryInfo(DefaultMailContext mailContext) {
        return "fallback secondaryInfo";
    }

    @Override
    public String tertiaryInfo(DefaultMailContext mailContext) {
        return "fallback tertiaryInfo";
    }

    @Override
    public Supplier<DefaultMailContext> getTextContext() {
        return DefaultMailContext::new;
    }


//    Class<EmailTemplate> emailTemplateClass;
//    @Override
//    public String mainInfo(OfferKfzMailContext context){
//        return "Please find your offer attached above.";
//    }
//    @Override
//    public String header(OfferKfzMailContext context){
//        return defer();
//    }

}
