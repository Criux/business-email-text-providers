package com.kmarinos.businessemaildemo.mail;


public abstract class OfferTextProvider<OfferContext, T extends MailContext> extends ContextualPartsTextProvider<OfferContext, T> {
    public OfferTextProvider(OfferContext offerContext) {
        super(offerContext);
    }

    public abstract String header(T mailContext);

    public abstract String mainInfo(T mailContext);

    public abstract String secondaryInfo(T mailContext);

    public abstract String tertiaryInfo(T mailContext);

    @Override
    protected void orchestrateTextParts() {
        add("header", ctx -> new TextPart("", header(ctx), System.lineSeparator() + System.lineSeparator()));
        add("mainInfo", ctx -> new TextPart("", mainInfo(ctx), System.lineSeparator()));
        add("secondaryInfo", ctx -> new TextPart("", secondaryInfo(ctx), System.lineSeparator() + System.lineSeparator()));
        add("tertiaryInfo", ctx -> new TextPart("", tertiaryInfo(ctx), ""));
    }

//    @Override
//    public StringBuilder execute(StringBuilder stringBuilder){
//
//        return super.build(stringBuilder);
//    }


}
