package com.tao.serial;

public interface ISerial {

    // 初始化串口
    void init() throws Throwable;
// 发送数据
    void send(byte[] data) throws Throwable;

    // 接收数据 阻塞式
    byte[] receiver() throws Throwable;
    
    // 接收数据 指定长度 超时时间
    byte[] receiver(int len , long time) throws Throwable;

    // 关闭串口
    void close();
    // 重置串口
    void  reset() throws Throwable;
    boolean isSelialUse();
    
    void breakOff();
    
    void onOver(Runnable runnable);
}
