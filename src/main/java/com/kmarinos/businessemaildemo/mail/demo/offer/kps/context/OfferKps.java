package com.kmarinos.businessemaildemo.mail.demo.offer.kps.context;

import java.time.LocalDate;

public record OfferKps(String offerNumber, Double discount, LocalDate createdAt) {
}
