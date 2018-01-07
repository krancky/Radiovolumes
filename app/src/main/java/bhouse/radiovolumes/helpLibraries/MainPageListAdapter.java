package bhouse.radiovolumes.helpLibraries;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import bhouse.radiovolumes.R;

/**
 * Created by megha on 15-03-06.
 */
public class MainPageListAdapter extends RecyclerView.Adapter<MainPageListAdapter.ViewHolder> {

  Context mContext;
  OnItemClickListener mItemClickListener;

  public MainPageListAdapter(Context context) {
    this.mContext = context;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_main, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, final int position) {
    final MainPageItem mainPageItem = new MainPageItemsData(mContext).placeList().get(position);

    holder.placeName.setText(mainPageItem.name);
    Picasso.with(mContext).load(mainPageItem.getImageResourceId(mContext)).into(holder.placeImage);
    holder.placeNameHolder.setBackgroundColor(mContext.getResources().getColor(R.color.primary));
    Bitmap photo = BitmapFactory.decodeResource(mContext.getResources(), mainPageItem.getImageResourceId(mContext));

    Palette.generateAsync(photo, new Palette.PaletteAsyncListener() {
      public void onGenerated(Palette palette) {
        int mutedLight = palette.getMutedColor(mContext.getResources().getColor(android.R.color.black));
        //holder.placeNameHolder.setBackgroundColor(mutedLight);
        //holder.placeNameHolder.setBackgroundColor(mContext.getResources().getColor(R.color.primary));
      }
    });
  }

  @Override
  public int getItemCount() {
    return new MainPageItemsData(mContext).placeList().size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public LinearLayout placeHolder;
    public LinearLayout placeNameHolder;
    public TextView placeName;
    public ImageView placeImage;

    public ViewHolder(View itemView) {
      super(itemView);
      placeHolder = (LinearLayout) itemView.findViewById(R.id.mainHolder);
      placeName = (TextView) itemView.findViewById(R.id.placeName);
      placeNameHolder = (LinearLayout) itemView.findViewById(R.id.placeNameHolder);
      placeImage = (ImageView) itemView.findViewById(R.id.placeImage);
      placeHolder.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
      if (mItemClickListener != null) {
        mItemClickListener.onItemClick(itemView, getPosition());
      }
    }
  }

  public interface OnItemClickListener {
    void onItemClick(View view, int position);
  }

  public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
    this.mItemClickListener = mItemClickListener;
  }

}