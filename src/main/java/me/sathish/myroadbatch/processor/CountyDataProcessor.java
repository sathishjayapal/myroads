package me.sathish.myroadbatch.processor;

import me.sathish.myroadbatch.data.County;
import me.sathish.myroadbatch.data.CountyInput;
import me.sathish.myroadbatch.data.Road;
import me.sathish.myroadbatch.data.RoadInput;
import org.springframework.batch.item.ItemProcessor;

public class CountyDataProcessor implements ItemProcessor<CountyInput, County> {
    @Override
    public County process(CountyInput countyInput) throws Exception {
        County county = new County();
        county.setCounty_code(countyInput.getCountyCode());
        county.setCounty_name(countyInput.getCountyName());
        county.setRegion_name(countyInput.getRegionName());
        return county;
    }
}
