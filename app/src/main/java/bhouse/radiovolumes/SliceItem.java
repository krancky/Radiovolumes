package bhouse.radiovolumes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kranck on 8/3/2017.
 */

public class SliceItem {
    private String storageLocation;
    private String number;
    private List<String> vectorStorageLocation = new ArrayList<>();

    public List<String> getVectorStorageLocation() {
        return vectorStorageLocation;
    }

    public void addVectorStorageLocation(String vectorStorageLocation) {
        this.vectorStorageLocation.add(vectorStorageLocation);
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
