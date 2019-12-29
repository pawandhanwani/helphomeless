package com.example.helphomeless;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class homelessmenu extends AppCompatActivity {

    Button spaceButton,historyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homelessmenu);

        spaceButton = (Button) findViewById(R.id.button3);
        historyButton =(Button) findViewById(R.id.button4);
        final Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        final String userhash = intent.getStringExtra("userhash");
        final int myid = (int) intent.getIntExtra("myid",0);
        spaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homelessmenu.this,SpaceSearcher.class);
                intent.putExtra("name",name);
                intent.putExtra("userhash",userhash);
                intent.putExtra("myid",myid);
                startActivity(intent);
            }
        });

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homelessmenu.this,AcqHistory.class);
                intent.putExtra("name",name);
                intent.putExtra("userhash",userhash);
                intent.putExtra("myid",myid);
                startActivity(intent);
            }
        });
    }
}
