package ws.wolfsoft.creative;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import ws.wolfsoft.creative.app.AppConfig;
import ws.wolfsoft.creative.helper.SQLiteHandler;
import ws.wolfsoft.creative.helper.SessionManager;


public class signup extends AppCompatActivity {

    TextView signinhere;
    TextView signpu1;
    EditText username,email,password;

    private static final String TAG = signup.class.getSimpleName();
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;



    private static final String REGISTER_URL = "http://miniaccount.ftns-mbstu.com/register";

    public static final String KEY_USERNAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";


    Typeface fonts1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        signpu1 = (TextView)findViewById(R.id.signup1);
        username = (EditText) findViewById(R.id.username);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);

        signpu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name1 = username.getText().toString().trim();
                String email1 = email.getText().toString().trim();
                String password1 = password.getText().toString().trim();

                if (!name1.isEmpty() && !email1.isEmpty() && !password1.isEmpty()) {


                    registerUser(name1,email1,password1);

                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });





        signinhere = (TextView)findViewById(R.id.signinhere);

        signinhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(signup.this, signin.class);
                startActivity(it);


            }
        });



        fonts1 =  Typeface.createFromAsset(signup.this.getAssets(),
                "fonts/Lato-Regular.ttf");




        TextView t1 =(TextView)findViewById(R.id.signinhere);
        t1.setTypeface(fonts1);

    }

    private void registerUser(final String name, final String email,
                              final String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(signup.this,response,Toast.LENGTH_LONG).show();
                        Intent i = new Intent(signup.this,signin.class);
                        i.putExtra(KEY_EMAIL, email);
                        i.putExtra(KEY_PASSWORD, password);
                        startActivity(i);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(signup.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_USERNAME,name);
                params.put(KEY_EMAIL, email);
                params.put(KEY_PASSWORD,password);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}