package rm.woozy.com.sajuguju;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Moon on 11/9/2015.
 */
public class Singletone {
    private static Singletone instace = null;
    RequestQueue mRequestQueue;

    public Singletone() {
       mRequestQueue= Volley.newRequestQueue(MyApp.getObject().getContext());
    }

    public static Singletone getInstance() {
        if (instace == null) {
            instace = new Singletone();
        }
        return instace;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }
}
