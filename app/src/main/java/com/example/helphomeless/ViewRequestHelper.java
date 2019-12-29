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

public class ViewRequestHelper extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<VRPList> myitems;
    ProgressBar progressBar;
    void LoadReqPosUtil(final String uid)
    {
        myitems.clear();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://www.domelements.com/services/homeless/ViewRequestPoster.php";
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
                                    VRPList item = new VRPList(JO.getInt("id"), JO.getInt("sid"),JO.getString("aname"),"Pending");
                                    myitems.add(item);
                                }
                                else if(JO.getInt("status")==2) {
                                    VRPList item = new VRPList(JO.getInt("id"), JO.getInt("sid"),JO.getString("aname") ,"Approved");
                                    myitems.add(item);
                                }
                                else if(JO.getInt("status")==3) {
                                    VRPList item = new VRPList(JO.getInt("id"), JO.getInt("sid"), JO.getString("aname"),"Rejected");
                                    myitems.add(item);
                                }
                                //Toast.makeText(getApplicationContext(),"hi",Toast.LENGTH_SHORT).show();
                            }
                            //Toast.makeText(getApplicationContext(),""+myitems.size(),Toast.LENGTH_SHORT).show();
                            adapter = new VRPAdapter(myitems,ViewRequestHelper.this);
                            recyclerView.setAdapter(adapter);
                            progressBar.setProgress(100);
                            progressBar.setVisibility(progressBar.GONE);
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),"errooo",Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_view_request_helper);
        progressBar = (ProgressBar) findViewById(R.id.progressBar6);
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
        LoadReqPosUtil(""+myid);
    }
}
