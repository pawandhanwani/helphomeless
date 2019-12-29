package com.example.helphomeless;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class AddSpace extends AppCompatActivity {

    Button choose,add;
    EditText addressField;
    ImageView imageView;
    Bitmap bitmap;
    ProgressBar progressBar;
    public String imageToString(Bitmap bitmap)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        byte[] imageBytes = outputStream.toByteArray();

        String encodedString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedString;

    }

    public void AddSpaceUtil(final String imagedata, final String myid, final String name, final String address, final String date)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://www.domelements.com/services/homeless/AddSpace.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        //Log.d("Response", response);
                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();

                        progressBar.setProgress(100);
                        progressBar.setVisibility(ProgressBar.GONE);
                        //progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        //Log.d("Error.Response", response);
                        //progressBar.setProgress(100);
                        progressBar.setVisibility(ProgressBar.GONE);
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                        //progressDialog.dismiss();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                //String imagedata = imageToString(bitmap);
                params.put("photo", imagedata);
                params.put("uid",myid);
                params.put("uname",name);
                params.put("date",date);
                params.put("address",address);
                return params;
            }
        };
        queue.add(postRequest);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_space);
        String pattern = "dd-MM-yyyy";
        final String dateInString =new SimpleDateFormat(pattern).format(new Date());
        //Toast.makeText(getApplicationContext(),dateInString,Toast.LENGTH_SHORT).show();*/

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(progressBar.GONE);
        choose = (Button) findViewById(R.id.choose);
        add = (Button) findViewById(R.id.add);
        addressField = (EditText) findViewById(R.id.address);
        imageView = (ImageView) findViewById(R.id.imageView);

        final Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        final String userhash = intent.getStringExtra("userhash");
        final int myid = (int) intent.getIntExtra("myid",0);

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(AddSpace.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},999);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imagedata = imageToString(bitmap);
                String address = addressField.getText().toString();
                AddSpaceUtil(imagedata,""+myid,name,address,dateInString);
                progressBar.setVisibility(ProgressBar.VISIBLE);
                progressBar.setProgress(0);
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 999)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent ,"Select"),999);
            }
            else
            {
                Toast.makeText(getApplicationContext(),"You didn't grant permisiion",Toast.LENGTH_LONG).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if((requestCode == 999) && (resultCode == RESULT_OK  )&& (data!=null))
        {
            Uri filepath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
