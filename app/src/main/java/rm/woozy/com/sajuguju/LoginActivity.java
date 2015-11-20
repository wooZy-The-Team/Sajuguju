package rm.woozy.com.sajuguju;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import rm.woozy.com.sajuguju.Network.Singletone;
import rm.woozy.com.sajuguju.Others.Constants;
import rm.woozy.com.sajuguju.Preference.SharedPref;

public class LoginActivity extends AppCompatActivity {
    EditText emailEditText, passwordEditText;
    Button loginButton;
    String temp_token = "";
    RelativeLayout progerss;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialize();       // Initialize

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = getEmail();
                String password = getPassword();

                if ((email.length() > 0) && (password.length() > 0)) {
                    SaveAndLogin saveAndLogin = new SaveAndLogin(email, password);
                    saveAndLogin.execute();
                }
            }
        });
    }

    private String getEmail() {
        String email = emailEditText.getText().toString();
        if (email.length() > 0) {
            return email;
        }
        return "";
    }

    private String getPassword() {
        String password = passwordEditText.getText().toString();
        if (password.length() > 0) {
            return password;
        }
        return "";
    }

    // Initialize all components
    private void initialize() {
        emailEditText = (EditText) findViewById(R.id.email);
        passwordEditText = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.loginButton);
        progerss = (RelativeLayout) findViewById(R.id.progressBar);
    }

    private String getToken(String Email, String Pass) {
        String token = "";
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        RequestQueue requestQueue = new Singletone().getmRequestQueue();
        String tokenCode = "grant_type=password&username=" + Email + "&password=" + Pass;

        JsonObjectRequest tokenRequest = new JsonObjectRequest(Request.Method.POST, Constants.TOKEN_URL, tokenCode, future, future);
        requestQueue.add(tokenRequest);
        try {
            JSONObject response = future.get(15, TimeUnit.SECONDS);
            Log.d("MyApp", response.toString());
            token = response.getString("access_token");
        } catch (InterruptedException e) {
            Log.d("MyApp", e.toString());
        } catch (ExecutionException e) {
            Log.d("MyApp", e.toString());
        } catch (TimeoutException e) {
            Log.d("MyApp", e.toString());
        } catch (JSONException e) {
            Log.d("MyApp", e.toString());
        } finally {
            return token;
        }
    }

    private JSONObject getDetail(final String token, String email) {
        final String temp_token = "bearer " + token;
        JSONObject uDetails = new JSONObject();

        RequestQueue requestQueue = new Singletone().getmRequestQueue();
        JSONObject param = new JSONObject();
        try {
            param.put("Email", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JsonObjectRequest userDetail = new JsonObjectRequest(Request.Method.POST, Constants.USER_DETAIL_URL, param, future, future) {

            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("authorization", temp_token);
                return header;
            }
        };
        requestQueue.add(userDetail);

        try {
            JSONObject response = future.get(15, TimeUnit.SECONDS);
            Log.d("MyApp", response.toString());
            uDetails = response;
        } catch (InterruptedException e) {
            Log.d("MyApp", e.toString());
        } catch (ExecutionException e) {
            Log.d("MyApp", e.toString());
        } catch (TimeoutException e) {
            Log.d("MyApp", e.toString());
        } finally {
            return uDetails;
        }
    }

    class SaveAndLogin extends AsyncTask<Void, Void, Boolean> {
        private String email, password;

        public SaveAndLogin(String email, String password) {
            this.email = email;
            this.password = password;
        }

        @Override
        protected void onPreExecute() {
            progerss.setVisibility(View.VISIBLE);

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean success = false;
            String token = getToken(email, password);
            if (token != "") {
                JSONObject userDetails = getDetail(token, email);
                if (userDetails != null) {
                    SharedPref save = new SharedPref(LoginActivity.this);
                    if (save.saveToSharedPreference(userDetails, token)) {
                        success = true;
                    }
                }
            }
            return success;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            progerss.setVisibility(View.GONE);
            if (aBoolean) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
