package bhouse.travellist.processor;

import android.util.Log;

import java.util.HashMap;


/**
 * The type Ctv 56 T case.
 * Stores CTV56 tumor target volumes after computation of necessary volumes
 */
public class CTV56TCase {

    // Name and identifier of elementary case
    private String caseName;
    private int identifier;

    // Stores in Hashmap the target lymph node volumes associated to spread volume
    private HashMap<String, Integer> cTV56TTarVolumes = new HashMap<String, Integer>();


    /**
     * Gets case t volumes.
     *
     * @return the case t volumes
     */
    public HashMap<String, Integer> getCaseTTarVolumes() {
        return cTV56TTarVolumes;
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
     * @param caseName the case name
     * @return the string
     */
    public String getCaseName(String caseName){
        return caseName;
    }

    /**
     * Add s volume to map.
     *
     * @param volume the volume
     * @param token  the token
     */


    /**
     * Add t volume to map.
     *
     * @param volume the volume
     * @param token  the token
     */


    /**
     * Add all tumor target volume to map. Adds from selected uCase
     *
     * @param TVolumes the t volumes
     */
    public void addAllTVolumeToMap(HashMap<String, Integer> TVolumes){
        this.cTV56TTarVolumes.putAll(TVolumes);
        Log.i("chargement","stuff");
    }

    @Override
    public String toString() {
        return  "Case: " + caseName + "\n" + identifier + "-" + cTV56TTarVolumes.toString()  ;
    }
}

