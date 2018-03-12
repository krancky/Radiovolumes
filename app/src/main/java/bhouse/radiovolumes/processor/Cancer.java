package bhouse.radiovolumes.processor;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import bhouse.radiovolumes.R;

/**
 * The type Cancer.
 * Harbors cancer input information.
 * Provided by Template cases after user input
 */
public class Cancer implements Serializable {
    //private String area;
    // Private hashmaps store cancer data
    private List<NodeAreaTemplate> cancerNVolumes = new ArrayList<NodeAreaTemplate>();
    private List<TumorAreaTemplate> cancerTVolumes = new ArrayList<TumorAreaTemplate>();

    private List<TumorAreaTemplate> cancerTDeveloppedVolumes = new ArrayList<TumorAreaTemplate>();


    private HashMap<String, HashMap<String, List<String>>> cancerTTarData = new HashMap<String, HashMap<String, List<String>>>();
    private HashMap<String, List<String>> cancerNTarData = new HashMap<String, List<String>>();

    private HashMap<String, HashMap<String, List<String>>> cancerTData;
    private HashMap<String, List<String>> cancerNData;


    // Main side of Cancer
    private String mainSide;

    // Main Area of Cancer
    private String mainArea;

    //Name of Case
    private String name;

    // Date of last access
    private Date time;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
     * Add n volumes from NodeTemplate
     *
     * @param spreadVolume the spread volume
     * @param integer      the integer
     */
    public void addNVolume(NodeAreaTemplate nodeAreaTemplate) {
        if (nodeAreaTemplate.getLeftContent() != "0" || nodeAreaTemplate.getRightContent() != "0") {
            cancerNVolumes.add(nodeAreaTemplate);
        }
    }

    /**
     * Returns the identifier of the image associated with the main area
     * to display within the loading activity
     *
     * @param context
     * @return integer
     */
    public int getImageResourceId(Context context) {
        String[] mainSideArray = context.getResources().getStringArray(R.array.main_areas_array);
        return context.getResources().getIdentifier(mainSideArray[Integer.valueOf(this.mainArea)].replaceAll("\\s+", "").replaceAll("é", "e").toLowerCase(), "drawable", context.getPackageName());
    }

    /**
     * Add t volume from TumorTemplate
     *
     * @param spreadTVolume the spread t volume
     */
    public void addTVolume(TumorAreaTemplate tumorAreaTemplate) {
        if (tumorAreaTemplate.getLeftContent() != "0" || tumorAreaTemplate.getRightContent() != "0") {
            cancerTVolumes.add(tumorAreaTemplate);
        }
    }

    public void setDeveloppedVolumes() {
        //this.cancerTDeveloppedVolumes = this.cancerTVolumes;
        for (TumorAreaTemplate tumorAreaTemplate : this.cancerTVolumes) {
            if (tumorAreaTemplate.getSubLocation().size() != 0) {
                int i;
                for (i=0;i<tumorAreaTemplate.getSubLocation().size();i++) {
                    TumorAreaTemplate newTAT = new TumorAreaTemplate();
                    newTAT.setLocation(tumorAreaTemplate.getSubLocation().get(i).replaceAll("\\s+", "").toLowerCase());
                    if (tumorAreaTemplate.getLeftContent().equals("1") && tumorAreaTemplate.getSubLocationLeftContent().get(i).equals("1")) {
                        newTAT.setLeftContent("1");
                    } else {
                        newTAT.setLeftContent("0");
                    }
                    if (tumorAreaTemplate.getRightContent().equals("1") && tumorAreaTemplate.getSubLocationRightContent().get(i).equals("1")) {
                        newTAT.setRightContent("1");
                    } else {
                        newTAT.setRightContent("0");
                    }

                    if (newTAT.getRightContent().equals("1") | newTAT.getLeftContent().equals("1")){
                        this.cancerTDeveloppedVolumes.add(newTAT);
                    }
                }
            } else {
                this.cancerTDeveloppedVolumes.add(tumorAreaTemplate);
            }
        }
    }


    public List<TumorAreaTemplate> getCancerTDeveloppedVolumes() {
        return cancerTDeveloppedVolumes;
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
    public void nClear() {
        cancerNVolumes.clear();
    }

    public void tClear() {
        cancerTVolumes.clear();
    }

    public void tDClear () {cancerTDeveloppedVolumes.clear();}

    public void setCancerTTarData(HashMap<String, HashMap<String, List<String>>> cancerTTarData) {
        this.cancerTTarData = cancerTTarData;
    }

    public void setCancerNTarData(HashMap<String, List<String>> cancerNTarData) {
        this.cancerNTarData = cancerNTarData;
    }


    /**
     * Saves the cancer to file as a .duc file
     *
     * @param context
     */
    public void saveToFile(Context context) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(this.name + ".duc", Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads all cancer data from file.
     * Called when clicking on case in loading activity
     *
     * @param context
     * @param name
     * @return
     */
    public static Cancer readFromFile(Context context, String name) {
        Cancer cancer = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(name));
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            cancer = (Cancer) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return cancer;
    }

    public void removeCancer(Context context) {


        File dir = context.getFilesDir();
        File file = new File(dir, this.getName() + ".duc");
        boolean deleted = file.delete();

    }

    public HashMap<String, HashMap<String, List<String>>> getCancerTTarData() {
        return cancerTTarData;
    }

    public HashMap<String, HashMap<String, List<String>>> getCancerTData() {
        return cancerTData;
    }

    public void setCancerTData(HashMap<String, HashMap<String, List<String>>> cancerTData) {
        this.cancerTData = cancerTData;
    }

    public HashMap<String, List<String>> getCancerNData() {
        return cancerNData;
    }

    public void setCancerNData(HashMap<String, List<String>> cancerNData) {
        this.cancerNData = cancerNData;
    }

    public HashMap<String, List<String>> getCancerNTarData() {
        return cancerNTarData;
    }

    public void setCancerTVolumes(List<TumorAreaTemplate> cancerTVolumes) {
        this.cancerTVolumes = cancerTVolumes;
    }
}
