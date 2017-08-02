package bhouse.radiovolumes;

import java.util.ArrayList;

/**
 * Created by megha on 15-03-06.
 */
public class MainPageItemsData {

  public static String[] placeNameArray = {"New case", "Load", "Test"};

  public static ArrayList<MainPageItem> placeList() {
    ArrayList<MainPageItem> list = new ArrayList<>();
    for (int i = 0; i < placeNameArray.length; i++) {
      MainPageItem mainPageItem = new MainPageItem();
      mainPageItem.name = placeNameArray[i];
      mainPageItem.imageName = placeNameArray[i].replaceAll("\\s+", "").toLowerCase();
      if (i == 2 || i == 5) {
        mainPageItem.isFav = true;
      }
      list.add(mainPageItem);
    }
    return (list);
  }

  public static MainPageItem getItem(String _id) {
    for (MainPageItem mainPageItem : placeList()) {
      if (mainPageItem.id.equals(_id)) {
        return mainPageItem;
      }
    }
    return null;
  }
}
