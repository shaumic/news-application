package com.app.news;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MySocket.MySocketListener {
    public static boolean isLogin = false;

    Button button1,button2,button3,button4,button5;
    ListView listView;
    public static int currentView = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());

        button1 = (Button)findViewById(R.id.button1);button1.setOnClickListener(this);
        button2 = (Button)findViewById(R.id.button2);button2.setOnClickListener(this);
        button3 = (Button)findViewById(R.id.button3);button3.setOnClickListener(this);
        button4 = (Button)findViewById(R.id.button4);button4.setOnClickListener(this);
        button5 = (Button)findViewById(R.id.button5);button5.setOnClickListener(this);

        listView = (ListView)findViewById(R.id.list);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button1:
                MySocket mySocket = new MySocket(this);
                mySocket.send("home");
                currentView = 1;
                break;
            case R.id.button2:
                mySocket = new MySocket(this);
                mySocket.send("business");
                currentView = 2;
                break;
            case R.id.button3:
                mySocket = new MySocket(this);
                mySocket.send("science");
                currentView = 3;
                break;
            case R.id.button4:
                mySocket = new MySocket(this);
                mySocket.send("magazine");
                currentView = 4;
                break;
            case R.id.button5:
                mySocket = new MySocket(this);
                mySocket.send("health");
                currentView = 5;
                break;
        }
    }

    @Override
    public void onDataReceived(String data){
        Log.e("debug",data);
        List<News> listData = new ArrayList<>();
        String[] a = data.split("::");
        for(int i=1;i<20;i+=2){
            News n = new News(a[i],a[i+1]);
            listData.add(n);
        }
        NewsAdapter adapter = new NewsAdapter(this,R.layout.news_item,listData);
        listView.setAdapter(adapter);
    }

    @Override
    public void onErrorReceived(String error){
        Log.e("debug2",error);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
