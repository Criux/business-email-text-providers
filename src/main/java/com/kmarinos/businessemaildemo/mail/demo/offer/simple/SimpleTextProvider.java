package com.kmarinos.businessemaildemo.mail.demo.offer.simple;


import com.kmarinos.businessemaildemo.mail.demo.offer.OfferContext;
import com.kmarinos.businessemaildemo.core.providers.AbstractSimpleTextProvider;
import com.kmarinos.businessemaildemo.mail.business.MailContext;

import java.util.function.Predicate;

public class SimpleTextProvider extends AbstractSimpleTextProvider<OfferContext> {

    public SimpleTextProvider(OfferContext conditionalContext) {
        super(conditionalContext);
    }

    public String standardText(MailContext context) {
        return "standard text -> " + context.getTenant().name();
    }

    @Override
    protected void orchestrateTextParts() {
        addText(standardText(getContext().get()));
        addText("text 1");
        addText("text 2");
    }

    @Override
    public Predicate<OfferContext> getCondition() {
        return c -> true;
    }
}
