package me.sathish.myroadbatch.processor;

import me.sathish.myroadbatch.data.Road;
import me.sathish.myroadbatch.data.RoadInput;
import org.springframework.batch.item.ItemProcessor;

public class RoadDataProcessor implements ItemProcessor<RoadInput, Road> {
    @Override
    public Road process(RoadInput roadInput) throws Exception {
        Road road = new Road();
        road.setRoad_name(roadInput.getRoadName());
        road.setSurface_type(roadInput.getSurfaceType());
        road.setMiles(roadInput.getMiles());
        road.setTotal_miles(roadInput.getTotalMiles());
        road.setRustic_road_number(roadInput.getRusticRoadNumber());
        road.setCommunity(roadInput.getCommunity());
        road.setCounty(roadInput.getCounty());
        road.setLine_to_web_page(roadInput.getLineToWebpage());
        road.setShape_len(roadInput.getShapeLen());
        return road;
    }
}
