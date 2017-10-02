package bhouse.radiovolumes.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * The type Ctv 56 N ucase.
 * Stores CTV56 lymph node target volumes as a function of a sole spread volume
 */
public class CTV56TUCase {

    // Name and identifier of elementary case
    private String location;
    private int identifier;
    //Side of the U case
    private String side;
    public boolean advanced;
    public boolean sided;


    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }


    // Stores in Hashmap one and only one spread volume
    private HashMap<String, Integer> uCaseSVolumes = new HashMap<String, Integer>();

    // Stores in Hashmap the target lymph node volumes associated to spread volume
    private List<LRTumorTargetVolume> uCaseTVolumes = new ArrayList<LRTumorTargetVolume>() {
    };

    /**
     * Gets case s volumes.
     *
     * @return the case s volumes
     */
    public HashMap<String, Integer> getuCaseSVolumes() {
        return uCaseSVolumes;
    }

    /**
     * Gets case t volumes.
     *
     * @return the case t volumes
     */
    public List<LRTumorTargetVolume> getuCaseTVolumes() {
        return uCaseTVolumes;
    }

    /**
     * Gets identifier.
     *
     * @return the identifier
     */
    public int getIdentifier() {
        return identifier;
    }

    /**
     * Sets identifier.
     *
     * @param identifier the identifier
     */
    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }


    /**
     * Sets case name.
     *
     * @param location the case name
     */
    public void setLocation(String location) {
        this.location = location;

    }

    /**
     * Get case name string.
     *
     *
     * @return the string
     */
    public String getLocation(){
        return location;
    }

    public boolean isAdvanced() {
        return advanced;
    }

    public void setAdvanced(boolean advanced) {
        this.advanced = advanced;
    }

    public boolean isSided() {
        return sided;
    }

    public void setSided(boolean sided) {
        this.sided = sided;
    }

    /**
     * Add s volume to map.
     *
     * @param volume the volume
     * @param token  the token
     */
    public void addSVolumeToMap(String volume, Integer token){
        this.uCaseSVolumes.put(volume, token);
    }

    /**
     * Add t volume to map.
     *
     * @param volume the volume
     * @param token  the token
     */
    public void addTVolumeToMap(LRTumorTargetVolume lrTumorTargetVolume){
        this.uCaseTVolumes.add(lrTumorTargetVolume);
    }

    @Override
    public String toString() {
        return  "Case: " + location + "\n" + identifier + "-" + uCaseTVolumes.toString()  ;
    }
}

