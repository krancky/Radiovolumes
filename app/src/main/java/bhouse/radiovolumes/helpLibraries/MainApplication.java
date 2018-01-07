package bhouse.radiovolumes.helpLibraries;

/**
 * Created by kranck on 10/7/2017.
 */

import android.app.Application;
import android.content.Context;

import bhouse.radiovolumes.helpLibraries.LocaleHelper;


public class MainApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "en"));

    }
}