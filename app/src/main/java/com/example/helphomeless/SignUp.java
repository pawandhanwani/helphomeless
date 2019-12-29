package com.example.helphomeless;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SignUp extends AppCompatActivity {
    EditText emailText,passText,cpassText,nameText;
    Button SignupButton;
    Spinner spinner;
    ProgressBar progressBar;
    void otpUtil(final String email, final String otp)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://www.domelements.com/services/homeless/OtpService.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        //Log.d("Response", response);
                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                        progressBar.setProgress(100);
                        progressBar.setVisibility(passText.GONE);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        //Log.d("Error.Response", error.getMessage());
                        Toast.makeText(getApplicationContext(),"Error sending otp.",Toast.LENGTH_SHORT).show();
                        progressBar.setProgress(100);
                        progressBar.setVisibility(passText.GONE);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("email", email);
                params.put("otp",otp);

                return params;
            }
        };
        queue.add(postRequest);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        emailText = (EditText) findViewById(R.id.email);
        nameText = (EditText) findViewById(R.id.name);
        passText = (EditText) findViewById(R.id.password);
        cpassText = (EditText) findViewById(R.id.cpassword);
        spinner = (Spinner) findViewById(R.id.spinner);
        progressBar = (ProgressBar) findViewById(R.id.progressBar4);
        progressBar.setProgress(0);
        progressBar.setVisibility(passText.GONE);
        String[] arraySpinner = new String[] {
                "I want to help homeless", "I am Homeless"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        SignupButton = (Button) findViewById(R.id.signup);
        SignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=nameText.getText().toString();
                String email = emailText.getText().toString();
                String pass = passText.getText().toString();
                String cpass = cpassText.getText().toString();
                String spintext = spinner.getSelectedItem().toString();
                progressBar.setProgress(0);
                progressBar.setVisibility(passText.VISIBLE);
                int type=0;
                if(spintext.equals("I want to help homeless"))
                {
                    type=1;
                }
                else
                {
                    type=2;
                }
                int otp = new Random().nextInt((999999 - 100000) + 1) + 100000;
                Intent intent = new Intent(SignUp.this,Otp.class);
                intent.putExtra("name",name);
                intent.putExtra("email",email);
                intent.putExtra("pass",pass);
                intent.putExtra("type",type);
                intent.putExtra("otp",otp);
                if(pass.equals(cpass))
                {
                    otpUtil(email,""+otp);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Passwords does not match.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
