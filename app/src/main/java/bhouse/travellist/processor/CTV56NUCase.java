package bhouse.travellist.processor;
import java.util.HashMap;


/**
 * The type Ctv 56 N ucase.
 * Stores CTV56 lymph node target volumes as a function of a sole spread volume
 */
public class CTV56NUCase {

    // Name and identifier of elementary case

    private String location;
    private String side;
    public String spreadLocation;
    public String spreadSide;

    private int identifier;
    // Stores in Hashmap one and only one spread volume
    private HashMap<String, Integer> uCaseSVolumes = new HashMap<String, Integer>();
    // Stores in Hashmap the target lymph node volumes associated to spread volume
    private HashMap<String, String> uCaseTVolumes = new HashMap<String, String>();

    /**
     * Gets case s volumes.
     *
     * @return the case s volumes
     */
    public HashMap<String, Integer> getuCaseSVolumes() {
        return uCaseSVolumes;
    }

    public String getSide() {
        return side;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSpreadLocation() {
        return spreadLocation;
    }

    public void setSpreadLocation(String spreadLocation) {
        this.spreadLocation = spreadLocation;
    }

    public String getSpreadSide() {
        return spreadSide;
    }

    public void setSpreadSide(String spreadSide) {
        this.spreadSide = spreadSide;
    }

    public void setSide(String side) {
        this.side = side;
    }
    /**
     * Gets case t volumes.
     *
     * @return the case t volumes
     */
    public HashMap<String, String> getuCaseTVolumes() {
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
     * Add s volume to map. Called by parser.
     *
     * @param volume the volume
     * @param token  the token
     */
    public void addSVolumeToMap(String volume, Integer token){
        this.uCaseSVolumes.put(volume, token);
    }

    /**
     * Add t volume to map. Called by parser
     *
     * @param volume the volume
     * @param token  the token
     */
    public void addTVolumeToMap(String volume, String side){
        this.uCaseTVolumes.put(volume, side);
    }


    @Override
    public String toString() {
        return  "Case: "  + "\n" + identifier + "-" + uCaseTVolumes.toString()  ;
    }
}

