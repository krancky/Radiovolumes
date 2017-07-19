package bhouse.travellist.processor;

import java.io.Serializable;

/**
 * The type Tumor area template.
 *
 */
public class TumorAreaTemplate implements Serializable{

    String location,content, area, side;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getArea() {
        return this.area;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getSide() {
        return this.side;
    }

}