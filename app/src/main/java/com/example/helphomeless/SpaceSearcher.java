package com.example.helphomeless;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SearchView;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpaceSearcher extends AppCompatActivity {
    //SearchView searchView;
    EditText queryField;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<SAList> myitems;
    ProgressBar progressBar;
    void LoadSpacesUtil(final String date, final String queryStr, final int aid, final String aname)
    {
        myitems.clear();
        if(queryStr.length()<4)
        {
            progressBar.setProgress(100);
            progressBar.setVisibility(progressBar.GONE);
            adapter = new SAadapter(myitems,SpaceSearcher.this);
            recyclerView.setAdapter(adapter);
            return;
        }
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://www.domelements.com/services/homeless/ViewSpaceForAcquirer.php";
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
                                SAList item = new SAList(JO.getInt("id"),JO.getInt("uid"),aid,JO.getString("uname"),aname ,JO.getString("photoloc"),JO.getString("address"));
                                myitems.add(item);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        progressBar.setProgress(100);
                        progressBar.setVisibility(progressBar.GONE);
                        adapter = new SAadapter(myitems,SpaceSearcher.this);
                        recyclerView.setAdapter(adapter);
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
                params.put("date", date);
                params.put("queryStr", queryStr);

                return params;
            }
        };
        queue.add(postRequest);


    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space_searcher);

        final Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        final String userhash = intent.getStringExtra("userhash");
        final int myid = (int) intent.getIntExtra("myid",0);

        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        //progressBar.setProgress(0);
        progressBar.setVisibility(progressBar.GONE);
        queryField = (EditText) findViewById(R.id.query);
        String pattern = "dd-MM-yyyy";
        final String dateInString =new SimpleDateFormat(pattern).format(new Date());
        queryField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                progressBar.setProgress(0);
                progressBar.setVisibility(progressBar.VISIBLE);
                LoadSpacesUtil(dateInString,text,myid,name);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myitems = new ArrayList<>();
        //RecyclerViewUtil();
    }
}
