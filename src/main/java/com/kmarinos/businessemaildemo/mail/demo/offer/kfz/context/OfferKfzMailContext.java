package com.kmarinos.businessemaildemo.mail.demo.offer.kfz.context;

import com.kmarinos.businessemaildemo.mail.business.MailContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class OfferKfzMailContext extends MailContext {
    OfferKfz offerKfz;
}
