package bhouse.travellist.processor;

import java.io.Serializable;
import java.util.HashMap;

/**
 * The type Cancer.
 * Harbors cancer input information.
 * Provided by Template cases after user input
 */
public class Cancer implements Serializable{
    private String area;
    // Private hashmaps store cancer data
    private HashMap<String, Integer> cancerNVolumes = new HashMap<String, Integer>();
    private HashMap<String, String> cancerTVolumes = new HashMap<String, String>();

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
    public void addTVolume (String spreadTAreaVolume, String spreadTVolume){
        if (spreadTVolume != "0"){
            cancerTVolumes.put(spreadTVolume,spreadTAreaVolume);
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
    public HashMap<String, String> getCancerTVolumes() {
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
