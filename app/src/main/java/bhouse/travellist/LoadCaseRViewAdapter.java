package bhouse.travellist;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import bhouse.travellist.processor.Cancer;

/**
 * Created by kranck on 7/25/2017.
 */

public class LoadCaseRViewAdapter extends RecyclerView.Adapter<LoadCaseRViewAdapter.CancerViewHolder> {
    List<Cancer> cancerCases;
    Context mContext;

    LoadCaseRViewAdapter(List<Cancer> cancerCases, Context context){
        this.cancerCases = cancerCases;
        this.mContext = context;
    }

    @Override
    public int getItemCount(){
        return cancerCases.size();
    }

    @Override
    public CancerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_load_case, viewGroup, false); // plutot cardtruc??
        CancerViewHolder cvh = new CancerViewHolder(v);
        return cvh;
    }

    @Override
    public void onBindViewHolder(CancerViewHolder cancerViewHolder, int i){
        cancerViewHolder.name.setText(cancerCases.get(i).getName());
        cancerViewHolder.main_area.setText(cancerCases.get(i).getMainArea());
        cancerViewHolder.case_photo.setImageResource(cancerCases.get(i).getImageResourceId(mContext));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static  class CancerViewHolder extends  RecyclerView.ViewHolder{
        CardView cv;
        TextView name;
        TextView main_area;
        ImageView case_photo;

        CancerViewHolder(View itemView){
            super(itemView);
            cv =(CardView)itemView.findViewById(R.id.load_card);
            name =(TextView) itemView.findViewById(R.id.card_name);
            main_area = (TextView) itemView.findViewById(R.id.card_main_area);
        }
    }
}
