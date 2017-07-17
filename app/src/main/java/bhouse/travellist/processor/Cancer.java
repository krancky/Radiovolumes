package bhouse.travellist.processor;

import java.util.HashMap;

/**
 * The type Cancer.
 * Harbors cancer input information.
 * Provided by Template cases after user input
 */
public class Cancer {
    private String area;
    // Private hashmaps store cancer data
    private HashMap<String, Integer> cancerNVolumes = new HashMap<String, Integer>();
    private HashMap<String, Integer> cancerTVolumes = new HashMap<String, Integer>();

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
     * @param integer       the integer
     */
    public void addTVolume (String spreadTVolume, Integer integer){
        if (spreadTVolume != "0"){
            cancerTVolumes.put(spreadTVolume, integer);
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
    public HashMap<String, Integer> getCancerTVolumes() {
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
