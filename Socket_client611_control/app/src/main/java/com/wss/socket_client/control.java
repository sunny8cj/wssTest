package com.wss.socket_client;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by 75307 on 2018/6/7.
 */

public class control extends AppCompatActivity implements View.OnClickListener {
    private Button button_open;
    private Button button_close;
    private Button button_stop;
    public static int frame_name_open;
    public static int frame_name_close;
    public static int frame_name_stop;
    String framestr_open="1000B400FFFFFFFFA0258F6701000100030000804C796E6B726F7300BB904351D17980624C796E6B726F7300BB904351D179806200000300010000000400000000030000FFFFFFFF020000008000000008030000FFFFFFFF030000008000000008030000FFFFFFFF01000000040000000000010001000000010000001400000000000100FFFFFFFFFFFFFFFF000000000000000000000000010000002000000000000100FFFFFFFFFFFFFFFFFFFFFFFF000001000100000091030000040000000000C842";
    String framestr_close="1000B400FFFFFFFF74AF8C6701000100030000804C796E6B726F7300BB904351D17980624C796E6B726F7300BB904351D179806200000300010000000400000000030000FFFFFFFF020000008000000008030000FFFFFFFF030000008000000008030000FFFFFFFF01000000040000000000010001000000010000001400000000000100FFFFFFFFFFFFFFFF000000000000000000000000010000002000000000000100FFFFFFFFFFFFFFFFFFFFFFFF0000010001000000910300000400000000000000";
    String framestr_stop="1000B400FFFFFFFFC7FA886701000100030000804C796E6B726F7300BB904351D17980624C796E6B726F7300BB904351D179806200000300010000000400000000030000FFFFFFFF020000008000000008030000FFFFFFFF030000008000000008030000FFFFFFFF01000000040000000000010001000000010000001400000000000100FFFFFFFFFFFFFFFF000000000000000000000000010000002000000000000100FFFFFFFFFFFFFFFFFFFFFFFF0000010001000000910300000400000000004842";

    public Socket mySocket = null;
    public boolean isConnected = false;
    private EditText editText;
    private EditText editText2;
    private Button btn_conn;
    public  PrintWriter printWriter = null;
    private  Spinner spinner;
    private List<String> data_list1;
    private ArrayAdapter<String> arr_adapter;
    private  Spinner spinner2;
    private List<String> data_list2;
    private List<String> data_list3;
    private ArrayAdapter<String> arr_adapter2;
    private ArrayAdapter<String> arr_adapter3;
    private Handler mHandler;
    private final int Msg_open=1;
    private final int MSG_close=2;
    private final int Msg_stop=3;
    private Button btn_set2;
    private Button btn_control2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.control2);
        button_open=(Button)findViewById(R.id.button_open) ;
        button_close=(Button)findViewById(R.id.button_close) ;
        button_stop=(Button) findViewById(R.id.button_stop);
        button_open.setOnClickListener(this);
        button_close.setOnClickListener(this);
        button_stop.setOnClickListener(this);
        btn_conn=(Button)findViewById(R.id.btn_conn) ;
        btn_conn.setOnClickListener(this);
        btn_set2=(Button)findViewById(R.id.btn_set2) ;
        btn_set2.setOnClickListener(this);

        editText=(EditText)findViewById(R.id.edt_ip) ;
        editText2=(EditText) findViewById(R.id.edt_port);


        spinner=(Spinner)findViewById(R.id.spinner) ;
        //数据 设备id
        data_list1=new ArrayList<String>();
        data_list1.add("窗户");
        data_list1.add("窗帘");
        data_list1.add("门禁");

        //适配器
        arr_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list1);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner.setAdapter(arr_adapter);
       // spinner.setSelection(0,true);


        spinner2=(Spinner)findViewById(R.id.spinner2) ;
        //数据 设备id
        data_list2=new ArrayList<String>();
        data_list2.add("1");
        data_list2.add("2");
        data_list2.add("3");
        data_list2.add("4");
        data_list2.add("5");
        data_list2.add("6");
        data_list2.add("7");
        data_list2.add("8");
        //适配器
        arr_adapter2= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list2);
        //设置样式
        arr_adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //数据 设备id
        data_list3=new ArrayList<String>();
        data_list3.add("1");
        data_list3.add("2");
        //适配器
        arr_adapter3= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list3);
        //设置样式
        arr_adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        //加载适配器
