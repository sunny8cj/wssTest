package com.wss.socket_client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 75307 on 2018/5/24.
 */

public class Index extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);
        //调度执行timerTask，第二个参数传入延迟时间（毫秒）
        timer.schedule(timerTask,4000);

    }
    Timer timer =new Timer();
    TimerTask timerTask=new TimerTask() {
        @Override
        public void run() {
            //发送intent实现页面跳转，第一个参数为当前页面的context，第二个参数为要跳转的主页
            Intent intent =new Intent(Index.this,MainActivity.class);
           startActivity(intent);
         //   startActivityForResult(intent,1);
            //跳转后关闭当前欢迎页
           Index.this.finish();

        }
    };

}
