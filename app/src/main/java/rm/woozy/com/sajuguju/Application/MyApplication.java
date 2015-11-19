package rm.woozy.com.sajuguju.Application;

import android.app.Application;
import android.content.Context;

/**
 * Created by Md. Abdur Rahim on 14-Nov-15.
 */
public class MyApplication extends Application{
    private static MyApplication instance;

    public static MyApplication getInstance(){
        return  instance;
    }

    public Context getContext(){
        return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