//        spinner2.setAdapter(arr_adapter2);
//        arr_adapter2.notifyDataSetChanged();
//        spinner2.setSelection(0);
//        //spinner2.setSelection(0,true);

        // spinner设置监听
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){//通过此方法为下拉列表设置点击事件
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text= spinner.getItemAtPosition(i).toString();
                switch (text){
                    case "门禁":
                        //加载适配器
                        spinner2.setAdapter(arr_adapter3);
                        arr_adapter3.notifyDataSetChanged();
                        spinner2.setSelection(0);
                        framestr_open="1000B400FFFFFFFFE32B876F01000100030000804C796E6B726F7300BB904351D17980624C796E6B726F7300BB904351D179806200000300010000000400000000030000FFFFFFFF020000008000000008030000FFFFFFFF030000008000000008030000FFFFFFFF01000000040000000000010001000000010000001400000000000100FFFFFFFFFFFFFFFF000000000000000000000000010000002000000000000100FFFFFFFFFFFFFFFFFFFFFFFF0000010001000000F10300000400000001000000";
                        framestr_close="1000B400FFFFFFFFEE00886F01000100030000804C796E6B726F7300BB904351D17980624C796E6B726F7300BB904351D179806200000300010000000400000000030000FFFFFFFF020000008000000008030000FFFFFFFF030000008000000008030000FFFFFFFF01000000040000000000010001000000010000001400000000000100FFFFFFFFFFFFFFFF000000000000000000000000010000002000000000000100FFFFFFFFFFFFFFFFFFFFFFFF0000010001000000F10300000400000000000000";
                        button_stop.setVisibility(View.INVISIBLE);
                        break;
                    default:  // "窗帘 窗户"
                        //加载适配器
                        spinner2.setAdapter(arr_adapter2);
                        arr_adapter2.notifyDataSetChanged();
                        spinner2.setSelection(0);
                        framestr_open="1000B400FFFFFFFFA0258F6701000100030000804C796E6B726F7300BB904351D17980624C796E6B726F7300BB904351D179806200000300010000000400000000030000FFFFFFFF020000008000000008030000FFFFFFFF030000008000000008030000FFFFFFFF01000000040000000000010001000000010000001400000000000100FFFFFFFFFFFFFFFF000000000000000000000000010000002000000000000100FFFFFFFFFFFFFFFFFFFFFFFF000001000100000091030000040000000000C842";
                        framestr_close="1000B400FFFFFFFF74AF8C6701000100030000804C796E6B726F7300BB904351D17980624C796E6B726F7300BB904351D179806200000300010000000400000000030000FFFFFFFF020000008000000008030000FFFFFFFF030000008000000008030000FFFFFFFF01000000040000000000010001000000010000001400000000000100FFFFFFFFFFFFFFFF000000000000000000000000010000002000000000000100FFFFFFFFFFFFFFFFFFFFFFFF0000010001000000910300000400000000000000";

                        button_stop.setVisibility(View.VISIBLE);
                        break;
                }
                Toast.makeText(control.this,text,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //随机生成一个1-60000之间的整数序号，作为帧名称
        Random random=new Random();
        frame_name_open=random.nextInt(59999)+1;
        frame_name_close=random.nextInt(59999)+1;
        frame_name_stop=random.nextInt(59999)+1;

        new SendThread1().start();

    }


    @Override
    public  void onClick(View v){
        switch (v.getId()){
            case R.id.btn_set2:
                Intent intent =new Intent(control.this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_conn:
                connectThread1();
                break;
            case R.id.button_open:
              //  editText.setText(Common.bytes2HexString(get_PublishByte(framestr_open,frame_name_open)));
             //   sendData(Common.bytes2HexString(get_PublishByte(framestr_open,frame_name_open)));
                mHandler.obtainMessage(Msg_open,get_PublishByte(framestr_open,frame_name_open)).sendToTarget();

                frame_name_open++;

                break;
            case R.id.button_close:
               // editText.setText(Common.bytes2HexString(get_PublishByte(framestr_close,frame_name_close)));
                mHandler.obtainMessage(MSG_close,get_PublishByte(framestr_close,frame_name_close)).sendToTarget();
                //sendData(Common.bytes2HexString(get_PublishByte(framestr_close,frame_name_close)));
                frame_name_close++;
                break;
            case R.id.button_stop:
               // editText.setText(Common.bytes2HexString(get_PublishByte(framestr_stop,frame_name_stop)));
               // sendData(Common.bytes2HexString(get_PublishByte(framestr_stop,frame_name_stop)));
                mHandler.obtainMessage(Msg_stop,get_PublishByte(framestr_stop,frame_name_stop)).sendToTarget();
                frame_name_stop++;
                break;
        }

    }
    /**
     * 启动连接线程.
     */
    public void connectThread1() {
        if (mySocket==null) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    Looper.prepare();
                    connectServer(editText.getText().toString(), editText2.getText()
                            .toString());
                    if(isConnected)
                    Toast.makeText(control.this,"连接成功！",Toast.LENGTH_LONG).show();
                    Looper.loop();

                }
            }).start();


        } else {
            try {
                if (mySocket != null) {
                    mySocket.close();
                    mySocket = null;

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            btn_conn.setText("连接服务器");
            isConnected = false;
            Toast.makeText(control.this,"连接断开！",Toast.LENGTH_LONG).show();
        }
    }

    // 连接服务器.(网络调试助手的服务器端编码方式:gb2312)
    private void connectServer(String ip, String port) {
        try {
            //   Log.e(tag, "--->>start connect  server !" + ip + "," + port);

            mySocket = new Socket(ip, Integer.parseInt(port));

//            Log.e(tag, "--->>end connect  server!");
//
            OutputStream outputStream = mySocket.getOutputStream();
            printWriter = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(outputStream,
                            Charset.forName("gb2312"))));
            isConnected = true;
            btn_conn.setText("取消连接");
           // Toast.makeText(control.this,"正在发送！",Toast.LENGTH_LONG).show();
//
//            in = mSocket.getInputStream();



//            showInfo("连接成功!");
        } catch (Exception e) {
//
        }

    }


    public  class SendThread1 extends Thread{
        @Override
        public void run(){
            Looper.prepare();
            mHandler = new Handler(){
                public void handleMessage(Message msg){
                   // sendData(msg.obj.toString());
                    sendByte((byte[]) msg.obj);

//
                }
            };
            Looper.loop();//4、启动消息循环



        }
    }



    private void sendByte(byte[] buf){
        try {
            if (printWriter == null || buf == null) {

                if (printWriter == null) {
                    //   showInfo("连接失败!");
                    return;
                }
                if (buf == null) {
                    // showInfo("连接失败!");
                    return;
                }
            }
            DataInputStream is=new DataInputStream(new ByteArrayInputStream(buf));
            OutputStream os   = mySocket.getOutputStream();
            os.write(buf);
            os.flush();

        } catch (Exception e) {

        }
    }

    public byte[] get_PublishByte(String framestr, int framename){
        byte buffershead[]  = {(byte) 0x55
                , (byte) 0xFF
        };
        byte nameList[] = Common.hexString2Bytes(Common.intToHexString(framename,2));
        nameList =   Common.reverseArray(nameList);
        //String frameStr = "10 00 B4 00 FF FF FF FF F6 8F 91 DA 01 00 01 00 03 00 00 80 4C 79 6E 6B 72 6F 73 00 BB 90 43 51 D1 79 80 62 4C 79 6E 6B 72 6F 73 00 BB 90 43 51 D1 79 80 62 00 00 03 00 01 00 00 00 04 00 00 00 00 03 00 00 FF FF FF FF 02 00 00 00 80 00 00 00 08 03 00 00 FF FF FF FF 03 00 00 00 80 00 00 00 08 03 00 00 FF FF FF FF 01 00 00 00 04 00 00 00 00 00 01 00 01 00 00 00 01 00 00 00 14 00 00 00 00 00 01 00 FF FF FF FF FF FF FF FF 00 00 00 00 00 00 00 00 00 00 00 00 01 00 00 00 20 00 00 00 00 00 01 00 FF FF FF FF FF FF FF FF FF FF FF FF 00 00 01 00 01 00 00 00 91 03 00 00 04 00 00 00 00 00 C8 42";
        // String frameStr ="1000B400FFFFFFFFFA11D3D401000100030000804C796E6B726F7300BB904351D17980624C796E6B726F7300BB904351D179806200000300010000000400000000030000FFFFFFFF020000008000000008030000FFFFFFFF030000008000000008030000FFFFFFFF01000000040000000000010001000000010000001400000000000100FFFFFFFFFFFFFFFF000000000000000000000000010000002000000000000100FFFFFFFFFFFFFFFFFFFFFFFF000001000100000091030000040000000000C842";
        byte temp[] = new  byte[0];
        temp = Common.hexString2Bytes(framestr);

        byte beif[] = new byte[3];
        byte sum[] = new byte[0];
        //framename++;

        //设置帧名称
        temp[10]= nameList[0];
        temp[11]= nameList[1];
        //crc初始化
        temp[8]= (byte)0x00;
        temp[9]= (byte)0x00;
        if(spinner.getSelectedItem().toString()=="窗户"){
            //修改设备id位
            switch (  spinner2.getSelectedItem().toString()){
                case "1":
                    temp[184]=(byte)0x91;
                    temp[185]=(byte)0x03;
                    break;
                case "2":
                    temp[184]=(byte)0x95;
                    temp[185]=(byte)0x03;
                    break;
                case "3":
                    temp[184]=(byte)0x99;
                    temp[185]=(byte)0x03;
                    break;
                case "4":
                    temp[184]=(byte)0x9D;
                    temp[185]=(byte)0x03;
                    break;
                case "5":
                    temp[184]=(byte)0xA1;
                    temp[185]=(byte)0x03;
                    break;
                case "6":
                    temp[184]=(byte)0xA5;
                    temp[185]=(byte)0x03;
                    break;
                case "7":
                    temp[184]=(byte)0xA9;
                    temp[185]=(byte)0x03;
                    break;
                case "8":
                    temp[184]=(byte)0xAD;
                    temp[185]=(byte)0x03;
                    break;


            }

        }
        else if(spinner.getSelectedItem().toString()=="窗帘")
        {
            //修改设备id位
            switch (  spinner2.getSelectedItem().toString()){
                case "1":
                    temp[184]=(byte)0xB1;
                    temp[185]=(byte)0x03;
                    break;
                case "2":
                    temp[184]=(byte)0xB9;
                    temp[185]=(byte)0x03;
                    break;
                case "3":
                    temp[184]=(byte)0xC1;
                    temp[185]=(byte)0x03;
                    break;
                case "4":
                    temp[184]=(byte)0xC9;
                    temp[185]=(byte)0x03;
                    break;
                case "5":
                    temp[184]=(byte)0xD1;
                    temp[185]=(byte)0x03;
                    break;
                case "6":
                    temp[184]=(byte)0xD9;
                    temp[185]=(byte)0x03;
                    break;
                case "7":
                    temp[184]=(byte)0xE1;
                    temp[185]=(byte)0x03;
                    break;
                case "8":
                    temp[184]=(byte)0xE9;
                    temp[185]=(byte)0x03;
                    break;


            }
        }
        else if(spinner.getSelectedItem().toString()=="门禁"){

            //修改设备id位
            switch (  spinner2.getSelectedItem().toString()){
                case "1":
                    temp[184]=(byte)0xF1;
                    temp[185]=(byte)0x03;
                    break;
                case "2":
                    temp[184]=(byte)0xF5;
                    temp[185]=(byte)0x03;
                    break;
            }
        }





        //做校验
        int jiaoyanma = CRC.get_crc16(temp,temp.length,beif);
        temp[8] = beif[0];
        temp[9] = beif[1];
        temp = concat(buffershead,temp);
        sum = concat(sum,temp);


        return sum;

    }

    public static byte[] concat(byte[] a, byte[] b) {
        byte[] c= new byte[a.length+b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }
}
