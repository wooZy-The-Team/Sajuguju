package rm.woozy.com.sajuguju;

import android.app.Application;
import android.content.Context;

/**
 * Created by Moon on 11/9/2015.
 */
public class MyApp extends Application {
    private static MyApp myApp;

    public static MyApp getObject(){
        return myApp;
    }

    public Context getContext(){
        return myApp.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApp=this;
    }
}
