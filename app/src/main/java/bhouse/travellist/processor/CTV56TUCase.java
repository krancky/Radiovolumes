package bhouse.travellist.processor;

import java.util.HashMap;


/**
 * The type Ctv 56 N ucase.
 * Stores CTV56 lymph node target volumes as a function of a sole spread volume
 */
public class CTV56TUCase {

    // Name and identifier of elementary case
    private String caseName;
    private int identifier;
    // Stores in Hashmap one and only one spread volume
    private HashMap<String, Integer> uCaseSVolumes = new HashMap<String, Integer>();
    // Stores in Hashmap the target lymph node volumes associated to spread volume
    private HashMap<String, Integer> uCaseTVolumes = new HashMap<String, Integer>();

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
    public HashMap<String, Integer> getuCaseTVolumes() {
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
     * @param caseName the case name
     */
    public void setCaseName(String caseName) {
        this.caseName = caseName;

    }

    /**
     * Get case name string.
     *
     *
     * @return the string
     */
    public String getCaseName(){
        return caseName;
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
    public void addTVolumeToMap(String volume, Integer token){
        this.uCaseTVolumes.put(volume, token);
    }

    @Override
    public String toString() {
        return  "Case: " + caseName + "\n" + identifier + "-" + uCaseTVolumes.toString()  ;
    }
}

