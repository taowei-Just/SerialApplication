package com.tao.tobacco;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.tao.globle.Gloable;
import com.tao.utilslib.log.LogUtil;
import com.tao.protocol.TobaccoProtocol;
import com.tao.seriallib.OnMessageCall;
import com.tao.seriallib.SerialHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vencent on 2018/9/3.
 */

public class SerialWrappleHeilper extends Thread {

    private SerialThread serialThread;
    private SerialHelper serialHelper;
    private OnMessageCall onMessageCall;
    private MyHandler myHandler;
    public static final int sendDataWhat = 1;
    public static final int receiveDataWhat = 2;
    private long wattingTime = 15 * 1000;
    private int dataLen = 12;
    boolean useHandler = false;
    boolean isPrepared = false;
    boolean isAutoNext = false;

    private long timeOut = 10 * 1000;
    private long startTinme;

    public SerialWrappleHeilper(Context context, String serialPath, int baudrate, OnMessageCall onMessageCall) {
        this.onMessageCall = onMessageCall;
        serialHelper = new SerialHelper(context, serialPath, baudrate, onMessageCall);
        serialHelper.open();
    }

    public SerialWrappleHeilper(Context context, String serialPath, int baudrate, OnMessageCall onMessageCall, boolean useHandler) {
        this.useHandler = useHandler;
        this.onMessageCall = onMessageCall;
        serialHelper = new SerialHelper(context, serialPath, baudrate, onMessageCall);
        if (useHandler) {
            serialThread = new SerialThread();
            serialThread.start();
        } else {
            serialHelper.open();
        }
    }

    public boolean isPrepared() {
        return isPrepared;
    }

    public void setOnMessageCall(OnMessageCall onMessageCall) {
        this.onMessageCall = onMessageCall;
        if (serialHelper != null)
            serialHelper.setOnMessageCall(onMessageCall);
    }

    TobaccoProtocol.TobaccoCmdInfo info;

    public void send(TobaccoProtocol.TobaccoCmdInfo info) {
        if (info == null)
            return;
        if (cmdList.contains(info))
            cmdList.remove(info);
        this.info = info;
        if (useHandler) {
            Message message = getMessage(sendDataWhat, info);
            myHandler.sendMessage(message);
        } else {
            startTinme = System.currentTimeMillis();
            serialHelper.send(info);
            serialHelper.receiver(info);
        }
        if (isAutoNext)
            next();
    }


    public void send(int len, long time, TobaccoProtocol.TobaccoCmdInfo info) {
        cmdList.remove(info);
        this.info = info;
        if (useHandler) {
            Message message = getMessage(sendDataWhat, len, time, info);
            myHandler.sendMessage(message);
        } else {
            serialHelper.send(info);
            serialHelper.receiver(len, time, info);
        }
        if (isAutoNext)
            next(len ,time);
    }

    private Message getMessage(int what, int len, long time, TobaccoProtocol.TobaccoCmdInfo info) {
        Message message = new Message();
        message.what = what;
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", info);
        bundle.putInt("len", len);
        bundle.putLong("wattingTime", time);
        message.setData(bundle);
        return message;
    }


    private Message getMessage(int what, TobaccoProtocol.TobaccoCmdInfo info) {
        Message message = new Message();
        message.what = what;
        Bundle bundle = new Bundle();
//        bundle.putByteArray("data", data);
//        bundle.putInt("flag", flag);
        bundle.putSerializable("data", info);
        message.setData(bundle);
        return message;
    }

    public void release() {
        serialHelper.release();
        if (serialThread != null)
            serialThread.close();
    }

    List<TobaccoProtocol.TobaccoCmdInfo> cmdList = new ArrayList<>();

    public void sendList(List<TobaccoProtocol.TobaccoCmdInfo> cmdInfoList) {
        if (cmdInfoList != null)
            cmdList.addAll(cmdInfoList);
        next();
    } 
    
    public void sendList( int len , long time ,List<TobaccoProtocol.TobaccoCmdInfo> cmdInfoList) {
        if (cmdInfoList != null)
            cmdList.addAll(cmdInfoList);
         next(len,timeOut);
    }

    public void clear() {
        cmdList.clear();
    }

    public synchronized void stopSend() {
        cmdList.clear();
        if (useHandler) {
            myHandler.removeMessages(sendDataWhat);
        } else {
        }
        next();
//        if (onMessageCall!=null)
//        onMessageCall.onOver();
    }


    public synchronized void next() {
        TobaccoProtocol.TobaccoCmdInfo info = nextInfo();
        if (info == null) {
            onMessageCall.onOver();
            return;
        }
        send(info);
    }
  public synchronized void next(int len, long time) {
        TobaccoProtocol.TobaccoCmdInfo info = nextInfo();
        if (info == null) {
            onMessageCall.onOver();
            return;
        }
        send(len ,time ,info);
    }


    private TobaccoProtocol.TobaccoCmdInfo nextInfo() {
        if (cmdList.size() > 0)
            return cmdList.get(0);
        else
            return null;
    }

    public boolean timeOut() {
        if (System.currentTimeMillis() - startTinme > timeOut)
            return true;
        return false;
    }


    class SerialThread extends Thread {
        private Looper looper;

        @Override
        public void run() {
            Looper.prepare();
            looper = Looper.myLooper();
            serialHelper.open();
            myHandler = new MyHandler(looper);
            isPrepared = true;
            onMessageCall.onPrepared();
            Looper.loop();
        }

        public void close() {
            if (!isInterrupted())
                interrupt();
            myHandler.removeMessages(sendDataWhat);
            looper.quitSafely();
        }


    }

    class MyHandler extends Handler {

        private long wattingTime;
        private int len;
        private TobaccoProtocol.TobaccoCmdInfo info;

        public MyHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case sendDataWhat:
                    try {
                        startTinme = System.currentTimeMillis();
                        sendCmd(msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                        LogUtil.e(Gloable.MQ_TAG, "串口数据发送失败：" + info.toString() +"\n"+e.toString());
                    }
                    break;
                case receiveDataWhat:
                    try {
                        reReceiver(msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                        LogUtil.e(Gloable.MQ_TAG, "串口数据接收失败：" + info.toString() +"\n"+e.toString());
                    }
                    break;
            }
        }

        private void sendCmd(Message msg) {
            LogUtil.e(Gloable.MQ_TAG, "sendCmd:" + msg.toString(),true);
            info = (TobaccoProtocol.TobaccoCmdInfo) msg.getData().getSerializable("data");
            len = msg.getData().getInt("len", dataLen);
            wattingTime = msg.getData().getLong("wattingTime", SerialWrappleHeilper.this.wattingTime);
            serialHelper.send(info);
            serialHelper.receiver(len, wattingTime, info);
        }

        public void reReceiver(Message msg) {
            LogUtil.e(Gloable.MQ_TAG, "reReceiver:" + msg.toString(),true);
            info = (TobaccoProtocol.TobaccoCmdInfo) msg.getData().getSerializable("data");
            len = msg.getData().getInt("len", dataLen);
            wattingTime = msg.getData().getLong("wattingTime", SerialWrappleHeilper.this.wattingTime);
            serialHelper.receiver(len, wattingTime, info);
        }

    }

    public void reReceiver() {
        if (useHandler) {
            Message message = getMessage(receiveDataWhat, info);
            myHandler.sendMessage(message);
            
        } else {
            
        //                              serialHelper.send(info);
            
            serialHelper.receiver(info);
            
        }
    }


}
