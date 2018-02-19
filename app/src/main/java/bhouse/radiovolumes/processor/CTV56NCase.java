package bhouse.radiovolumes.processor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


/**
 * The type Ctv 56 N case.
 * Stores CTV56 lymph node target volumes after computation of necessary volumes
 */
public class CTV56NCase implements Serializable{

    // Name and identifier of elementary case
    private String caseName;
    private int identifier;

    List<String> modifier = new ArrayList<>();

    public List<String> getModifier() {
        return modifier;
    }

    public void setModifier(List<String> modifier) {
        this.modifier.addAll(modifier);
    }

    // Stores in Hashmap the target lymph node volumes associated to spread volume
    private List<LRNodeTargetVolume> cTV56NTarVolumes = new ArrayList<>();


    /**
     * Gets case t volumes.
     *
     * @return the case t volumes
     */
    public List<LRNodeTargetVolume> getCaseNTarVolumes() {
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
    public void addAllTVolumeToMap(List<LRNodeTargetVolume> TVolumes){
        this.cTV56NTarVolumes.addAll(TVolumes);
    }

    public void addAllModifiers(List<String> modifiers){
        this.modifier.addAll(modifiers);
    }

    /**
     * Removes duplicates in computed target volumes
     */
    public void removeTarVolumesDuplicates() {
        Set<LRNodeTargetVolume> s = new LinkedHashSet<>();
        s.addAll(this.getCaseNTarVolumes());
        this.cTV56NTarVolumes.clear();
        List<LRNodeTargetVolume> list = new ArrayList<LRNodeTargetVolume>();
        list.addAll(s);
        this.addAllTVolumeToMap(list);
    }

    public void removeModifiersDuplicates(){
        Set<String> s = new LinkedHashSet<>();
        s.addAll(this.getModifier());
        this.modifier.clear();
        List<String> list = new ArrayList<String>();
        list.addAll(s);
        this.addAllModifiers(list);
    }

    @Override
    public String toString() {
        return  "Case: " + caseName + "\n" + identifier + "-" + cTV56NTarVolumes.toString()  ;
    }
}

