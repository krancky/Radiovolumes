package bhouse.radiovolumes.processor;

import android.content.Context;
import android.util.Log;

import java.io.Serializable;

/**
 * The type Node area template.
 * Content stores the String used for N definition
 */
public class NodeAreaTemplate implements Serializable {

    String title;
    String content;
    String nodeLocation;
    String completeName;



    String side;
    int color;

    public String getNodeLocation() {
        return nodeLocation;
    }

    public void setNodeLocation(String nodeLocation) {
        this.nodeLocation = nodeLocation;
    }



    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getCompleteName() {
        return completeName;
    }

    public void setCompleteName(String completeName) {
        this.completeName = completeName;
    }

    public int getImageResourceId(Context context) {
        Log.i("node location", nodeLocation);
        return context.getResources().getIdentifier("ic_"+ nodeLocation.toLowerCase(), "drawable", context.getPackageName());
    }

}