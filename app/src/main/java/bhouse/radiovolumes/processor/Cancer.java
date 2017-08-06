package bhouse.radiovolumes.processor;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The type Cancer.
 * Harbors cancer input information.
 * Provided by Template cases after user input
 */
public class Cancer implements Serializable{
    //private String area;
    // Private hashmaps store cancer data
    private List<NodeAreaTemplate> cancerNVolumes = new ArrayList<NodeAreaTemplate>();
    private List<TumorAreaTemplate> cancerTVolumes = new ArrayList<TumorAreaTemplate>();
    private String mainSide;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    private String mainArea;

    private Date time;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getMainArea() {
        return mainArea;
    }

    public void setMainArea(String mainArea) {
        this.mainArea = mainArea;
    }

    public String getMainSide() {
        return mainSide;
    }

    public void setMainSide(String mainSide) {
        this.mainSide = mainSide;
    }

    /**
     * Add n volume from NodeTemplate
     *
     * @param spreadVolume the spread volume
     * @param integer      the integer
     */
    public void addNVolume (NodeAreaTemplate nodeAreaTemplate) {
        if (nodeAreaTemplate.getLeftContent() != "0" || nodeAreaTemplate.getRightContent()!="0") {
            cancerNVolumes.add(nodeAreaTemplate);
        }
    }

    public int getImageResourceId(Context context) {
        Log.i("valeur identifiant", String.valueOf(context.getResources().getIdentifier(this.mainArea, "drawable", context.getPackageName())));
        return context.getResources().getIdentifier(this.mainArea.replaceAll("\\s+", "").toLowerCase(), "drawable", context.getPackageName());
    }

    /**
     * Add t volume from TumorTemplate
     *
     * @param spreadTVolume the spread t volume
     */
    public void addTVolume (TumorAreaTemplate tumorAreaTemplate){
        if (tumorAreaTemplate.getLeftContent() != "0" || tumorAreaTemplate.getRightContent() != "0"){
            cancerTVolumes.add(tumorAreaTemplate);
        }

    }

    /**
     * Gets cancer n volumes.
     *
     * @return the cancer n volumes
     */
    public List<NodeAreaTemplate> getCancerNVolumes() {
        return cancerNVolumes;
    }

    /**
     * Gets cancer t volumes.
     *
     * @return the cancer t volumes
     */
    public List<TumorAreaTemplate> getCancerTVolumes() {
        return cancerTVolumes;
    }

    /**
     * Clears cancer volumes.
     */
    public void nClear(){
        cancerNVolumes.clear();
    }

    public void tClear(){
        cancerTVolumes.clear();
    }

    @Override
    public String toString() {
        return "Cancer{" +
                "cancerNVolumes=" + cancerNVolumes +
                '}';
    }

    public void saveToFile (Context context){
        try{
            FileOutputStream fileOutputStream = context.openFileOutput(this.name +".duc", Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
            fileOutputStream.close();
            Log.i("path ecriture", context.getFilesDir().toString());
            Log.i("path ecriture", context.getFilesDir().toString());
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static Cancer readFromFile(Context context, String name){
        Cancer cancer = null;
        try{
            FileInputStream fileInputStream = new FileInputStream(new File(name));
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            cancer = (Cancer) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return cancer;
    }

}
