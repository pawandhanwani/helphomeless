package com.example.helphomeless;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AcqHistory extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<AVRList> myitems;
    ProgressBar progressBar;

    void LoadReqAcqUtil(final String aid)
    {
        myitems.clear();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://www.domelements.com/services/homeless/ViewRequestAcquirer.php";
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
                            for(int i=0;i<JA.length();i++)
                            {
                                JSONObject JO = (JSONObject) JA.get(i);
                                if(JO.getInt("status")==1) {
                                    AVRList item = new AVRList(JO.getInt("id"), "Pending");
                                    myitems.add(item);
                                }
                                else if(JO.getInt("status")==2) {
                                    AVRList item = new AVRList(JO.getInt("id"), "Accepted");
                                    myitems.add(item);
                                }
                                else if(JO.getInt("status")==3) {
                                    AVRList item = new AVRList(JO.getInt("id"), "Rejected");
                                    myitems.add(item);
                                }
                                //Toast.makeText(getApplicationContext(),"hi",Toast.LENGTH_SHORT).show();
                            }
                            //Toast.makeText(getApplicationContext(),""+myitems.size(),Toast.LENGTH_SHORT).show();
                            progressBar.setProgress(100);
                            progressBar.setVisibility(progressBar.GONE);
                            adapter = new VRAadapter(myitems,AcqHistory.this);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),"errooo",Toast.LENGTH_SHORT).show();
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
                        //Log.d("Error.Response", response);
                        Toast.makeText(getApplicationContext(),"Server Error",Toast.LENGTH_SHORT).show();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("aid", aid);

                return params;
            }
        };
        queue.add(postRequest);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acq_history);
        progressBar = (ProgressBar) findViewById(R.id.progressBar7);
        progressBar.setProgress(0);
        progressBar.setVisibility(progressBar.VISIBLE);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        final String userhash = intent.getStringExtra("userhash");
        final int myid = (int) intent.getIntExtra("myid",0);
        myitems = new ArrayList<>();
        LoadReqAcqUtil(""+myid);

    }
}
