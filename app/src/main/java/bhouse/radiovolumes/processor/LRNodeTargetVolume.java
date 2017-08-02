package bhouse.radiovolumes.processor;

import java.io.Serializable;

/**
 * Created by kranck on 7/19/2017.
 */

public class LRNodeTargetVolume implements Serializable{

    String location = new String();
    String content = new String();
    String area = new String();
    String side = new String();

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

    public String toString() {
        return (this.location + this.side + this.area);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LRNodeTargetVolume that = (LRNodeTargetVolume) o;

        if (location != null ? !location.equals(that.location) : that.location != null)
            return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (area != null ? !area.equals(that.area) : that.area != null) return false;
        return side != null ? side.equals(that.side) : that.side == null;

    }

    @Override
    public int hashCode() {
        int result = location != null ? location.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (area != null ? area.hashCode() : 0);
        result = 31 * result + (side != null ? side.hashCode() : 0);
        return result;
    }
}
