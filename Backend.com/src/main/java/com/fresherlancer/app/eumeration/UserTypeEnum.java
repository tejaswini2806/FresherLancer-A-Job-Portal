package com.fresherlancer.app.eumeration;

public enum UserTypeEnum {

    ADMIN("Admin"),
    AGENCY("Agency"),
    CANDIDATE("Candidate");

    String label;

    private UserTypeEnum(String label) {this.label = label;}

    public String getLabel() {return this.label;}
}
