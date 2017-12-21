package bhouse.radiovolumes;

/**
 * Created by kranck on 10/8/2017.
 */

public class SliceVectorItem {
    private String filename;
    private String side;
    private String location;
    private double xMargin;
    private double yMargin;

    public double getxMargin() {
        return xMargin;
    }

    public void setxMargin(double xMargin) {
        this.xMargin = xMargin;
    }

    public double getyMargin() {
        return yMargin;
    }

    public void setyMargin(double yMargin) {
        this.yMargin = yMargin;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
