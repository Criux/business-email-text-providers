package com.kmarinos.businessemaildemo.mail.offerProviders;

import com.kmarinos.businessemaildemo.OfferContext;
import com.kmarinos.businessemaildemo.mail.OfferKfz;
import com.kmarinos.businessemaildemo.mail.OfferTextProvider;
import com.kmarinos.businessemaildemo.mail.offerProviders.mailCtx.OfferKfzMailContext;

import java.time.LocalDate;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OfferKfzTextProvider extends OfferTextProvider<OfferContext, OfferKfzMailContext> {

    @Override
    public Predicate<OfferContext> getCondition() {
        return OfferContext::isKfz;
    }

    public OfferKfzTextProvider(OfferContext offerContext) {
        super(offerContext);
    }

    @Override
    public String header(OfferKfzMailContext mailContext) {
        return "header for " + mailContext.getOfferKfz().offerNumber();
    }

    @Override
    public String mainInfo(OfferKfzMailContext mailContext) {
        return "mainInfo KFZ";
    }

    @Override
    public String secondaryInfo(OfferKfzMailContext mailContext) {
        return defer();
    }

    @Override
    public String tertiaryInfo(OfferKfzMailContext mailContext) {
        return "tertiaryInfo kfz";
    }

    @Override
    public Supplier<OfferKfzMailContext> getTextContext() {
        return () -> new OfferKfzMailContext(new OfferKfz("offerNumber kfz", LocalDate.now()));
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
