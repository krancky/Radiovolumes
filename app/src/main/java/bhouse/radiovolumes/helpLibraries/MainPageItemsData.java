package bhouse.radiovolumes.helpLibraries;

import android.content.Context;

import java.util.ArrayList;

import bhouse.radiovolumes.R;

/**
 * Created by megha on 15-03-06.
 */
public class MainPageItemsData {

  private ArrayList<String> placeNameArray;

  public MainPageItemsData(Context context){
    this.placeNameArray = new ArrayList<>();
    String newCase = context.getString(R.string.newCase);
    String load = context.getString(R.string.load);
    String atlas = context.getString(R.string.atlas);
    String rectumcontour = context.getString(R.string.rectumcontour);
    this.placeNameArray.add(newCase);
    this.placeNameArray.add(load);
    this.placeNameArray.add(atlas);
    this.placeNameArray.add(rectumcontour);

  }

  //public String[] placeNameArray = {"New case", "Load", "Test"};

  public ArrayList<MainPageItem> placeList() {
    ArrayList<MainPageItem> list = new ArrayList<>();
    for (int i = 0; i < placeNameArray.size(); i++) {
      MainPageItem mainPageItem = new MainPageItem();
      mainPageItem.name = placeNameArray.get(i);
      mainPageItem.imageName = placeNameArray.get(i).replaceAll("\\s+", "").toLowerCase();
      if (i == 2 || i == 5) {
        mainPageItem.isFav = true;
      }
      list.add(mainPageItem);
    }
    return (list);
  }

  public MainPageItem getItem(String _id) {
    for (MainPageItem mainPageItem : placeList()) {
      if (mainPageItem.id.equals(_id)) {
        return mainPageItem;
      }
    }
    return null;
  }
}