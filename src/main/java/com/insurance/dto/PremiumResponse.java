package com.insurance.dto;

public class PremiumResponse {
    private Double premiumAmount;

    public PremiumResponse(Double premiumAmount) {
        this.premiumAmount = premiumAmount;
    }

    public Double getPremiumAmount() {
        return premiumAmount;
    }
}
