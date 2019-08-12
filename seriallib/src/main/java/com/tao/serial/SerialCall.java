package com.tao.serial;

public interface SerialCall  < T extends  SerialData>{
    
    void  onInitError(String path , int bound);
    void  onSendError(T data);
    void  onSendSuccess(T data);
    void  onReceiverError(T data);
    void  onReceiverData(T data);
    void  onUnInit(String path , int bound);
    
}
