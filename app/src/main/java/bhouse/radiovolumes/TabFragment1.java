package bhouse.radiovolumes;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;

public class TabFragment1 extends Fragment {

    private HashMap<String, HashMap<String, List<String>>> cancerTData;
    private HashMap<String, HashMap<String, List<String>>> cancerTTarData;
    private HashMap<String, List<String>> cancerNData;
    private HashMap<String, List<String>> cancerNTarData;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        TabbedActivity activity = (TabbedActivity) getActivity();
        cancerTData = new HashMap<String, HashMap<String, List<String>>>();
        cancerNData = new HashMap<String, List<String>>();

        cancerNData = activity.getCancerNData();
        cancerTData = activity.getCancerTData();
        Log.i("argh", cancerNData.toString());






        return inflater.inflate(R.layout.tab_fragment_1, container, false);
    }
}