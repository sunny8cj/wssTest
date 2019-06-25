package com.wss.socket_client;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class set extends AppCompatActivity implements View.OnClickListener {


    private EditText edtIP;
    private EditText edtPort;

    private Button button1;
    private Button button_up;
    private Button button_down;
    private Button button_stop;
    private Button button_id;
    private Button button_time;
    private Button button_tcp;
    private Button button_udp;
    private Button button_wifi;
    private Button button_server;
    private Button button_set;

    private EditText edit_ip;
    private EditText edit_port;
    private EditText edit_ssid;
    private EditText edit_psd;
    private EditText edit_time;
    private Spinner spinner1;
    private Spinner spinner2;
    private List<String> data_list1;
    private List<String> data_list2;
    private ArrayAdapter<String> arr_adapter;

    private String ip;
    private Integer port;

    public boolean isConnected = false;
    private String tag = "MainActivity";

    Socket mSocket = null;
    InputStream in;
    PrintWriter printWriter = null;
    //private MyHandler myHandler;


    private final int MSG_set=10;
    private final int Msg_up=7;
    private final int MSG_down=8;
    private final int Msg_stop=9;
    private final int MSG_conn=0;
    private final int Msg_wifi=2;
    private final int MSG_server=1;
    private final int Msg_tcp=3;
    private final int Msg_udp=4;
    private final int Msg_time=6;
    private final int Msg_id=5;

    private String str;
    private Handler mHandler;
    private  Message msg=new Message();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        edtIP = (EditText) this.findViewById(R.id.editText);
        edtPort = (EditText) this.findViewById(R.id.editText2) ;


        button1=(Button)findViewById(R.id.button_conn);
        button1.setOnClickListener(this);
        button_up=(Button)findViewById(R.id.button_up) ;
        button_up.setOnClickListener(this);
        button_down=(Button)findViewById(R.id.button_down) ;
        button_down.setOnClickListener(this);
        button_stop=(Button)findViewById(R.id.button_stop) ;
        button_stop.setOnClickListener(this);
        button_id=(Button)findViewById(R.id.button_id) ;
        button_id.setOnClickListener(this);
        button_time=(Button)findViewById(R.id.button_time);
        button_time.setOnClickListener(this);
        button_tcp=(Button)findViewById(R.id.button_tcp) ;
        button_tcp.setOnClickListener(this);
        button_udp=(Button)findViewById(R.id.button_udp) ;
        button_udp.setOnClickListener(this);
        button_wifi=(Button)findViewById(R.id.button_wifi) ;
        button_wifi.setOnClickListener(this);
        button_server=(Button)findViewById(R.id.button_server) ;
        button_server.setOnClickListener(this);
        button_set=(Button)findViewById(R.id.button_set) ;
        button_set.setOnClickListener(this);

        edit_ssid=(EditText)findViewById(R.id.editText_ssid) ;
        edit_psd=(EditText)findViewById(R.id.editText_psd) ;
        edit_time=(EditText)findViewById(R.id.editText_time) ;
        edit_ip=(EditText)findViewById(R.id.editText_ip) ;
        edit_port=(EditText)findViewById(R.id.editText_port) ;

        spinner1=(Spinner)findViewById(R.id.spinner1) ;


        //数据 设备id
        data_list1=new ArrayList<String>();
        data_list1.add("1");
        data_list1.add("2");
        data_list1.add("3");
        data_list1.add("4");
        data_list1.add("5");
        data_list1.add("6");
        data_list1.add("7");
        data_list1.add("8");
        data_list1.add("9");
        data_list1.add("10");
        data_list1.add("11");
        data_list1.add("12");
        data_list1.add("13");
        data_list1.add("14");
        data_list1.add("15");
        data_list1.add("16");
        //适配器
        arr_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list1);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner1.setAdapter(arr_adapter);
        new SendThread().start();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            // 启动2个工作线程:发送、接收。
            case R.id.button_conn:

                connectThread();

                break;
            case R.id.button_server:
                //  Message msg=new Message();
                //  msg.what=MSG_server;
                str="AT+I=\""+edit_ip.getText().toString()+"\",\""+edit_port.getText().toString()+"\"";
                //   mHandler.sendMessage(msg);

                mHandler.obtainMessage(MSG_server,str).sendToTarget();



                //  str="AT+I=\""+edit_ip.getText().toString()+"\",\""+edit_port.getText().toString()+"\"";
                // mHandler.obtainMessage(MSG_server,str).sendToTarget();
                //   sendData("AT+I=\""+edit_ip.getText().toString()+"\",\""+edit_port.getText().toString()+"\"");
                break;
            case R.id.button_set:
                str="AT+R";
                mHandler.obtainMessage(MSG_set,str).sendToTarget();
                //  sendData("AT+R");
                break;
            case R.id.button_up:

                str="AT+O";
                mHandler.obtainMessage(Msg_up,str).sendToTarget();
                break;
            case R.id.button_down:

                str="AT+N";
                mHandler.obtainMessage(MSG_down,str).sendToTarget();

                break;
            case R.id.button_stop:
                str="AT+P";
                mHandler.obtainMessage(Msg_stop,str).sendToTarget();
                break;
            case R.id.button_tcp:
                str="AT+T=\"T\" ";
                mHandler.obtainMessage(Msg_tcp,str).sendToTarget();
                break;
            case R.id.button_udp:
                str="AT+T=\"U\" ";
                mHandler.obtainMessage(Msg_udp,str).sendToTarget();
                break;
            case R.id.button_wifi:

                str="AT+J=\""+edit_ssid.getText().toString()+"\",\""+edit_psd.getText().toString()+"\"";
                mHandler.obtainMessage(Msg_wifi,str).sendToTarget();
                break;
            case R.id.button_id:

                str="AT+D=\""+spinner1.getSelectedItem().toString()+"\"";
                mHandler.obtainMessage(Msg_id,str).sendToTarget();
                break;
            case R.id.button_time:

                str="AT+X=\""+edit_time.getText().toString()+"\"";
                mHandler.obtainMessage(Msg_time,str).sendToTarget();;
                break;



        }
    }


    public  class SendThread extends Thread{
        @Override
        public void run(){
            Looper.prepare();
            mHandler = new Handler(){
                public void handleMessage(Message msg){
                    sendData(msg.obj.toString());

//
                }
            };
            Looper.loop();//4、启动消息循环



        }
    }

    /**
     * 启动连接线程.
     */
    private void connectThread() {
        if (!isConnected) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    Looper.prepare();
                    Log.i(tag, "---->> connect/close server!");

                    connectServer(edtIP.getText().toString(), edtPort.getText()
                            .toString());

                }
            }).start();
