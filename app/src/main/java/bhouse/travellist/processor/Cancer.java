package bhouse.travellist.processor;

import android.util.Log;

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
    private HashMap<String, Integer> cancerNVolumes = new HashMap<String, Integer>();
    private HashMap<String, List<String>> cancerTVolumes = new HashMap<String, List<String>>();

    /**
     * Add n volume from NodeTemplate
     *
     * @param spreadVolume the spread volume
     * @param integer      the integer
     */
    public void addNVolume (String spreadVolume, Integer integer) {
        if (spreadVolume != "0") {
            cancerNVolumes.put(spreadVolume, integer);
        }
    }

    /**
     * Add t volume from TumorTemplate
     *
     * @param spreadTVolume the spread t volume
     */
    public void addTVolume (TumorAreaTemplate tumorAreaTemplate){
        if (tumorAreaTemplate.getContent() != "0"){
            List<String> tList = new ArrayList<String>();
            tList.add(tumorAreaTemplate.getSide());
            tList.add(tumorAreaTemplate.getArea());
            Log.i("contenu area", tumorAreaTemplate.getArea() );
            cancerTVolumes.put(tumorAreaTemplate.getLocation(),tList);
            Log.i("contenu bizarre", cancerTVolumes.get(tumorAreaTemplate.getLocation()).toString());
        }

    }

    /**
     * Gets cancer n volumes.
     *
     * @return the cancer n volumes
     */
    public HashMap<String, Integer> getCancerNVolumes() {
        return cancerNVolumes;
    }

    /**
     * Gets cancer t volumes.
     *
     * @return the cancer t volumes
     */
    public HashMap<String, List<String>> getCancerTVolumes() {
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
}
