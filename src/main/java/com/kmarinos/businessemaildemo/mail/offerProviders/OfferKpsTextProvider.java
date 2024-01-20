package com.kmarinos.businessemaildemo.mail.offerProviders;

import com.kmarinos.businessemaildemo.OfferContext;
import com.kmarinos.businessemaildemo.mail.OfferKps;
import com.kmarinos.businessemaildemo.mail.OfferTextProvider;
import com.kmarinos.businessemaildemo.mail.offerProviders.mailCtx.OfferKpsMailContext;

import java.time.LocalDate;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OfferKpsTextProvider extends OfferTextProvider<OfferContext, OfferKpsMailContext> {

    @Override
    public Predicate<OfferContext> getCondition() {
        return OfferContext::isKps;
    }

    public OfferKpsTextProvider(OfferContext offerContext) {
        super(offerContext);
    }

    @Override
    public String header(OfferKpsMailContext mailContext) {
        return "header for " + mailContext.getOfferKps().offerNumber() + " with discount " + mailContext.getOfferKps().discount();
    }

    @Override
    public String mainInfo(OfferKpsMailContext mailContext) {
        return defer(to(SimpleTextProvider.class)::standardText);
    }

    @Override
    public String secondaryInfo(OfferKpsMailContext mailContext) {
        return defer(toStandard());
    }

    @Override
    public String tertiaryInfo(OfferKpsMailContext mailContext) {
        return defer();
    }

    @Override
    public Supplier<OfferKpsMailContext> getTextContext() {
        return () -> new OfferKpsMailContext(new OfferKps("offerNumber kps", 0.1, LocalDate.now()));
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
