package com.tao.serialliba;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;

import com.demo.serial.R;
import com.tao.serial.AsyncSerial;
import com.tao.serial.SerialCall;
import com.tao.serial.SerialData;
import com.tao.serialliba.tobaco.CmdType;
import com.tao.serialliba.tobaco.CommandResult;
import com.tao.serialliba.tobaco.TobacoProtocol;
import com.tao.utilslib.log.LogUtil;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    private AsyncSerial asyncSerial;
    private String tag = getClass().getSimpleName();
    private EditText etText;
    private ScrollView svText;

    StringBuilder sb = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etText = findViewById(R.id.et_text);
        svText = findViewById(R.id.sv_text);
        open(null);
    }

    public void open(View view) {
        try {
            asyncSerial = new AsyncSerial("/dev/ttyS2", 9600, new MyCall());
            asyncSerial.setMode(AsyncSerial.Mode.signleMode).init();
            showText("打开串口成功：" + asyncSerial.getSerialPath() + "  " + asyncSerial.getBaudrate());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            showText("初始化失败！");
        }

        reset(null);

    }

    public void close(View view) {
        asyncSerial.close();
    }

    public void send(View view) {

        showText("send ");
        SerialData data = new SerialData();
        data.setData(new byte[]{1, 2, 3, 4});
        data.setId(AsyncSerial.getId());
        asyncSerial.send(data);

    }

    public void bh(View view) {

    }

    public void ch(View view) {

    }

    public void clear(View view) {
        sb = new StringBuilder();
        showText("");
    }

    public void reset(View view) {
        SerialData data = new SerialData();
        data.setData(TobacoProtocol.bootReady());
        data.setId(AsyncSerial.getId());
        asyncSerial.send(data);

    }

    class MyCall implements SerialCall {
        @Override
        public void onInitError(String path, int bound) {
            LogUtil.e(tag, "onInitError " + path + "  " + bound);
            showText("onInitError " + path + "  " + bound);
        }

        @Override
        public void onSendError(SerialData data) {
            LogUtil.e(tag, "onSendError " + data.toString());
            showText("onSendError " + data.toString());
        }

        @Override
        public void onSendSuccess(SerialData data) {
            LogUtil.e(tag, "onSendSuccess " + data.toString());
            showText("onSendSuccess " + data.toString());
        }

        @Override
        public void onReceiverError(SerialData data) {
            LogUtil.e(tag, "onReceiverError " + data.toString());
            showText("onReceiverError " + data.toString());
        }

        @Override
        public void onReceiverData(SerialData data) {
            LogUtil.e(tag, "onReceiverData " + data.toString());
            showText("接收数据：" + data.toString());
            /**
             * 1.收到应答帧不做处理
             * 2. 收到数据帧进行数据存储   并发送应答帧
             * 3. 收到结果帧进行结果分析   并发送应答帧
             * 4. 进行下一跳数据发送
             */

            CommandResult commandResult = TobacoProtocol.praceata(data.getData());
            if (commandResult == null) {
                onReceiverError(data);
                return;
            }
            LogUtil.e(tag, " 解析数据结果 :" + commandResult.toString());
            switch (commandResult.getCmdType()) {
                case DATA:
                    repeatData(data);
                    break;
                case RESULT:
                    repeatData(data);
                    break;
                    
                case CMD:
                case ACK:
                case INFO:
                case WARNING:
                    break;
            }
        }

        @Override
        public void onUnInit(String path, int bound) {
            LogUtil.e(tag, "onUnInit " + path + "  " + bound);
            showText("onUnInit " + path + "  " + bound);
        }
    }

    private void repeatData(SerialData data) {
        data.setData(TobacoProtocol.replyFrame(CmdType.type(data.getData()[2])));
        data.setId(AsyncSerial.getId());
        asyncSerial.send(data);
    }

    public void showText(String str) {
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date(System.currentTimeMillis()));
        sb.insert(0, "==\n" + format + "\n" + str + "\n");
        etText.post(new Runnable() {
            @Override
            public void run() {
                etText.setText(sb.toString());
                svText.post(new Runnable() {
                    @Override
                    public void run() {
                        svText.fullScroll(ScrollView.SCROLL_INDICATOR_TOP);
                    }
                });

            }
        });
    }
}
