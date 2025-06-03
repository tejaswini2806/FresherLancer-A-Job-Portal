package com.fresherlancer.app.eumeration;

public enum ApplicationStatusEnum {

    APPLIED("Applied"),
    SHORTLIST("Shortlisted"),
    REJECTED("Rejected"),
    ACTIVE("Active"),
    CLOSED("Closed");

    String label;

    private ApplicationStatusEnum(String label) {this.label = label;}

    public String getLabel() {return this.label;}
}
