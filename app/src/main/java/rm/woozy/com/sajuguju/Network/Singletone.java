package rm.woozy.com.sajuguju.Network;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import rm.woozy.com.sajuguju.Application.MyApplication;

/**
 * Created by Md. Abdur Rahim on 14-Nov-15.
 */
public class Singletone {
    private static Singletone instance = null;
    private RequestQueue mRequestQueue;

    public Singletone() {
        mRequestQueue = Volley.newRequestQueue(MyApplication.getInstance().getContext());
    }

    public static Singletone getInstance(){
        if(instance==null){
            instance = new Singletone();
        }
        return instance;
    }

    public RequestQueue getmRequestQueue(){
        return mRequestQueue;
    }
}