//            button1.setText("取消连接");
            Toast.makeText(set.this,"连接成功！",Toast.LENGTH_LONG).show();

        } else {
            try {
                if (mSocket != null) {
                    mSocket.close();
                    mSocket = null;
                    Log.i(tag, "--->>取消server.");
                    // receiverThread.interrupt();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            button1.setText("连接服务器");
            isConnected = false;
            Toast.makeText(set.this,"连接断开！",Toast.LENGTH_LONG).show();
        }
    }

    // 连接服务器.(网络调试助手的服务器端编码方式:gb2312)
    private void connectServer(String ip, String port) {
        try {
            //   Log.e(tag, "--->>start connect  server !" + ip + "," + port);

            mSocket = new Socket(ip, Integer.parseInt(port));

//            Log.e(tag, "--->>end connect  server!");
//
            OutputStream outputStream = mSocket.getOutputStream();

            printWriter = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(outputStream,
                            Charset.forName("gb2312"))));
            isConnected = true;
            button1.setText("取消连接");
            Toast.makeText(set.this,"正在发送！",Toast.LENGTH_LONG).show();
//
//            in = mSocket.getInputStream();



//            showInfo("连接成功!");
        } catch (Exception e) {
//            isConnected = false;
//            showInfo(e.toString());
//            Log.e(tag, "exception:" + e.toString());
        }

    }


    //发送消息线程



    /**
     * 发送数据线程.
     */


    private void sendData(String msg) {



        // sendThread.start();
        try {
            //	String context = edtSend.getText().toString();

            if (printWriter == null || msg == null) {

                if (printWriter == null) {
                    //   showInfo("连接失败!");
                    return;
                }
                if (msg == null) {
                    // showInfo("连接失败!");
                    return;
                }
            }
            // 向服务端程序发送数据  方法1
//            printWriter.print(msg);
//            printWriter.flush();

            // 向服务端程序发送数据
            OutputStream ops = mSocket.getOutputStream();
            OutputStreamWriter opsw = new OutputStreamWriter(ops);
            BufferedWriter bw = new BufferedWriter(opsw);
            bw.write(msg);
            bw.flush();
            Toast.makeText(set.this,"正在发送！",Toast.LENGTH_LONG).show();
            Log.i(tag, "--->> client send data!");
        } catch (Exception e) {
            Log.e(tag, "--->> send failure!" + e.toString());

        }
    }






}
