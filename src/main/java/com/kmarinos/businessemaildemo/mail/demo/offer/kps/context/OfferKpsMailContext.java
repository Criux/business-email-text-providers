package com.kmarinos.businessemaildemo.mail.demo.offer.kps.context;

import com.kmarinos.businessemaildemo.mail.business.MailContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class OfferKpsMailContext extends MailContext {
    OfferKps offerKps;
}
