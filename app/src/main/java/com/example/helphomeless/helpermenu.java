package com.example.helphomeless;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class helpermenu extends AppCompatActivity {
    Button add,request,history;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpermenu);
        add = (Button) findViewById(R.id.add);
        request = (Button) findViewById(R.id.requests);
        history = (Button) findViewById(R.id.history);
        final Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        final String userhash = intent.getStringExtra("userhash");
        final int myid = (int) intent.getIntExtra("myid",0);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(helpermenu.this,AddSpace.class);
                intent1.putExtra("name",name);
                intent1.putExtra("userhash",userhash);
                intent1.putExtra("myid",myid);
                startActivity(intent1);
            }
        });

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(helpermenu.this, ViewRequestHelper.class);
                intent1.putExtra("name",name);
                intent1.putExtra("userhash",userhash);
                intent1.putExtra("myid",myid);
                startActivity(intent1);
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(helpermenu.this,HelperHistory.class);
                intent1.putExtra("name",name);
                intent1.putExtra("userhash",userhash);
                intent1.putExtra("myid",myid);
                startActivity(intent1);
            }
        });
    }
}
