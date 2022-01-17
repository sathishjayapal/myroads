package me.sathish.myroadbatch.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class Road {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String road_name;
    private String surface_type;
    private String miles;
    private String total_miles;
    private String rustic_road_number;
    private String community;
    private String county;
    private String line_to_web_page;
    private String shape_len;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRoad_name() {
        return road_name;
    }

    public void setRoad_name(String road_name) {
        this.road_name = road_name;
    }

    public String getSurface_type() {
        return surface_type;
    }

    public void setSurface_type(String surface_type) {
        this.surface_type = surface_type;
    }

    public String getMiles() {
        return miles;
    }

    public void setMiles(String miles) {
        this.miles = miles;
    }

    public String getTotal_miles() {
        return total_miles;
    }

    public void setTotal_miles(String total_miles) {
        this.total_miles = total_miles;
    }

    public String getRustic_road_number() {
        return rustic_road_number;
    }

    public void setRustic_road_number(String rustic_road_number) {
        this.rustic_road_number = rustic_road_number;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getLine_to_web_page() {
        return line_to_web_page;
    }

    public void setLine_to_web_page(String line_to_web_page) {
        this.line_to_web_page = line_to_web_page;
    }

    public String getShape_len() {
        return shape_len;
    }

    public void setShape_len(String shape_len) {
        this.shape_len = shape_len;
    }
}
