package com.kmarinos.businessemaildemo.mail.offerProviders.mailCtx;

import com.kmarinos.businessemaildemo.mail.MailContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class SimpleMailCtx extends MailContext {
    String dummy;
}
