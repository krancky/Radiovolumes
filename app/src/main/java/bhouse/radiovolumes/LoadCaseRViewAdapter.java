package bhouse.radiovolumes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.List;
import java.util.Locale;

import bhouse.radiovolumes.processor.Cancer;

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
        CancerViewHolder cvh = new CancerViewHolder(this.cancerCases, v);
        return cvh;
    }

    @Override
    public void onBindViewHolder(CancerViewHolder cancerViewHolder, int i){
        final Cancer cancer = cancerCases.get(i);
        cancerViewHolder.name.setText(cancerCases.get(i).getName());
        String[] mainAreaArray = mContext.getResources().getStringArray(R.array.main_areas_array);
        String[] mainSideArray = mContext.getResources().getStringArray(R.array.main_side_array);
        cancerViewHolder.main_area.setText(mainAreaArray[Integer.valueOf(cancerCases.get(i).getMainArea())] + " (" + mainSideArray[Integer.valueOf(cancerCases.get(i).getMainSide())] + ")");

        //java.text.DateFormat dateFormat =
        DateFormat f = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.getDefault());
        String date = f.format(cancerCases.get(i).getTime());
        cancerViewHolder.date.setText(date);
        int truc = cancerCases.get(i).getImageResourceId(mContext);
        cancerViewHolder.case_photo.setImageResource(cancerCases.get(i).getImageResourceId(mContext));
        cancerViewHolder.cv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(mContext, NewCaseActivity.class);
                //transitionIntent.putExtra(DetailActivity.EXTRA_PARAM_ID, position);
                intent.putExtra("cancer", cancer);
                intent.putExtra("newParam", "0");
                mContext.startActivity(intent);

            }
        });


   }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static  class CancerViewHolder extends  RecyclerView.ViewHolder{
        CardView cv;
        TextView name;
        TextView main_area;
        TextView date;
        ImageView case_photo;
        List<Cancer> cancers;

        public CancerViewHolder(List<Cancer> cancers, View itemView){
            super(itemView);
            this.cancers = cancers;
            cv =(CardView)itemView.findViewById(R.id.load_card);
            name =(TextView) itemView.findViewById(R.id.card_name);
            main_area = (TextView) itemView.findViewById(R.id.card_main_area);
            case_photo = (ImageView)itemView.findViewById(R.id.card_photo);
            date = (TextView)itemView.findViewById(R.id.create_date);
        }
    }
}
