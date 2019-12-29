package com.example.helphomeless;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

public class AddRequest extends AppCompatActivity {
    TextView addressField;
    EditText gidnField;
    Button addButton;
    ProgressBar progressBar;
    void AddRequestUtil(final String sid, final String uid, final String aid, final String uname, final String aname, final String gidn)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://www.domelements.com/services/homeless/AddRequest.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        //Log.d("Response", response);
                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                        progressBar.setProgress(100);
                        progressBar.setVisibility(progressBar.GONE);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        //Log.d("Error.Response", response);
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                        progressBar.setProgress(100);
                        progressBar.setVisibility(progressBar.GONE);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("sid", sid);
                params.put("uid", uid);
                params.put("aid", aid);
                params.put("uname", uname);
                params.put("aname",aname);
                params.put("gidn",gidn);
                //params.put("domain", "http://itsalif.info");

                return params;
            }
        };
        queue.add(postRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_request);
        Intent intent = getIntent();
        progressBar = (ProgressBar) findViewById(R.id.progressBar9);
        progressBar.setProgress(0);
        progressBar.setVisibility(progressBar.GONE);
        final String uname = intent.getStringExtra("uname");
        final String aname = intent.getStringExtra("aname");
        final int sid = (int) intent.getIntExtra("sid",0);
        final int uid = (int) intent.getIntExtra("uid",0);
        final int aid = (int) intent.getIntExtra("aid",0);
        String address = intent.getStringExtra("address");


        addressField = (TextView) findViewById(R.id.address);
        addressField.setText("Your request is for - "+address);
        gidnField = (EditText) findViewById(R.id.gidn);
        addButton = (Button) findViewById(R.id.add);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setProgress(0);
                progressBar.setVisibility(progressBar.VISIBLE);
                String gidn = gidnField.getText().toString();
                AddRequestUtil(""+sid,""+uid,""+aid,""+uname,""+aname,gidn);
            }
        });

    }
}
