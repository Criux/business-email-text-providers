package com.kmarinos.businessemaildemo.mail;

import lombok.Data;

import java.util.Locale;

@Data
public abstract class MailContext {
    Tenant tenant;
    Locale locale;
}
