package com.kmarinos.businessemaildemo.mail;

import java.time.LocalDate;

public record OfferKps(String offerNumber, Double discount, LocalDate createdAt) {
}
