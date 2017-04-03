package com.app.news;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity{
    Button button;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String title = getIntent().getStringExtra("title");
        String content = getIntent().getStringExtra("content");

        TextView titleV = (TextView)findViewById(R.id.title);
        TextView contentV = (TextView)findViewById(R.id.content);
        Button button = (Button)findViewById(R.id.button);
        ListView listView = (ListView)findViewById(R.id.list);

        titleV.setText(title);
        contentV.setText(content);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.isLogin){
                    final EditText inputServer = new EditText(NewsActivity.this);
                    AlertDialog.Builder builder = new AlertDialog.Builder(NewsActivity.this);
                    builder.setTitle("Comment").setIcon(android.R.drawable.ic_dialog_info).setView(inputServer)
                            .setNegativeButton("Cancel", null);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            String text = inputServer.getText().toString();
                        }
                    });
                    builder.show();
                }else {
                    Intent intent = new Intent(NewsActivity.this, LoginActivity.class);
                    NewsActivity.this.startActivity(intent);
                }
            }
        });
    }
}
