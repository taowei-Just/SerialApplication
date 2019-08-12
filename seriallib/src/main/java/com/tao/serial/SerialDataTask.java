package com.tao.serial;

import com.tao.utilslib.log.LogUtil;

public class SerialDataTask<T extends SerialData> implements Runnable {

    T data;
    ISerial serial;
    SerialCall serialCall;
    private String tag = getClass().getSimpleName();

    public SerialDataTask(ISerial serial, T data, SerialCall serialCall) {
        this.serial = serial;
        this.data = data;
        this.serialCall = serialCall;
    }

    @Override
    public void run() {

        try {
            serial.send(data.getData());
            LogUtil.e(tag, "senSuccess " + data.getId());
            serialCall.onSendError(data);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            serialCall.onSendError(data);
            serial.onOver(this);
            LogUtil.e(tag, "send Faile " + throwable.toString());
            return;
        }

        try {
            LogUtil.e(tag, "receiver " + data.getId());

            byte[] receiver = serial.receiver(data.receiveLen, data.receiveTime);
            data.setReceive(receiver);
            serialCall.onReceiverData(data);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            serialCall.onReceiverError(data);
            return;
        } finally {
            serial.onOver(this);
        }

    }

    public void close() {
        serial.breakOff();
    }
}
