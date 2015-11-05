package rm.woozy.com.sajuguju;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        Button loginbutton = (Button) findViewById(R.id.loginButton);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Comment by moon
    }

    public void signIn(View view){
        Intent intent = new Intent(LoginScreen.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
