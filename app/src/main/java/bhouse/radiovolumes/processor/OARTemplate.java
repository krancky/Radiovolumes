package bhouse.radiovolumes.processor;

import android.content.Context;

import java.io.Serializable;

/**
 * The type Tumor area template.
 *
 */
public class OARTemplate implements Serializable{

    String location,leftContent, rightContent, otherConstraints, side; String constraints; String complications; String lateralized; String comment;
    String cranial; String caudal; String anterior; String posterior; String lateral ; String medial;

    public void setOtherConstraints(String otherConstraints) {
        this.otherConstraints = otherConstraints;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getCranial() {
        return cranial;
    }

    public void setCranial(String cranial) {
        this.cranial = cranial;
    }

    public String getCaudal() {
        return caudal;
    }

    public void setCaudal(String caudal) {
        this.caudal = caudal;
    }

    public String getAnterior() {
        return anterior;
    }

    public void setAnterior(String anterior) {
        this.anterior = anterior;
    }

    public String getPosterior() {
        return posterior;
    }

    public void setPosterior(String posterior) {
        this.posterior = posterior;
    }

    public String getLateral() {
        return lateral;
    }

    public void setLateral(String lateral) {
        this.lateral = lateral;
    }

    public String getMedial() {
        return medial;
    }

    public void setMedial(String medial) {
        this.medial = medial;
    }

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