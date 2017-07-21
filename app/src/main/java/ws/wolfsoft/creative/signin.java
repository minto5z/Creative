package ws.wolfsoft.creative;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ws.wolfsoft.creative.app.AppConfig;
import ws.wolfsoft.creative.helper.SQLiteHandler;
import ws.wolfsoft.creative.helper.SessionManager;

import static android.R.attr.data;
import static ws.wolfsoft.creative.app.AppConfig.LOGGEDIN_SHARED_PREF;
import static ws.wolfsoft.creative.app.AppConfig.SHARED_PREF_NAME;

public class signin extends AppCompatActivity {

    TextView create, sign;


    private static final String TAG = signup.class.getSimpleName();

    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

        //Keys for Sharedpreferences



    private boolean loggedIn = false;

    public static final String LOGIN_URL = "http://miniaccount.ftns-mbstu.com/login";

    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";


    Typeface fonts1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);



        final EditText etemail = (EditText) findViewById(R.id.email);
        final EditText etpassword = (EditText) findViewById(R.id.password);

        Intent intent = getIntent();

        if(intent.getStringExtra("email")!=""&&intent.getStringExtra("password")!="") {
            etemail.setText(intent.getStringExtra("email"));
            etpassword.setText(intent.getStringExtra("password"));
        }

        create = (TextView) findViewById(R.id.create);


        sign = (TextView) findViewById(R.id.signin1);
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etemail.getText().toString().trim();
                String password = etpassword.getText().toString().trim();

                // Check for empty data in the form
                if (!email.isEmpty() && !password.isEmpty()) {
                    // login user
                    checkLogin(email, password);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG)
                            .show();
                }

            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(signin.this, signup.class);
                startActivity(it);

            }
        });


        fonts1 = Typeface.createFromAsset(signin.this.getAssets(),
                "fonts/Lato-Regular.ttf");


        TextView t4 = (TextView) findViewById(R.id.create);
        t4.setTypeface(fonts1);
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
            Intent intent = new Intent(signin.this, FirstActivity.class);
            startActivity(intent);
        }
    }

    private void checkLogin(final String email, final String password) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(!response.trim().equals("success")){
                            //openProfile();
                            //Creating a shared preference
                            SharedPreferences sharedPreferences = signin.this.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

                            //Creating editor to store values to shared preferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            //Adding values to editor
                            editor.putBoolean(LOGGEDIN_SHARED_PREF, true);
                            editor.putString(AppConfig.EMAIL_SHARED_PREF, email);

                            Toast.makeText(signin.this,response +  "---------->>",Toast.LENGTH_LONG).show();
                            Log.d(TAG,response);

                            String id="",token="";

                            JSONObject json = null;
                            try {
                                json = new JSONObject(response);
                                id = json.getString( "user_id" );
                                token = json.getString( "api_token" );

                                Log.d(TAG,id + "--msdfsdf--" + token);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            editor.putString(AppConfig.USER_ID_SHARED_PREF, id);
                            editor.putString(AppConfig.API_TOKEN_SHARED_PREF, token);

                            //Saving values to editor
                            editor.commit();

                            //Starting profile activity
                            Intent intent = new Intent(signin.this, FirstActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(signin.this,response,Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(signin.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(KEY_EMAIL,email);
                map.put(KEY_PASSWORD,password);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}