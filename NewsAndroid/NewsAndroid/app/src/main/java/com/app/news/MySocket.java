package com.app.news;

import android.app.Application;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by  on 2017/3/12.
 */

public class MySocket implements Runnable {
    MySocketListener listener;

    private static final String HOST = "137.207.82.51";
    private static final int PORT = 9999;
    private Socket socket = null;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private String content = "";

    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            listener.onDataReceived(content);
        }
    };

    public MySocket(MySocketListener listener){
        this.listener = listener;
        try {
            socket = new Socket(HOST, PORT);
            in = new BufferedReader(new InputStreamReader(socket
                    .getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                    socket.getOutputStream())), true);
        } catch (IOException ex) {
            ex.printStackTrace();
            listener.onErrorReceived("login exception" + ex.getMessage());
        }
        new Thread(this).start();
    }

    public void send(String data){
        final String msg = data;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (socket.isConnected()) {
                    if (!socket.isOutputShutdown()) {
                        out.print(msg);
                        out.flush();
                    }
                }
            }
        };
        mHandler.post(runnable);
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (!socket.isClosed()) {
                    if (socket.isConnected()) {
                        if (!socket.isInputShutdown()) {
                            if ((content = in.readLine()) != null) {
                                mHandler.sendMessage(mHandler.obtainMessage());
                                break;
                            } else {

                            }
                        }
                    }else {//socket disconnected
                        listener.onErrorReceived("socket disconnected");
                        break;
                    }
                }else {//socket closed
                    listener.onErrorReceived("socket closed");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static interface MySocketListener{
        void onDataReceived(String data);
        void onErrorReceived(String error);
    }
}
