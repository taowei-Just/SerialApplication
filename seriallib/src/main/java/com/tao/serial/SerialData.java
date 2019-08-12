package com.tao.serial;

import com.tao.utilslib.encrypt.ParseSystemUtil;

import java.util.Arrays;

public class SerialData {
    int id;
    // 发送数据
    byte[] data;
    // 接收数据
    byte[] receive;
    // 数据长度
    int receiveLen = 20;
    // 接收数据超时时间
    long receiveTime = 200;

    public SerialData() {
    }

    public SerialData(int id, byte[] data, int receiveLen, long receiveTime) {
        this.id = id;
        this.data = data;
        this.receiveLen = receiveLen;
        this.receiveTime = receiveTime;
    }

    public int getReceiveLen() {
        return receiveLen;
    }

    public void setReceiveLen(int receiveLen) {
        this.receiveLen = receiveLen;
    }

    public long getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(long receiveTime) {
        this.receiveTime = receiveTime;
    }

    public byte[] getReceive() {
        return receive;
    }

    public void setReceive(byte[] receive) {
        this.receive = receive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{" +
                "\nid=" + id +
                "\n send  data=" +  ParseSystemUtil.parseByte2HexStr( data) +
                "\n receive  data=" +ParseSystemUtil.parseByte2HexStr( receive) +
                "\n receive Len=" + receiveLen +
                "\n receive Time=" + receiveTime +
                "\n}";
    }
}
