package bhouse.radiovolumes.processor;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The type Tumor area template.
 *
 */
public class TumorAreaTemplate implements Serializable{

    String location,leftContent, rightContent, area, side; String locationLocale; String areaLocale;
    ArrayList<String> subLocation = new ArrayList<>();
    int color;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLeftContent() {
        return leftContent;
    }

    public void setLeftContent(String leftContent) {
        this.leftContent = leftContent;
    }

    public String getRightContent() {
        return rightContent;
    }

    public void setRightContent(String rightContent) {
        this.rightContent = rightContent;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getArea() {
        return this.area;
    }

    public String getLocationLocale() {
        return locationLocale;
    }

    public String getAreaLocale() {
        return areaLocale;
    }

    public void setAreaLocale(String areaLocale) {
        this.areaLocale = areaLocale;
    }

    public void setLocationLocale(String locationLocale) {
        this.locationLocale = locationLocale;
    }

    public void setSubLocation(String subLocation){this.subLocation.add(subLocation);}

    public ArrayList<String> getSubLocation(){return this.subLocation;};

    public int getImageResourceId(Context context, boolean isChecked) {
        if  (isChecked){
            return context.getResources().getIdentifier("ic_"+ location.replaceAll("\\s+", "").toLowerCase() +"_ok", "drawable", context.getPackageName());
        }
        else{
            return context.getResources().getIdentifier("ic_"+ location.replaceAll("\\s+", "").toLowerCase(), "drawable", context.getPackageName());
        }
    }

}