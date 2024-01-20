package com.kmarinos.businessemaildemo.mail.demo.offer.kfzAndKps.context;

import com.kmarinos.businessemaildemo.mail.business.MailContext;
import com.kmarinos.businessemaildemo.mail.demo.offer.kfz.context.OfferKfz;
import com.kmarinos.businessemaildemo.mail.demo.offer.kps.context.OfferKps;
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
