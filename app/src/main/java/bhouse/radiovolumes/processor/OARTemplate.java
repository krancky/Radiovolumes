package bhouse.radiovolumes.processor;

import android.content.Context;

import java.io.Serializable;

/**
 * The type Tumor area template.
 *
 */
public class OARTemplate implements Serializable{

    String location,leftContent, rightContent, otherConstraints, side; String constraints; String complications; String lateralized; String comment;
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

    public void setLateralized(String lateralized) {
        this.lateralized = lateralized;
    }

    public String getLateralized() {
        return lateralized;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRightContent() {
        return rightContent;
    }

    public void setRightContent(String rightContent) {
        this.rightContent = rightContent;
    }

    public void setOtherconstraints(String otherConstraints) {
        this.otherConstraints = otherConstraints;
    }

    public String getOtherConstraints() {
        return this.otherConstraints;
    }

    public String getConstraints() {
        return constraints;
    }

    public String getComplications() {
        return complications;
    }

    public void setComplications(String complications) {
        this.complications = complications;
    }

    public void setConstraints(String constraints) {
        this.constraints = constraints;
    }

    public int getImageResourceId(Context context, boolean isChecked) {
        if  (isChecked){
            return context.getResources().getIdentifier("ic_"+ location.replaceAll("\\s+", "").toLowerCase() +"_ok", "drawable", context.getPackageName());
        }
        else{
            return context.getResources().getIdentifier("ic_"+ location.replaceAll("\\s+", "").toLowerCase(), "drawable", context.getPackageName());
        }
    }

}