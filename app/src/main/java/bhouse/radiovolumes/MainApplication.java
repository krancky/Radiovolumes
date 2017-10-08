package bhouse.radiovolumes;

/**
 * Created by kranck on 10/7/2017.
 */

import android.app.Application;
import android.content.Context;

        import android.app.Application;
        import android.content.Context;


public class MainApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "fr"));

    }
}