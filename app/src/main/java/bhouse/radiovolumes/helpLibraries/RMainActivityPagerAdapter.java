package bhouse.radiovolumes.helpLibraries;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import bhouse.radiovolumes.TabFragment1;
import bhouse.radiovolumes.TabFragment2;
import bhouse.radiovolumes.rectum.RTabFragment1;
import bhouse.radiovolumes.rectum.RTabFragment2;

public class RMainActivityPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public RMainActivityPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                RTabFragment1 tab1 = new RTabFragment1();
                return tab1;
            case 1:
                RTabFragment2 tab2 = new RTabFragment2();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}