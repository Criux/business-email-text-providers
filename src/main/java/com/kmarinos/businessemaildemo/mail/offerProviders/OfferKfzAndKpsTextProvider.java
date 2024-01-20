package com.kmarinos.businessemaildemo.mail.offerProviders;

import com.kmarinos.businessemaildemo.OfferContext;
import com.kmarinos.businessemaildemo.mail.OfferKfz;
import com.kmarinos.businessemaildemo.mail.OfferKps;
import com.kmarinos.businessemaildemo.mail.OfferTextProvider;
import com.kmarinos.businessemaildemo.mail.offerProviders.mailCtx.OfferKfzAndKpsMailContext;

import java.time.LocalDate;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OfferKfzAndKpsTextProvider extends OfferTextProvider<OfferContext, OfferKfzAndKpsMailContext> {

    @Override
    public Predicate<OfferContext> getCondition() {
        return AND(OfferContext::isKps, OfferContext::isKfz);
    }

    public OfferKfzAndKpsTextProvider(OfferContext offerContext) {
        super(offerContext);
    }

    @Override
    public String header(OfferKfzAndKpsMailContext mailContext) {
        return "header for " + mailContext.getOfferKfz().offerNumber() + " and " + mailContext.getOfferKps().offerNumber() + " with discount " + mailContext.getOfferKps().discount();
    }

    @Override
    public String mainInfo(OfferKfzAndKpsMailContext mailContext) {
        return "mainInfo KFZ && KPS";
    }

    @Override
    public String secondaryInfo(OfferKfzAndKpsMailContext mailContext) {
        return defer();
    }

    @Override
    public String tertiaryInfo(OfferKfzAndKpsMailContext mailContext) {
        return defer();
    }

    @Override
    public Supplier<OfferKfzAndKpsMailContext> getTextContext() {
        return () -> new OfferKfzAndKpsMailContext(new OfferKps("offerNumber kps", 0.1, LocalDate.now()), new OfferKfz("offerNumber kfz", LocalDate.now()));
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
