package rm.woozy.com.sajuguju.Preference;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import rm.woozy.com.sajuguju.MainActivity;
import rm.woozy.com.sajuguju.Others.Constants;

/**
 * Created by Md. Abdur Rahim on 19-Nov-15.
 */
public class SharedPref {
    SharedPreferences sharedPreference;
    Context context;

    public SharedPref(Context context) {
        this.context = context;
        sharedPreference = context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);

    }

    public boolean saveToSharedPreference(JSONObject response, String temp_token) {
        if (response != null) {
            try {
                String firstName = response.getString(Constants.FIRST_NAME).toString();
                String lastName = response.getString(Constants.LAST_NAME).toString();
                String email = response.getString(Constants.EMAIL).toString();
                String address = response.getString(Constants.ADDRESS).toString();
                String userId = response.getString(Constants.USER_ID).toString();
                String contactNo = response.getString(Constants.CONTACT_NO).toString();
                String fullName = firstName + " " + lastName;

                SharedPreferences.Editor editor = sharedPreference.edit();
                editor.putString(Constants.SHARED_FULL_NAME, fullName);
                editor.putString(Constants.SHARED_EMAIL, email);
                editor.putString(Constants.SHARED_ADDRESS, address);
                editor.putString(Constants.SHARED_USER_ID, userId);
                editor.putString(Constants.SHARED_CONTACT_NO, contactNo);
                editor.putString(Constants.SHARED_TOKEN, temp_token);
                editor.commit();
                return true;
            } catch (JSONException e) {
                Log.d("MyApp", e.toString());
                return false;
            }
        } else {
            Log.d("MyApp", "Not Saved to shared Pref");
            return false;
        }
    }

    public boolean isLogin(){
        boolean available = false;
        String token = sharedPreference.getString(Constants.SHARED_TOKEN, "");
        if(token!=""){
            available = true;
        }
        return available;
    }

    public boolean logout(){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.clear();
        editor.commit();
        return true;
    }
}
