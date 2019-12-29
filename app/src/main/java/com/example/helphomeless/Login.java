package com.example.helphomeless;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

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

public class Login extends AppCompatActivity {
    EditText emailField,passField;
    Button loginButton;
    ProgressBar progressBar;
    void type1Login(String name, String userhash, int myid)
    {
        Intent intent = new Intent(Login.this,helpermenu.class);
        intent.putExtra("name",name);
        intent.putExtra("userhash",userhash);
        intent.putExtra("myid",myid);
        startActivity(intent);
    }
    void type2login(String name, String userhash, int myid)
    {
        Intent intent = new Intent(Login.this,homelessmenu.class);
        intent.putExtra("name",name);
        intent.putExtra("userhash",userhash);
        intent.putExtra("myid",myid);
        startActivity(intent);
    }
    void loginUtil(final String email, final String password)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://www.domelements.com/services/homeless/Login.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        //Log.d("Response", response);
                        //Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                        try {
                            JSONArray JA = new JSONArray(response);
                            //Toast.makeText(getApplicationContext(),"..NO error",Toast.LENGTH_LONG).show();
                            for(int i=0;i<JA.length();i++)
                            {
                                JSONObject JO= (JSONObject) JA.get(i);
                                //Toast.makeText(getApplicationContext(),"..NO error",Toast.LENGTH_LONG).show();
                                String name = JO.getString("name");
                                String userhash = JO.getString("username");
                                int myid = JO.getInt("id");
                                int type = JO.getInt("type");
                                progressBar.setProgress(100);
                                progressBar.setVisibility(progressBar.GONE);
                                if(type==1)
                                {
                                    type1Login(name,userhash,myid);
                                }
                                else
                                {
                                    type2login(name,userhash,myid);
                                }
                                break;
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),"..ERROR",Toast.LENGTH_LONG).show();
                            progressBar.setProgress(100);
                            progressBar.setVisibility(progressBar.GONE);
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        //Log.d("Error.Response", error.getMessage());
                        Toast.makeText(getApplicationContext(),"Error sending otp.",Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password",password);


                return params;
            }
        };
        queue.add(postRequest);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailField = (EditText) findViewById(R.id.email);
        passField = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.button);
        progressBar = (ProgressBar) findViewById(R.id.progressBar3);
        progressBar.setVisibility(progressBar.GONE);
        //intent = new Intent(Login.this,Main2Activity.class);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailField.getText().toString();
                String pass = passField.getText().toString();
                //intent.putExtra("email",email);
                progressBar.setProgress(0);
                progressBar.setVisibility(progressBar.VISIBLE);
                //Toast.makeText(getApplicationContext(),"login hit",Toast.LENGTH_LONG).show();
                loginUtil(email,pass);
            }
        });

    }
}
