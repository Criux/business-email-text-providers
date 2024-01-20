package com.kmarinos.businessemaildemo;

import com.kmarinos.businessemaildemo.mail.Tenant;

import java.util.Locale;

public class DemoUtils {
    public static Locale DEMO_LOCALE=Locale.ENGLISH;
    public static Tenant DEMO_TENANT=new Tenant("demo-tenant",DEMO_LOCALE);
}
