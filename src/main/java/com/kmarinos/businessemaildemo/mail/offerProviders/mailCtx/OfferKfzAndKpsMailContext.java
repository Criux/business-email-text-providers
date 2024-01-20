package com.kmarinos.businessemaildemo.mail.offerProviders.mailCtx;

import com.kmarinos.businessemaildemo.mail.MailContext;
import com.kmarinos.businessemaildemo.mail.OfferKfz;
import com.kmarinos.businessemaildemo.mail.OfferKps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class OfferKfzAndKpsMailContext extends MailContext {
    OfferKps offerKps;
    OfferKfz offerKfz;
}
