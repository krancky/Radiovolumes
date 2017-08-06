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
    String leftContent;
    String rightContent;

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


    public String getCompleteName() {
        return completeName;
    }

    public void setCompleteName(String completeName) {
        this.completeName = completeName;
    }

    public int getImageResourceId(Context context, boolean isChecked) {
    if  (isChecked){
        return context.getResources().getIdentifier("ic_"+ nodeLocation.toLowerCase() +"_ok", "drawable", context.getPackageName());
    }
    else{
        return context.getResources().getIdentifier("ic_"+ nodeLocation.toLowerCase(), "drawable", context.getPackageName());
    }
    }

}