package com.fresherlancer.app.eumeration;

public enum JobStatusEnum {

    ACTIVE("Active"),
    BOOKED("Booked"),
    OPEN("Open"),
    REOPEN("Reopen");

    String label;

    private JobStatusEnum(String label) {this.label = label;}

    public String getLabel() {return this.label;}
}
