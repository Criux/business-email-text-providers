package com.kmarinos.businessemaildemo.mail.offerProviders;


import com.kmarinos.businessemaildemo.OfferContext;
import com.kmarinos.businessemaildemo.mail.AbstractSimpleTextProvider;
import com.kmarinos.businessemaildemo.mail.MailContext;

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
