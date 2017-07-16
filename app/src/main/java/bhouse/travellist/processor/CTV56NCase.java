package bhouse.travellist.processor;

import java.util.HashMap;


/**
 * The type Ctv 56 N case.
 * Stores CTV56 lymph node target volumes after computation of necessary volumes
 */
public class CTV56NCase {

    // Name and identifier of elementary case
    private String caseName;
    private int identifier;

    // Stores in Hashmap the target lymph node volumes associated to spread volume
    private HashMap<String, Integer> cTV56NTarVolumes = new HashMap<String, Integer>();


    /**
     * Gets case t volumes.
     *
     * @return the case t volumes
     */
    public HashMap<String, Integer> getCaseNTarVolumes() {
        return cTV56NTarVolumes;
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
     * Add all N Target volumes of selected uCase to TarVolumes
     *
     * @param TVolumes the t volumes
     */
    public void addAllTVolumeToMap(HashMap<String, Integer> TVolumes){
        this.cTV56NTarVolumes.putAll(TVolumes);
    }

    @Override
    public String toString() {
        return  "Case: " + caseName + "\n" + identifier + "-" + cTV56NTarVolumes.toString()  ;
    }
}

