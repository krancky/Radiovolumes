package bhouse.radiovolumes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kranck on 8/3/2017.
 */

public class Slice {
    private String scanStorageLocation;
    private String number;
    //private List<String> vectorStorageLocation = new ArrayList<>();
    private List<SliceVectorItem> vectorStorageLocation = new ArrayList<>();

    public List<SliceVectorItem> getVectorStorageLocation() {
        return vectorStorageLocation;
    }

    public void addVectorStorageLocation(SliceVectorItem vectorStorageLocation) {
        this.vectorStorageLocation.add(vectorStorageLocation);
    }

    public  void removeVectorStorageLocation(String vectorStorageLocation){
        this.vectorStorageLocation.remove(vectorStorageLocation);
    }

    public String getScanStorageLocation() {
        return scanStorageLocation;
    }

    public void setStorageLocation(String storageLocation) {
        this.scanStorageLocation = storageLocation;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
