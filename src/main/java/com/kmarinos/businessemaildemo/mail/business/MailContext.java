package com.kmarinos.businessemaildemo.mail.business;

import lombok.Data;

import java.util.Locale;

@Data
public abstract class MailContext {
    Tenant tenant;
    Locale locale;
}
