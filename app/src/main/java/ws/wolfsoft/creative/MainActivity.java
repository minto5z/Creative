package ws.wolfsoft.creative;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import ws.wolfsoft.creative.app.AppConfig;
import ws.wolfsoft.creative.helper.SQLiteHandler;
import ws.wolfsoft.creative.helper.SessionManager;

import static ws.wolfsoft.creative.R.id.textView;

public class MainActivity extends AppCompatActivity {
    TextView signin;
    TextView signup;
    private SQLiteHandler db;
    private SessionManager session;
    private boolean loggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        signin = (TextView)findViewById(R.id.signin);
        signup = (TextView)findViewById(R.id.Signup);






        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(MainActivity.this, signup.class);
                startActivity(it);

            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(MainActivity.this,signin.class);
                startActivity(it);


            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences = getSharedPreferences(AppConfig.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences.getBoolean(AppConfig.LOGGEDIN_SHARED_PREF, false);

        //If we will get true
        if(loggedIn){
            //We will start the Profile Activity
            Intent intent = new Intent(MainActivity.this, FirstActivity.class);
            //Fetching email from shared preferences
            SharedPreferences sharedPreferences1 = getSharedPreferences(AppConfig.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            String email = sharedPreferences1.getString(AppConfig.EMAIL_SHARED_PREF,"Not Available");
            String id = sharedPreferences1.getString(AppConfig.USER_ID_SHARED_PREF,"Not Available");
            String token = sharedPreferences1.getString(AppConfig.API_TOKEN_SHARED_PREF,"Not Available");

            Log.d("MainActivity.this",email + "-----id-----"+ id + "-----token------"+token);
            startActivity(intent);
        }
    }

    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, signin.class);
        startActivity(intent);
        finish();
    }
}
