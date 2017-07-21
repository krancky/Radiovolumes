package bhouse.travellist.processor;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    /**
     * Add n volume from NodeTemplate
     *
     * @param spreadVolume the spread volume
     * @param integer      the integer
     */
    public void addNVolume (NodeAreaTemplate nodeAreaTemplate) {
        if (nodeAreaTemplate.getContent() != "0") {
            cancerNVolumes.add(nodeAreaTemplate);
        }
    }

    /**
     * Add t volume from TumorTemplate
     *
     * @param spreadTVolume the spread t volume
     */
    public void addTVolume (TumorAreaTemplate tumorAreaTemplate){
        if (tumorAreaTemplate.getContent() != "0"){
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
            Log.i("path ecriture", "yo");
            FileOutputStream fileOutputStream = context.openFileOutput(this.name, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
            fileOutputStream.close();
            Log.i("path ecriture", context.getFilesDir().toString());
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static Cancer readFromFile(Context context, String name){
        Cancer cancer = null;
        try{
            FileInputStream fileInputStream = context.openFileInput(name);
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
