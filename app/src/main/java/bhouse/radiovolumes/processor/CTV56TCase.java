package bhouse.radiovolumes.processor;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


/**
 * The type Ctv 56 T case.
 * Stores CTV56 tumor target volumes after computation of necessary volumes
 */
public class CTV56TCase implements Serializable{

    // Name and identifier of elementary case
    private String caseName;
    private int identifier;

    // Stores in Hashmap the target lymph node volumes associated to spread volume
    private List<LRTumorTargetVolume> cTV56TTarVolumes = new ArrayList<LRTumorTargetVolume>();


    /**
     * Gets case t volumes.
     *
     * @return the case t volumes
     */
    public List<LRTumorTargetVolume> getCaseTTarVolumes() {
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
    public void addAllTVolumeToMap(List<LRTumorTargetVolume> TVolumes){
        this.cTV56TTarVolumes.addAll(TVolumes);
    }

    /**
     * Removes duplicates in target volumes
     */
    public void removeTarVolumesDuplicates(){
        Set<LRTumorTargetVolume> s = new LinkedHashSet<LRTumorTargetVolume>();
        s.addAll(this.getCaseTTarVolumes());
        this.cTV56TTarVolumes.clear();
        List<LRTumorTargetVolume> list = new ArrayList<LRTumorTargetVolume>();
        list.addAll(s);
        this.addAllTVolumeToMap(list);
    }

    public List<LRTumorTargetVolume> getcTV56TTarVolumes() {
        return cTV56TTarVolumes;
    }

    @Override
    public String toString() {
        return  "Case: " + caseName + "\n" + identifier + "-" + cTV56TTarVolumes.toString()  ;
    }
}

