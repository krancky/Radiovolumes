package bhouse.radiovolumes;

import android.content.Context;

/**
 * Created by megha on 15-03-06.
 */
public class MainPageItem {

  public String id;
  public String name;
  public String imageName;
  public boolean isFav;

  public int getImageResourceId(Context context) {
    return context.getResources().getIdentifier(this.imageName, "drawable", context.getPackageName());
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getImageName() {
    return imageName;
  }

  public void setImageName(String imageName) {
    this.imageName = imageName;
  }

  public boolean isFav() {
    return isFav;
  }

  public void setFav(boolean fav) {
    isFav = fav;
  }
}
