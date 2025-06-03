package com.fresherlancer.app.eumeration;

public enum EmployementTypeEnum {

    FULL_TIME("Full Time"),
    PART_TIME("Part Time"),
    CONTRACT("Contract");

    String label;

    private EmployementTypeEnum(String label) {this.label = label;}

    public String getLabel() {return this.label;}
}
