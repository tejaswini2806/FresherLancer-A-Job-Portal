package com.fresherlancer.app.eumeration;

public enum CandidateStatusEnum {

    ACTIVE("Active"),
    BOOKED("Booked"),
    AVAILABLE("Available");

    String label;

    private CandidateStatusEnum(String label) {this.label = label;}

    public String getLabel() {return this.label;}
}
