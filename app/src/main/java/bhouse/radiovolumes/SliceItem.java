package bhouse.radiovolumes;

/**
 * Created by kranck on 8/3/2017.
 */

public class SliceItem {
    private String storageLocation;
    private String number;
    private String vectorStorageLocation;

    public String getVectorStorageLocation() {
        return vectorStorageLocation;
    }

    public void setVectorStorageLocation(String vectorStorageLocation) {
        this.vectorStorageLocation = vectorStorageLocation;
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
