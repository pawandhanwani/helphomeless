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

import java.util.HashMap;
import java.util.Map;

public class Otp extends AppCompatActivity {
    EditText otpField;
    Button submitButton;
    ProgressBar progressBar;
    void SignupUtil(final String email, final String type, final String name, final String pass)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://www.domelements.com/services/homeless/SignUp.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        //Log.d("Response", response);
                        progressBar.setProgress(100);
                        progressBar.setVisibility(progressBar.GONE);
                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(Otp.this,Main2Activity.class);
                        startActivity(intent1);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        //Log.d("Error.Response", error.getMessage());
                        progressBar.setProgress(100);
                        progressBar.setVisibility(progressBar.GONE);
                        Toast.makeText(getApplicationContext(),"Error sending otp.",Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("email", email);
                params.put("type",type);
                params.put("name",name);
                params.put("password",pass);
                return params;
            }
        };
        queue.add(postRequest);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        final Intent intent = getIntent();
        final int otp = (int) intent.getIntExtra("otp",0);
        final int type= (int) intent.getIntExtra("type",0);
        final String name = intent.getStringExtra("name");
        final String email = intent.getStringExtra("email");
        final String pass = intent.getStringExtra("pass");
        otpField = (EditText) findViewById(R.id.otp);
        progressBar = (ProgressBar) findViewById(R.id.progressBar5);
        progressBar.setVisibility(progressBar.GONE);
        submitButton = (Button) findViewById(R.id.confirm);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int eotp = Integer.parseInt(otpField.getText().toString());
                if(eotp==otp)
                {
                    progressBar.setProgress(0);
                    progressBar.setVisibility(progressBar.VISIBLE);
                    SignupUtil(email,""+type,name,pass);

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Incorrect otp.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
