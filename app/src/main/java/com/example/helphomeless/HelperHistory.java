package com.example.helphomeless;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

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

public class HelperHistory extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<HHlist> myitems;
    ProgressBar progressBar;
    void LoadSpacePosterHistoryUtil(final String uid)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://www.domelements.com/services/homeless/ViewSpaceByPoster.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        try {
                            JSONArray JA = new JSONArray(response);
                            for(int i=0;i<JA.length();i++)
                            {
                                JSONObject JO= (JSONObject) JA.get(i);
                                if(JO.getInt("status")==1) {
                                    HHlist pitem = new HHlist(JO.getString("photoloc"), JO.getString("address"), JO.getString("date"), JO.getString("aname"), "Un-Allocated");
                                    myitems.add(pitem);
                                }
                                else if(JO.getInt("status")==2)
                                {
                                    HHlist pitem = new HHlist(JO.getString("photoloc"), JO.getString("address"), JO.getString("date"), JO.getString("aname"), "Allocated");
                                    myitems.add(pitem);
                                }

                            }
                            progressBar.setProgress(100);
                            progressBar.setVisibility(progressBar.GONE);
                            adapter = new HHadapter(HelperHistory.this,myitems);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
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
                        progressBar.setProgress(100);
                        progressBar.setVisibility(progressBar.GONE);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("uid", uid);


                return params;
            }
        };
        queue.add(postRequest);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helper_history);
        final Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        final String userhash = intent.getStringExtra("userhash");
        final int myid = (int) intent.getIntExtra("myid",0);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressBar = (ProgressBar) findViewById(R.id.progressBar8);
        progressBar.setProgress(0);
        progressBar.setVisibility(progressBar.VISIBLE);

        myitems = new ArrayList<>();
        /*for(int i=1;i<=100;i++)
        {
            HHlist pitem= new HHlist("http://www.domelements.com/services/homeless/uploads/5dc294dc0fede018d0a960c3a5576921.jpeg","Kuch bhi","28-12-2019","name","Pendo");
            myitems.add(pitem);
        }

        adapter = new HHadapter(HelperHistory.this,myitems);

        recyclerView.setAdapter(adapter);
         */
        LoadSpacePosterHistoryUtil(""+myid);
    }
}
