package me.sathish.myroadbatch.data;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CountyInput {
    private String objectid;
    private String regionName;
    private String countyCode;
    private String countyName;
    private String countyFipsCode;
    private String shapeArea;
    private String shapeLen;
}
