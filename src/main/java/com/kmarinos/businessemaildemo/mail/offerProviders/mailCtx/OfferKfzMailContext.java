package com.kmarinos.businessemaildemo.mail.offerProviders.mailCtx;

import com.kmarinos.businessemaildemo.mail.MailContext;
import com.kmarinos.businessemaildemo.mail.OfferKfz;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class OfferKfzMailContext extends MailContext {
    OfferKfz offerKfz;
}
